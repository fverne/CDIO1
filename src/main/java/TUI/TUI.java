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
    public void createUser() throws IUserDAO.DALException {
        Scanner sc = new Scanner(System.in);
        String userName;
        String userIni = "";
        int userId;
        //INPUT ID AND CHECK
        displayText("Indtast unikt brugerID (integer i interval 11-99");
        userId = Integer.parseInt(sc.nextLine());
        while(!cont.checkID(userId)){
            displayText("Ugyldigt brugerID. Indtast nyt brugerID");
            userId = Integer.parseInt(sc.nextLine());
        }

        //INPUT USERNAME AND CHECK
        displayText("Indtast fornavn: ");
        String fornavn = sc.nextLine();
        displayText("Indtast efternavn: ");
        String efternavn = sc.nextLine();
        userName = fornavn + " " + efternavn;
        while(!cont.checkUserName(userName)){
            displayText("Ugyldigt brugernavn. Skal være mellem 2 - 20 karakterer. Indtast nyt brugernavn.");
            userName = sc.nextLine();
        }
        displayText("Brugernavn er: "+ userName);
        userIni += fornavn.charAt(0) + efternavn.charAt(0);
        displayText("Initiale er: " + userIni);
        //behøver ikke check, når initialer autogenereres til 2 bogstaver

        //INPUT AND CHECK ROLES
        ArrayList<String> roles = new ArrayList<>();
        displayText("Indtast enkeltvist roller. (max 4) Afslut med \"end\". \n" +
                "Mulige roller: Admin, Pharmacist, Foreman, Operator");
        for (int i = 0; i < 4; i++) {
            roles.add(sc.nextLine());
            if (roles.get(i).equals("end")) {
                roles.remove(i);
                break;
            }
        }
        while(!cont.checkRoles(roles)){
            displayText("En eller flere roller er ugyldige. Indtast dem igen ");
            roles.clear();
            for (int i = 0; i < 4; i++) {
                roles.add(sc.nextLine());
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
        while(!cont.checkCPR(cpr)){
            displayText("Ugyldigt cpr. Indtast igen. Skal være 10 cifre. Ingen bogstaver");
            cpr = sc.nextLine();
        }

        //CREATING OBJECT
        try {
            cont.createUser(new UserDTO(userId, userName, userIni, roles, password, cpr));
        } catch (IUserDAO.DALException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showUser() {
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
            displayObject(cont.getUserList().get(i));
        }
    }

    public static void displayText(String str) {
        System.out.println(str);
    }


    public static void displayObject(UserDTO user) {
        System.out.println(user);
    }
}