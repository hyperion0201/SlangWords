import java.util.Scanner;

public class Main {
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  

    public static void main(String[] args) {
        var slang = new SlangWord();
        var scanner = new Scanner(System.in);
        try {
            slang._initializeSession();
            while (true) {

                System.out.println("================================");
                System.out.println("MAIN MENU :");
                System.out.println("1. Search by slang word");
                System.out.println("2. Search by definition");
                System.out.println("3. Search history ");
                System.out.println("4. Add new slang word");
                System.out.println("5. Edit a slang word");
                System.out.println("6. Delete a slang word");
                System.out.println("7. Reset slang / Undo all changes");
                System.out.println("8. Random a slang word");
                System.out.println("9. Random a slang question");
                System.out.println("10. Random a definition question");
                System.out.println("0. Exit");
                System.out.println("================================");

                System.out.print("==> Select option : ");
                int parentOption = scanner.nextInt();
                switch (parentOption) {
                    case 1: {
                        clearScreen();
                        System.out.print("Input slang word: ");
                        var query = scanner.next();
                        slang.SearchBySlang(query);
                        System.in.read();
                        break;
                    }
                    default: {
                        break;

                    }
                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
            return;
        }  
    }
}