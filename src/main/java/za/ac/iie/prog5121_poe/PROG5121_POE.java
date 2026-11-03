package za.ac.iie.prog5121_poe;

import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;

public class PROG5121_POE {

    private static final String JSON_FILE = "storedMessages.json";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        String username = "", password = "", cell = "";

        // Load any previously stored messages from JSON at startup
        loadStoredMessagesFromJson();
        Message.populateTestData();

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1) Register User");
            System.out.println("2) Registration Login");
            System.out.println("3) Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine();

            if (choice.equals("1")) {
                System.out.print("Enter username (must contain _ and <=5 chars): ");
                username = sc.nextLine();
                System.out.print("Enter password (8 chars, capital, number, special): ");
                password = sc.nextLine();
                System.out.print("Enter SA cell (+27... 12 chars): ");
                cell = sc.nextLine();
                System.out.println(login.registerUser(username, password, cell));

            } else if (choice.equals("2")) {
                System.out.print("Enter username: ");
                String u = sc.nextLine();
                System.out.print("Enter password: ");
                String p = sc.nextLine();
                
                if (login.loginUser(u, p)) {
                    System.out.println("Welcome " + u + ", it is great to see you again.");
                    System.out.println("Welcome to QuickChat.");
                    System.out.print("How many messages do you wish to send? ");
                    int howMany = Integer.parseInt(sc.nextLine());
                    int sentCount = 0;
                    int msgNumber = 0;
                    String qcChoice = "";
                    
                    while (!qcChoice.equals("5")) {
                        System.out.println("\n--- QuickChat MENU ---");
                        System.out.println("1) Send Messages");
                        System.out.println("2) Show recently sent messages");
                        System.out.println("3) Display total sent");
                        System.out.println("4) Stored Messages");
                        System.out.println("5) Quit");
                        System.out.print("Choose: ");
                        qcChoice = sc.nextLine();
                        
                        if (qcChoice.equals("1")) {
                            if (sentCount >= howMany) {
                                System.out.println("Limit reached. Total number of messages sent: " + sentCount);
                                continue;
                            }
                            msgNumber++;
                            System.out.print("Enter recipient (+27... max 13): ");
                            String recip = sc.nextLine();
                            Message mCheck = new Message(msgNumber, recip, "test");
                            while (!mCheck.checkRecipientCell()) {
                                System.out.println("Cell number incorrectly formatted or no int code (+27).");
                                System.out.print("Enter again: ");
                                recip = sc.nextLine();
                                mCheck = new Message(msgNumber, recip, "test");
                            }
                            System.out.print("Enter message (max 250): ");
                            String txt = sc.nextLine();
                            Message msg = new Message(msgNumber, recip, txt);
                            while (!msg.checkMessageLength()) {
                                System.out.println("Too long by " + (txt.length() - 250));
                                System.out.print("Enter again: ");
                                txt = sc.nextLine();
                                msg = new Message(msgNumber, recip, txt);
                            }
                            
                            String hash = msg.createMessageHash();
                            System.out.println("MessageID: " + msg.getMessageID());
                            System.out.println("Message Hash: " + hash);
                            
                            System.out.println("What do you want to do?");
                            System.out.println("1) Send Message");
                            System.out.println("2) Disregard Message");
                            System.out.println("3) Store Message to send later");
                            System.out.print("Choose: ");
                            String sendChoice = sc.nextLine();
                            int opt = Integer.parseInt(sendChoice);
                            
                            String result = msg.sendMessageOption(opt);
                            System.out.println(result);
                            
                            if (opt == 1) {
                                sentCount++;
                            }
                            if (opt == 3) {
                                saveStoredMessagesToJson();
                                System.out.println("Message stored to " + JSON_FILE);
                            }

                        } else if (qcChoice.equals("2")) {
                            if (Message.sentMessages.isEmpty()) {
                                System.out.println("No sent messages yet.");
                            } else {
                                for (Message m : Message.sentMessages) {
                                    System.out.println(m.getMessageID() + " | " + m.getRecipient() + " | " + m.getText());
                                }
                            }
                        } else if (qcChoice.equals("3")) {
                            System.out.println("Total sent: " + Message.returnTotalMessages());
                            
                        } else if (qcChoice.equals("4")) {
                            handleStoredMessagesMenu(sc);
                        }
                    }
                } else {
                    System.out.println("Login failed.");
                }

            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                break;
            }
        }
        System.out.println("Total number of messages sent: " + Message.sentMessages.size());
        sc.close();
    }

    private static void handleStoredMessagesMenu(Scanner sc) {
        ArrayList<Message> sentMessagesArray = Message.sentMessages;
        ArrayList<Message> disregardedArray = Message.disregardedMessages;
        ArrayList<Message> storedMessagesArray = Message.storedMessages;
        ArrayList<String> messageHashesArray = new ArrayList<>();
        ArrayList<String> messageIDsArray = new ArrayList<>();

        for (Message m : storedMessagesArray) {
            messageHashesArray.add(m.getMessageHash());
            messageIDsArray.add(m.getMessageID());
        }

        ArrayList<Message> jsonMessages = loadStoredMessagesFromJson();
        if (!jsonMessages.isEmpty()) {
            System.out.println("Loaded " + jsonMessages.size() + " messages from " + JSON_FILE);
        }

        String subChoice = "";
        while (!subChoice.equals("7")) {
            System.out.println("\n--- Stored Messages MENU (Part 2) ---");
            System.out.println("a) Display sender and recipient of all stored messages");
            System.out.println("b) Display the longest stored message");
            System.out.println("c) Search for a message ID and display recipient and message");
            System.out.println("d) Search for all messages for a particular recipient");
            System.out.println("e) Delete a message using message hash");
            System.out.println("f) Display a report of all stored messages");
            System.out.println("7) Back to QuickChat MENU");
            System.out.print("Choose (a-f or 7): ");
            subChoice = sc.nextLine().toLowerCase();

            switch (subChoice) {
                case "a":
                    if (storedMessagesArray.isEmpty()) {
                        System.out.println("No stored messages.");
                    } else {
                        System.out.println("\n--- Sender and Recipient ---");
                        for (Message m : storedMessagesArray) {
                            System.out.println("Recipient: " + m.getRecipient() + " | Message: " + m.getText());
                        }
                    }
                    break;
                case "b":
                    if (storedMessagesArray.isEmpty()) {
                        System.out.println("No stored messages.");
                    } else {
                        Message longest = storedMessagesArray.get(0);
                        for (Message m : storedMessagesArray) {
                            if (m.getText().length() > longest.getText().length()) {
                                longest = m;
                            }
                        }
                        System.out.println("Longest message: " + longest.getText());
                        System.out.println("Length: " + longest.getText().length());
                    }
                    break;
                case "c":
                    System.out.print("Enter Message ID to search: ");
                    String searchID = sc.nextLine();
                    boolean foundID = false;
                    for (Message m : storedMessagesArray) {
                        if (m.getMessageID().equals(searchID)) {
                            System.out.println("Found - Recipient: " + m.getRecipient() + " | Message: " + m.getText());
                            foundID = true;
                            break;
                        }
                    }
                    if (!foundID) {
                        System.out.println("Message ID not found.");
                    }
                    break;
                case "d":
                    System.out.print("Enter recipient to search (e.g. +27718693002): ");
                    String searchRecip = sc.nextLine();
                    boolean foundRecip = false;
                    for (Message m : storedMessagesArray) {
                        if (m.getRecipient().equals(searchRecip)) {
                            System.out.println("Recipient: " + m.getRecipient() + " | Message: " + m.getText());
                            foundRecip = true;
                        }
                    }
                    if (!foundRecip) {
                        System.out.println("No messages found for that recipient.");
                    }
                    break;
                case "e":
                    System.out.print("Enter Message Hash to delete: ");
                    String delHash = sc.nextLine();
                    boolean deleted = false;
                    for (int i = 0; i < storedMessagesArray.size(); i++) {
                        if (storedMessagesArray.get(i).getMessageHash().equals(delHash)) {
                            storedMessagesArray.remove(i);
                            saveStoredMessagesToJson();
                            System.out.println("Message with hash " + delHash + " deleted.");
                            deleted = true;
                            break;
                        }
                    }
                    if (!deleted) {
                        System.out.println("Hash not found.");
                    }
                    break;
                case "f":
                    if (storedMessagesArray.isEmpty()) {
                        System.out.println("No stored messages.");
                    } else {
                        System.out.println("\n--- Full Report of Stored Messages ---");
                        for (Message m : storedMessagesArray) {
                            System.out.println("MessageID: " + m.getMessageID());
                            System.out.println("Message Hash: " + m.getMessageHash());
                            System.out.println("Recipient: " + m.getRecipient());
                            System.out.println("Message: " + m.getText());
                            System.out.println("---------------------------");
                        }
                    }
                    break;
            }
        }
    }

    public static void saveStoredMessagesToJson() {
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            writer.write("[\n");
            for (int i = 0; i < Message.storedMessages.size(); i++) {
                Message m = Message.storedMessages.get(i);
                writer.write("  {\n");
                writer.write("    \"messageID\": \"" + m.getMessageID() + "\",\n");
                writer.write("    \"messageNumber\": " + m.getMessageNumber() + ",\n");
                writer.write("    \"recipient\": \"" + m.getRecipient() + "\",\n");
                writer.write("    \"message\": \"" + m.getText().replace("\"", "\\\"") + "\",\n");
                writer.write("    \"messageHash\": \"" + m.getMessageHash() + "\"\n");
                writer.write("  }" + (i < Message.storedMessages.size() - 1 ? "," : "") + "\n");
            }
            writer.write("]\n");
        } catch (IOException e) {
            System.out.println("Error saving JSON: " + e.getMessage());
        }
    }

    public static ArrayList<Message> loadStoredMessagesFromJson() {
        ArrayList<Message> list = new ArrayList<>();
        try {
            Path path = Paths.get(JSON_FILE);
            if (!Files.exists(path)) {
                return list;
            }
            String content = Files.readString(path);
            if (content.trim().isEmpty() || content.trim().equals("[]")) {
                return list;
            }
            String[] objects = content.split("\\},\\s*\\{");
            for (String obj : objects) {
                try {
                    String id = extractJsonValue(obj, "messageID");
                    String numStr = extractJsonValue(obj, "messageNumber");
                    String recip = extractJsonValue(obj, "recipient");
                    String msg = extractJsonValue(obj, "message");
                    String hash = extractJsonValue(obj, "messageHash");
                    if (id != null && recip != null && msg != null) {
                        int num = 0;
                        try { num = Integer.parseInt(numStr); } catch (Exception ex) {}
                        Message m = new Message(id, num, recip, msg, hash);
                        boolean exists = false;
                        for (Message existing : Message.storedMessages) {
                            if (existing.getMessageID().equals(id)) {
                                exists = true;
                                break;
                            }
                        }
                        if (!exists) {
                            Message.storedMessages.add(m);
                        }
                        list.add(m);
                    }
                } catch (Exception ex) {}
            }
        } catch (Exception e) {
            System.out.println("Error loading JSON: " + e.getMessage());
        }
        return list;
    }

    private static String extractJsonValue(String json, String key) {
        try {
            String search = "\"" + key + "\"";
            int start = json.indexOf(search);
            if (start == -1) return null;
            int colon = json.indexOf(":", start);
            if (colon == -1) return null;
            if (key.equals("messageNumber")) {
                int comma = json.indexOf(",", colon);
                int brace = json.indexOf("}", colon);
                int end = (comma != -1 && comma < brace) ? comma : brace;
                if (end == -1) end = json.length();
                return json.substring(colon + 1, end).trim().replaceAll("[^0-9]", "");
            }
            int firstQuote = json.indexOf("\"", colon + 1);
            if (firstQuote == -1) return null;
            int secondQuote = json.indexOf("\"", firstQuote + 1);
            while (secondQuote != -1 && json.charAt(secondQuote - 1) == '\\') {
                secondQuote = json.indexOf("\"", secondQuote + 1);
            }
            if (secondQuote == -1) return null;
            return json.substring(firstQuote + 1, secondQuote);
        } catch (Exception e) {
            return null;
        }
    }
}