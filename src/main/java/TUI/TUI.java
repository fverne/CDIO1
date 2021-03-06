package TUI;

import data.IUserDAO;
import datatransfer.UserDTO;
import funktionalitet.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TUI implements ITUI {
    private Scanner sc;
    private Controller cont;


    public TUI() {
        this.cont = new Controller();
        sc = new Scanner(System.in);
    }

    public static void displayText(String str) {
        System.out.println(str);
    }

    public static void displayObject(UserDTO user) {
        System.out.println(user);
    }

    public void showMenu() throws IUserDAO.DALException {
        //switch based TUI-menu. input number decides which feature to run.

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
    public void createUser() throws IUserDAO.DALException {
        //all input and output here. all value checks happens in controller with check-methods
        Scanner sc = new Scanner(System.in);
        String userName;
        String userIni = "";
        int userId;
        //INPUT ID AND CHECK
        displayText("Indtast unikt brugerID (integer i interval 11-99");
        userId = Integer.parseInt(sc.nextLine());
        while (!cont.checkID(userId)) {
            displayText("Ugyldigt brugerID. Indtast nyt brugerID");
            userId = Integer.parseInt(sc.nextLine());
        }

        //INPUT USERNAME AND CHECK
        displayText("Indtast fornavn: ");
        String fornavn = sc.nextLine();
        displayText("Indtast efternavn: ");
        String efternavn = sc.nextLine();
        userName = fornavn + " " + efternavn;
        while (!cont.checkUserName(userName)) {
            displayText("Ugyldigt brugernavn. Skal være mellem 2 - 20 karakterer. Indtast nyt brugernavn.");
            userName = sc.nextLine();
        }
        displayText("Brugernavn er: " + userName);
        userIni = "" + fornavn.charAt(0) + efternavn.charAt(0);
        displayText("Initialer er: " + userIni);
        //behøver ikke check, når initialer autogenereres til 2 bogstaver

        //INPUT ROLES AND CHECK
        ArrayList<String> roles = new ArrayList<>();
        displayText("Indtast enkeltvist roller. (max 4) Afslut med \"end\". \n" +
                "Mulige roller: Admin, Pharmacist, Foreman, Operator");
        for (int i = 0; i < 4; i++) {
            roles.add(sc.next());
            if (roles.get(i).equals("end")) {
                roles.remove(i);
                break;
            }
        }
        while (!cont.checkRoles(roles)) {
            displayText("En eller flere roller er ugyldige. Indtast dem igen ");
            roles.clear();
            for (int i = 0; i < 4; i++) {
                roles.add(sc.next());
                if (roles.get(i).equals("end")) {
                    roles.remove(i);
                    break;
                }
            }
        }

        //GENERATING PASSWORD
        String password = cont.generatePassword();

        //INPUT CPR AND CHECK
        displayText("Indtast cpr: ");
        String cpr = sc.next();
        while (!cont.checkCPR(cpr)) {
            displayText("Ugyldigt cpr. Indtast igen. Skal være 10 cifre. Ingen bogstaver");
            cpr = sc.nextLine();
        }

        //CREATING USER WITH ENTERED VALUES
        try {
            cont.createUser(new UserDTO(userId, userName, userIni, roles, password, cpr));
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUser() {
        //input userID and return user
        displayText("Indtast ID for at vise bruger: ");
        int ID = sc.nextInt();
        try {
            displayObject(cont.getUser(ID));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser() throws IUserDAO.DALException {
        //input userID and access/change the users variables
        displayText("Indtast bruger ID");
        int id = sc.nextInt();
        cont.updateUser(id);
    }

    @Override
    public void deleteUser() throws IUserDAO.DALException {
        //input userID and user will be deleted if found
        displayText("Indtast ID for at slette bruger: ");
        int ID = sc.nextInt();
        cont.deleteUser(ID);
    }

    public void showUserList() throws IUserDAO.DALException {
        //outputs all users from data.
        List<UserDTO> userlist = cont.getUserList();
        if (userlist.size() == 0) {
            TUI.displayText("Listen er tom.");
        }
        for (int i = 0; i < userlist.size(); i++) {
            displayObject(userlist.get(i));

        }
    }
}