package funktionalitet;

import TUI.TUI;
import data.IUserDAO;
import data.UserDAO;
import data.UserDAODB;
import data.UserDAODISK;
import datatransfer.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller implements iController {
    private IUserDAO data;

    public Controller() {
        //only run one of these at a time!
        System.out.println("Tast 1 for implementering uden persistent data");
        System.out.println("Tast 2 for implementering med persistent data på disk");
        System.out.println("tast 3 for implementering med persistent data på database");
        Scanner scanner = new Scanner(System.in);
        boolean retry = true;
        do {
            switch (scanner.nextInt()) {
                case 1:
                    this.data = new UserDAO(new Codegenerator());
                    retry = false;
                    break;
                case 2:
                    this.data = new UserDAODISK();
                    retry = false;
                    break;
                case 3:
                    this.data = new UserDAODB();
                    retry = false;
                    break;
                default:
                    System.out.println("Forket data input");
            }
        }  while (retry);
    }

    @Override
    public UserDTO getUser(int userId) throws IUserDAO.DALException {
        return this.data.getUser(userId);
    }

    @Override
    public void deleteUser(int userId) throws IUserDAO.DALException {
        this.data.deleteUser(userId);
    }

    @Override
    public void updateUser(int userId) throws IUserDAO.DALException {
        //Creating a new user objekt with all new parameters and send it to updateUser in data.
        //in data this new user will replace the old user.
        //could make it so not all parameters HAVE to be written again...
        TUI.displayText("Du har valgt at opdaterer følgende bruger: ");
        TUI.displayObject(getUser(userId));
        Scanner sc = new Scanner(System.in);
        String userName;
        String cpr;
        String password;
        String ini;
        do {
            String firstName = getInput(sc, "Indtast fornavn: ");
            String lastName = getInput(sc, "Indtast efternavn: ");
            userName = firstName + " " + lastName;
            ini = ("" + firstName.charAt(0) + lastName.charAt(0));
        } while (!checkUserName(userName));
        do {
            cpr = getInput(sc, "Indtast cpr nr: ");
        } while (!checkCPR(cpr));
        ArrayList<String> roles = setNewRoles(sc);
        password = getInput(sc, "Indtast nyt kodeord: ");
        UserDTO user = new UserDTO(userId, userName, ini, roles, password, cpr);
        this.data.updateUser(user);
    }

    private ArrayList<String> setNewRoles(Scanner sc) {
        //method for setting/changing roles of object.
        //all roles must be typed correctly.
        ArrayList<String> roles = new ArrayList<>();
        TUI.displayText("Indtast enkeltvist roller. (max 4) Afslut med \"end\". \n" +
                "Mulige roller: Admin, Pharmacist, Foreman, Operator");
        //first input
        for (int i = 0; i < 4; i++) {
            roles.add(sc.next());
            if (roles.get(i).equals("end")) {
                roles.remove(i);
                break;
            }
        }
        //checking if roles are good if not repeat process of entering roles
        while(!checkRoles(roles)){
            TUI.displayText("Mindst 1 ugyldig rolle. Indtast roller igen.");
            roles.clear();
            for (int i = 0; i < 4; i++) {
                roles.add(sc.next());
                if (roles.get(i).equals("end")) {
                    roles.remove(i);
                    break;
                }
            }
        }
        return roles;
    }

    private String getInput(Scanner sc, String s) {
        //method for printing something on the TUI and immediately taking input afterwards
        TUI.displayText(s);
        return sc.next();
    }

    public void createUser(UserDTO user) throws IUserDAO.DALException {
        //sends final new user object to data where it is added.
        // input/output during creation is in TUI
        this.data.createUser(user);
    }

    @Override
    public List<UserDTO> getUserList() throws IUserDAO.DALException {
        return data.getUserList();
    }

    /*
    ***
    ALL CHECK-METHODS RETURN TRUE IF THE VALUES ARE GOOD
    ***
     */

    public boolean checkID(int id) throws IUserDAO.DALException {
        if (!(id >= 11 && id <= 99)) {
            return false; //ID is not within boundaries 11-99
        } else {
            for (UserDTO tempuser : data.getUserList()) { //Is ID used before?
                if (tempuser.getUserId() == id) {
                    return false; //ID is already in use
                }
            }
            return true; //ID is good
        }
    }

    public boolean checkCPR(String cpr) {
        if (cpr.length() != 10) {
            return false; //wrong length
        }
        for (int i = 0; i < cpr.length(); i++) {
            if (cpr.charAt(i) > '9' || cpr.charAt(i) < '0') {
                return false; //cannot contain characters other than numbers
            }
        }
        return true; //CPR good
    }

    public boolean checkUserName(String userName) {
        return userName.length() >= 2 && userName.length() <= 20; //wrong length must be 2-20 characters
    }

    public boolean checkRoles(ArrayList<String> roles) {
        if (roles.isEmpty()) {
            return false; //user must have a role
        }
        for (String role : roles) {
            if (!(role.equals("Pharmacist") ||
                    role.equals("Admin") ||
                    role.equals("Foreman") ||
                    role.equals("Operator"))) {

                return false; //atleast one role is not viable
            }
        }
        return true;
    }

    public String generatePassword() {
        //returns a randomly generated password with the proper password format
        Codegenerator passGen = new Codegenerator();
        return passGen.generateCode();
    }
}
