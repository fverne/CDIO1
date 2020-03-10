package TUI;

import data.IUserDAO;
import data.UserDAO;
import datatransfer.UserDTO;
import funktionalitet.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TUI implements ITUI{
    Scanner sc;
    private Controller cont;


    public TUI() {
        this.cont = new Controller();
        sc = new Scanner(System.in);
    }

    public void showMenu() throws IOException {
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
                cont.addUser();
                break;
            case 2:
                System.out.print("Indtast ID: ");
                String ID = sc.next();
                try {
                    cont.showUser(ID);
                } catch (IUserDAO.DALException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                //updateUser();
                break;
            case 4:
                //deleteUser();
                break;
            case 5:
                //editUser();
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
    public void addUser() {

    }

    @Override
    public void showUser() {

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
