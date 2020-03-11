package TUI;

import data.IUserDAO;
import datatransfer.UserDTO;
import funktionalitet.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TUI implements ITUI {
    Scanner sc;
    private Controller cont;
    UserDTO user;


    public TUI() {
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

    public void addUser() throws IUserDAO.DALException {
        Scanner sc = new Scanner(System.in);
        String userName;
        String userIni;
        int userId;
        //INPUT ID AND CHECK
        System.out.println("Indtast unikt brugerID (integer i interval 11-99");
        userId = Integer.parseInt(sc.nextLine());
        while(!cont.checkID(userId)){
            System.out.println("Ugyldigt brugerID. Indtast nyt brugerID");
            userId = Integer.parseInt(sc.nextLine());
        }

        //INPUT USERNAME AND CHECK
        System.out.print("Indtast fornavn: ");
        String fornavn = sc.nextLine();
        System.out.print("Indtast efternavn: ");
        String efternavn = sc.nextLine();
        userName = fornavn + " " + efternavn;
        System.out.println("Brugernavn er: "+ userName);
        userIni = String.valueOf(fornavn.charAt(0) + efternavn.charAt(0));
        while(!cont.checkUserName(userName)){
            System.out.println("Ugyldigt brugernavn. Skal være mellem 2 - 20 karakterer. Indtast nyt brugernavn.");
            userName = sc.nextLine();
        }
        System.out.println("Initiale er: " + userIni);
        //behøver ikke check, når initialer autogenereres til 2 bogstaver

        //INPUT AND CHECK ROLES
        ArrayList<String> roles = new ArrayList<>();
        System.out.println("Indtast enkeltvist roller. (max 4) Afslut med \"end\". \n" +
                "Mulige roller: Admin, Pharmacist, Foreman, Operator");
        for (int i = 0; i < 4; i++) {
            roles.add(sc.nextLine());
            if (roles.get(i).equals("end")) {
                roles.remove(i);
                break;
            }
        }
        while(!cont.checkRoles(roles)){
            System.out.println("En eller flere roller er ugyldige. Indtast dem igen ");
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
        System.out.print("Indtast cpr: ");
        String cpr = sc.next();
        while(!cont.checkCPR(cpr)){
            System.out.println("Ugyldigt cpr. Indtast igen. Skal være 10 cifre. Ingen bogstaver");
            cpr = sc.nextLine();
        }

        //CREATING OBJECT
        try {
            cont.addUser(new UserDTO(userId, userName, userIni, roles, password, cpr));
        } catch (IUserDAO.DALException e) {
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
    public void updateUser() throws IUserDAO.DALException {
        Scanner sc = new Scanner(System.in);
        System.out.print("Indtast ID for at opdatere bruger: ");
        int ID = sc.nextInt();
        cont.updateUser(ID);
    }

    @Override
    public void deleteUser() throws IUserDAO.DALException {
        System.out.print("Indtast ID for at slette bruger: ");
        int ID = sc.nextInt();
        cont.deleteUser(ID);
    }
}