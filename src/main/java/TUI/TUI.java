package TUI;

import data.IUserDAO;
import datatransfer.UserDTO;
import funktionalitet.Controller;

import java.util.ArrayList;
import java.util.Scanner;

public class TUI implements ITUI {
    Scanner sc;
    private Controller cont;
    UserDTO user;

    public TUI() throws IUserDAO.DALException {
        this.cont = new Controller();
        sc = new Scanner(System.in);
    }

    public void showMenu() throws IUserDAO.DALException {
        sc = new Scanner(System.in);
        displayText("1. Opret bruger");
        displayText("2. Vis bruger");
        displayText("3. Vis alle brugere");
        displayText("4. Slet bruger");
        displayText("5. Ret bruger");
        displayText("6. Afslut program");
        displayText("Indtast valg: ");
        int userSelection = sc.nextInt();

        switch (userSelection) {
            case 1:
                createUser();
                break;
            case 2:
                showUser();
                break;
            case 3:
                showUserList();
                break;
            case 4:
                deleteUser();
                break;
            case 5:
                updateUser();
                break;
            case 6:
                displayText("Program afsluttes");
                System.exit(0);
                break;
            default:
                showMenu();
        }
    }

    @Override
    public void createUser() {
        Scanner sc = new Scanner(System.in);
        String userName;
        String userIni;
        int userId;
        displayText("Indtast fornavn: ");
        String fornavn = sc.next();
        displayText("Indtast efternavn: ");
        String efternavn = sc.next();
        displayText("Indtast ønskede bruger ID(10-99): ");
        userId = sc.nextInt();

        userIni = String.valueOf(fornavn.charAt(0) + efternavn.charAt(0));
        userName = fornavn + " " + efternavn;
        ArrayList<String> roles = null;
        String password = null;
        displayText("Indtast cpr: ");
        String cpr = sc.next();
        try {
            cont.createUser(user = new UserDTO(userId, userName, userIni, roles, password, cpr));
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
        //sc.close();
    }

    @Override
    public void showUser() {
        displayText("Indtast ID for at vise bruger: ");
        int ID = sc.nextInt();
        try {
            System.out.println(cont.getUser(ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser() throws IUserDAO.DALException {
        displayText("Indtast bruger ID");
        int id = sc.nextInt();
        cont.updateUser(id);
    }

    @Override
    public void deleteUser() throws IUserDAO.DALException {
        displayText("Indtast ID for at slette bruger: ");
        int ID = sc.nextInt();
        cont.deleteUser(ID);
    }

    public void showUserList() throws IUserDAO.DALException {
        for (int i = 0; i < cont.getUserList().size(); i++) {
            printObject(cont.getUserList().get(i));
        }
    }

    public static void displayText(String str) {
        System.out.println(str);
    }


    public static void printObject(UserDTO user) {
        System.out.println(user);
    }
}