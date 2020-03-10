package TUI;

import data.IUserDAO;
import data.UserDAO;
import datatransfer.UserDTO;
import funktionalitet.Controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class TUI{
    Scanner sc;
    private IUserDAO data;
    private Controller cont = new Controller();
    UserDTO user = new UserDTO();
    String ID;


    public TUI(IUserDAO data) {
        this.data = data;
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
                cont.addUser();
                break;
            case 2:
                System.out.print("Indtast ID: ");
                ID = sc.next();
                cont.showUser(ID);
                break;
            case 3:
                System.out.print("Indtast ID: ");
                ID = sc.next();
                cont.deleteUser(ID);
                break;
            case 4:
                //editUser();
                break;
            case 5:
                System.out.println("Program afsluttes");
                System.exit(0);
                break;
            default:
                showMenu();
        }
    }

}
