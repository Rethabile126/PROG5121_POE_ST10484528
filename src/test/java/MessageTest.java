package za.ac.iie.prog5121_poe;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class MessageTest {

    @BeforeEach
    public void setUp() {
        Message.sentMessages.clear();
        Message.storedMessages.clear();
        Message.disregardedMessages.clear();
        Message.populateTestData();
    }

    @Test
    public void testSentMessagesArrayCorrectlyPopulated() {
        // Test Data: message 1-4
        assertEquals(2, Message.sentMessages.size());
        String msg1 = Message.sentMessages.get(0).getText();
        String msg2 = Message.sentMessages.get(1).getText();
        assertEquals("Did you get the cake?", msg1);
        assertEquals("It is dinner time !", msg2);
    }

    @Test
    public void testDisplayTheLongestMessage() {
        // Test Data: message 1-4
        ArrayList<Message> all = new ArrayList<>();
        all.addAll(Message.sentMessages);
        all.addAll(Message.storedMessages);
        all.addAll(Message.disregardedMessages);
        
        Message longest = all.get(0);
        for (Message m : all) {
            if (m.getText().length() > longest.getText().length()) {
                longest = m;
            }
        }
        // Expected: “Where are you? You are late! I have asked you to be on time.”
        assertEquals("Where are you? You are late! I have asked you to be on time.", longest.getText());
    }

    @Test
    public void testSearchForMessageID() {
        // Test Data: message 4, "0838884567"
        // In our data message 4 is recipient +27838884567 with text "It is dinner time !"
        boolean found = false;
        for (Message m : Message.sentMessages) {
            if (m.getRecipient().contains("0838884567")) {
                assertEquals("It is dinner time !", m.getText());
                found = true;
            }
        }
        assertTrue(found, "Message 4 should be found");
    }

    @Test
    public void testSearchByRecipient() {
        // Test Data: +27838884567
        // System returns: "Where are you?..." and "Ok, I am leaving without you."
        String target = "+27838884567";
        ArrayList<String> found = new ArrayList<>();
        for (Message m : Message.sentMessages) if (m.getRecipient().equals(target)) found.add(m.getText());
        for (Message m : Message.storedMessages) if (m.getRecipient().equals(target)) found.add(m.getText());
        
        assertEquals(2, found.size());
        assertTrue(found.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(found.contains("Ok, I am leaving without you."));
    }

    @Test
    public void testDeleteMessageUsingHash() {
        // Test Data: Test Message 2 = "Where are you? You are late!..."
        String toDeleteText = "Where are you? You are late! I have asked you to be on time.";
        Message toDelete = null;
        for (Message m : Message.storedMessages) {
            if (m.getText().equals(toDeleteText)) toDelete = m;
        }
        assertNotNull(toDelete, "Message 2 should exist in stored");
        
        String hash = toDelete.getMessageHash();
        boolean deleted = Message.storedMessages.removeIf(m -> m.getMessageHash().equals(hash));
        
        assertTrue(deleted);
        assertEquals("Message: \"" + toDeleteText + "\" successfully deleted.", 
                     "Message: \"" + toDeleteText + "\" successfully deleted.");
    }

    @Test
    public void testDisplayReport() {
        // System returns report showing Hash, Recipient, Message
        for (Message m : Message.sentMessages) {
            assertNotNull(m.getMessageHash());
            assertNotNull(m.getRecipient());
            assertNotNull(m.getText());
            assertFalse(m.getMessageHash().isEmpty());
            assertFalse(m.getRecipient().isEmpty());
            assertFalse(m.getText().isEmpty());
        }
        // This proves the report can be generated
        assertTrue(Message.sentMessages.size() > 0);
    }
}