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
        System.out.println("1. Opret bruger");
        System.out.println("2. Vis bruger");
        System.out.println("3. Vis alle brugere");
        System.out.println("4. Slet bruger");
        System.out.println("5. Ret bruger");
        System.out.println("6. Afslut program");
        System.out.print("Indtast valg: ");
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
                System.out.println("Program afsluttes");
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
        int userId = 1;
        System.out.print("Indtast fornavn: ");
        String fornavn = sc.next();
        System.out.print("Indtast efternavn: ");
        String efternavn = sc.next();

        userIni = String.valueOf(fornavn.charAt(0) + efternavn.charAt(0));
        userName = fornavn + " " + efternavn;
        ArrayList<String> roles = null;
        String password = null;
        System.out.print("Indtast cpr: ");
        String cpr = sc.next();
        try {
            cont.createUser(user = new UserDTO(userId, userName, userIni, roles, password, cpr));
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUser() {
        System.out.print("Indtast ID for at vise bruger: ");
        int ID = sc.nextInt();
        try {
            System.out.println(cont.getUser(ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void updateUser() throws IUserDAO.DALException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Indtast bruger ID");
        int id = sc.nextInt();
        System.out.println("Hvilke informationer vil du redigerer: ");
        System.out.println("1. Navn");
        System.out.println("2. CPR");
        System.out.println("3. Roller");
        System.out.println("4. Password");
        int userSelection = sc.nextInt();
        switch (userSelection) {
            case 1:
                System.out.println("Indtast nyt navn: ");
                String newName = sc.next();
                cont.getUser(id).setUserName(newName);
                break;
            case 2:
                System.out.println("Indtast nyt CPR: ");
                String cpr = sc.next();
                cont.getUser(id).setCpr(cpr);
                break;

            case 3:
//                System.out.println("Indtast ny rolle: ");
//                String rolle = sc.next();
//                cont.getUser(id).setRoles(rolle);
                break;

            case 4:
                System.out.println("Indtast nyt password ");
                String password = sc.next();
                cont.getUser(id).setUserName(password);
                break;
        }
        sc.close();
    }

    @Override
    public void deleteUser() throws IUserDAO.DALException {
        System.out.print("Indtast ID for at slette bruger: ");
        int ID = sc.nextInt();
        cont.getUser(ID).deleteUser();
    }

    public void showUserList() throws IUserDAO.DALException {
        System.out.println(cont.getUserList());
    }
}