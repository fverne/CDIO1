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
        System.out.println("2. Vis bruger");
        System.out.println("3. Slet bruger");
        System.out.println("4. Ret bruger");
        System.out.println("5. Afslut program");
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
                deleteUser();
                break;
            case 4:
                updateUser();
                break;
            case 5:
                System.out.println("Program afsluttes");
                System.exit(0);
                break;
            default:
                showMenu();
        }
    }

    @Override
    public void addUser() {
        try {
            cont.addUser();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUser() {
        System.out.print("Indtast ID for at vise bruger: ");
        int ID = sc.nextInt();
        try {
            cont.showUser(ID);
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser() {
        System.out.print("Indtast ID for at opdatere bruger: ");
        int ID = sc.nextInt();
        cont.updateUser(ID);
    }

    @Override
    public void deleteUser() {
        System.out.print("Indtast ID for at slette bruger: ");
        int ID = sc.nextInt();
        cont.deleteUser(ID);
    }

}
