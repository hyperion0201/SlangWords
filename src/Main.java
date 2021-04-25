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
                    case 2: {
                        clearScreen();
                        System.out.print("Input definition keyword: ");
                        var query = scanner.next();
                        slang.SearchByDefinition(query);
                        System.in.read();
                        break;
                    }
                    case 3: {
                        clearScreen();
                        slang.displayHistory();
                        System.in.read();
                        break;
                    }
                    case 4: {
                        clearScreen();
                        System.out.print("Input new slang to add : ");
                        var slangToAdd = scanner.next();
                        var isSlangFound = slang.isSlangFound(slangToAdd);
                        if (isSlangFound) {
                            System.out.println("Found exist slang with the word you input." + "\n"
                                    + "Please type (Y) to override, (N) to add more definitions for exist slang, (C) to cancel: ");
                            var addOption = scanner.next();
                            switch (addOption.toLowerCase()) {
                                case "y": {
                                    System.out.print("Input new definition for exist slang : ");
                                    var newDef = scanner.next();
                                    slang.addNewSlangWord(slangToAdd, newDef, true);
                                    System.out.println("Extra definition for slang " + slangToAdd
                                            + " was added successfully.");
                                    break;
                                }
                                case "n": {
                                    System.out.print("Input new definition for exist slang : ");
                                    var newDef = scanner.next();
                                    slang.addNewSlangWord(slangToAdd, newDef, false);
                                    System.out.println("Slang " + slangToAdd + " was added successfully.");
                                    break;
                                }
                                case "c": {
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                        } else {
                            System.out.println("Input definition for slang : ");
                            var newDef = scanner.next();
                            slang.addNewSlangWord(slangToAdd, newDef, true);
                            System.out.println("New slang word was added successfully.");
                        }
                        System.in.read();
                        break;
                    }
                    case 5: {
                        clearScreen();
                        System.out.print("Input slang to edit: ");
                        var slangToEdit = scanner.next();
                        var slangFound = slang.isSlangFound(slangToEdit);
                        if (!slangFound) {
                            System.out.print("Slang is not found.");
                        } else {
                            System.out.print("Input new value for this slang: ");
                            var newSlang = scanner.next();
                            slang.editSlangWord(slangToEdit, newSlang);
                        }
                        System.in.read();
                        break;
                    }
                    case 6: {
                        clearScreen();
                        System.out.print("Input slang to delete: ");
                        var slangToDelete = scanner.next();
                        var slangFound = slang.isSlangFound(slangToDelete);
                        if (!slangFound) {
                            System.out.print("Slang is not found.");
                        } else {
                            System.out.print("Slang found. Type (Y) to delete, (N) to cancel: ");
                            var confirm = scanner.next();
                            switch (confirm.toLowerCase()) {
                                case "y": {
                                    slang.deleteSlangWord(slangToDelete);
                                    System.out.println("Slang was deleted successfully.");
                                    break;
                                }
                                case "n": {
                                    System.out.println("Cancelled.");
                                    break;
                                }
                                default: {
                                    break;
                                }
                            }
                        }
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