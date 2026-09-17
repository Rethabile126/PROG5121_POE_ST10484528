package za.ac.iie.prog5121_poe;
import java.util.Scanner;

public class PROG5121_POE {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Login login = new Login();
        String username = "", password = "", cell = "";
        
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
                } else {
                    System.out.println("Username or password incorrect, please try again.");
                }
                
            } else if (choice.equals("3")) {
                System.out.println("Goodbye!");
                break;
            }
        }
        sc.close();
    }
}