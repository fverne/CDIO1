package funktionalitet;

import TUI.TUI;
import codegenerator.Codegenerator;
import data.IUserDAO;
import data.UserDAODB;
import datatransfer.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller implements iController {
    private IUserDAO data;

    public Controller() {
        //only run one of these at a time!
//        this.data = new UserDAO(new Codegenerator());
        //this.data = new UserDAODISK();
        this.data = new UserDAODB();
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
        ArrayList<String> roles = new ArrayList<>();
        TUI.displayText("Indtast enkeltvist roller. (max 4) Afslut med \"end\". \n" +
                "Mulige roller: Admin, Pharmacist, Foreman, Operator");
        for (int i = 0; i < 4; i++) {
            roles.add(sc.next());
            if (roles.get(i).equals("end")) {
                roles.remove(i);
                break;
            }
        }
        while(!checkRoles(roles)){ //checking if roles are good if not repeat process of entering roles
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
        TUI.displayText(s);
        return sc.next();
    }

    public void createUser(UserDTO user) throws IUserDAO.DALException {
        this.data.createUser(user);
    }

    @Override
    public List<UserDTO> getUserList() throws IUserDAO.DALException {
        return data.getUserList();
    }

    /*
    ***
    ALL CHECKS RETURN TRUE IF THE VALUES ARE GOOD
    ***
     */

    public boolean checkID(int id) throws IUserDAO.DALException {
        if (!(id >= 11 && id <= 99)) {
            return false; //ID is not within boundaries
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
        Codegenerator passGen = new Codegenerator();
        return passGen.generateCode();
    }
}
