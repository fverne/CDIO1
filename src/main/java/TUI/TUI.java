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
        System.out.println("3. Slet bruger");
        System.out.println("4. Ret bruger");
        System.out.println("5. Afslut program");
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
        System.out.println("Ikke implementeret");
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Indtast ID for at opdatere bruger: ");
//        int ID = sc.nextInt();
//        cont.updateUser(ID);
    }

    @Override
    public void deleteUser() throws IUserDAO.DALException {
        System.out.println("Ikke implementeret");
//        System.out.print("Indtast ID for at slette bruger: ");
//        int ID = sc.nextInt();
//        cont.deleteUser(ID);
    }
}