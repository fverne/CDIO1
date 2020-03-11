package funktionalitet;

import TUI.TUI;
import codegenerator.Codegenerator;
import data.IUserDAO;
import data.UserDAODB;
import data.UserDAODISK;
import datatransfer.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller implements iController {
    IUserDAO data;

    public Controller() throws IUserDAO.DALException {
        //only run one of these at a time!
        //this.data = new UserDAO(new Codegenerator());
        this.data = new UserDAODISK();
        //this.data = newUserDAODB();
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
        //lav objekt ud fra userID og send til userDAO
        Scanner sc = new Scanner(System.in);
        TUI.displayText("Indtast fornavn: ");
        String firstName = sc.next();
        TUI.displayText("Indtast efternavn: ");
        String lastName = sc.next();
        TUI.displayText("Indtast cpr nr: ");
        String cpr = sc.next();
        String userName = firstName+" "+lastName;
        String ini = String.valueOf(firstName.charAt(0)+lastName.charAt(0));
        ArrayList<String> roles = new ArrayList<>();
        TUI.displayText("Vælg roller\n1. Pharma\n2.dsadsa,\n3.dsadsa,\n4.dasdsa");
        int userSelection = sc.nextInt();
        switch (userSelection){
            case 1:
                roles.add("Pharma");
                break;
            case 2:
                roles.add("nr2");
                break;
            case 3:
                roles.add("nr3");
                break;
            case 4:
                roles.add("nr 4");
                break;
        }
        TUI.displayText("Indtast kodeord: ");
        String password = sc.next();
        UserDTO user = new UserDTO(userId, userName,ini,roles,password,cpr);
        this.data.updateUser(user);
    }



    //virker
    public void createUser(UserDTO user) throws IUserDAO.DALException {
        this.data.createUser(user);
    }

    //virker
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
