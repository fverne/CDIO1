package TUI;

import data.IUserDAO;

import java.util.Scanner;

public class TUI implements ITUI {
    Scanner sc;
    private IUserDAO data;

    public TUI(IUserDAO data) {
        this.data = data;
        sc = new Scanner(System.in);
    }

    @Override
    public void showMenu() {
        sc = new Scanner(System.in);
        System.out.println("1. Opret bruger");
        System.out.println("2. Vis brugere");
        System.out.println("3. Opdater brugere");
        System.out.println("4. Slet bruger");
        System.out.println("5. Ret bruger");
        System.out.println("6. Afslut program");
        System.out.print("Indtast valg: ");
        int userSelection = sc.nextInt();

        switch (userSelection) {
            case 1:
                addUser();
                break;
            case 2:
                showUser();
                break;
            case 3:
                updateUser();
                break;
            case 4:
                deleteUser();
                break;
            case 5:
                editUser();
                break;
            case 6:
                System.out.println("Program afsluttes");
                System.exit(0);
                break;
            default:
                showMenu();
        }
    }

    @Override
    public void addUser(){

    }

    @Override
    public void showUser(){

    }

    @Override
    public void updateUser() {

    }

    @Override
    public void editUser() {

    }

    @Override
    public void deleteUser() {

    }
}
