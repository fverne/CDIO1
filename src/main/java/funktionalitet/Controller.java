package funktionalitet;

import codegenerator.Codegenerator;
import TUI.TUI;
import data.IUserDAO;
import data.UserDAO;
import data.UserDAODISK;
import datatransfer.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controller implements iController {
    IUserDAO data;

    public Controller() throws IUserDAO.DALException {
        //only run one of these at a time!
        this.data = new UserDAO(new Codegenerator());
        //this.data = new UserDAODISK();
        //this.data = newUserDAODB();
    }

    public void createUser(UserDTO user) throws IUserDAO.DALException {
        this.data.createUser(user);
    }

    @Override
    public UserDTO getUser(int userId) throws IUserDAO.DALException {
        return this.data.getUser(userId);
    }

    @Override
    public List<UserDTO> getUserList() throws IUserDAO.DALException {
        return data.getUserList();
    }

    @Override
    public void deleteUser(int userId) throws IUserDAO.DALException {
        this.data.deleteUser(userId);
    }

    @Override
    public void updateUser(int userId) throws IUserDAO.DALException {
        Scanner sc = new Scanner(System.in);
        TUI.displayText("Hvilke informationer vil du redigerer: \n1. Navn\n2. Cpr\n3. Roller\n4. Password");
        int userSelection = sc.nextInt();
        switch (userSelection) {
            case 1:
                TUI.displayText("Indtast navn: ");
                String newName = sc.next();
                data.getUser(userId).setUserName(newName);
                break;
            case 2:
                TUI.displayText("Indtast cpr: ");
                String cpr = sc.next();
                getUser(userId).setCpr(cpr);
                break;

            case 3:
                this.data.getUser(userId).getRoles().clear();
                TUI.displayText("Tast y/n for at tilvælge roller: ");
                ArrayList<String> roles = new ArrayList<>();
                if (sc.next().equals("y")) {
                    roles.add("Admin");
                }
                TUI.displayText("Pharmacist");
                if (sc.next().equals("y")) {
                    roles.add("Pharmacist");
                }
                TUI.displayText("Foreman");
                if (sc.next().equals("y")) {
                    roles.add("Foreman");
                }
                TUI.displayText("Operator");
                if (sc.next().equals("y")) {
                    roles.add("Operator");
                }
                this.data.getUser(userId).setRoles(roles);
                break;

            case 4:
                TUI.displayText("Indtast password: ");
                String password = sc.next();
                getUser(userId).setUserName(password);
                break;
            default:
                break;
        }
        sc.close();
        this.data.deleteUser(userId);
    }

    /*
    ***
    ALL CHECKS RETURN TRUE IF THE VALUES ARE GOOD
    ***
     */

    public boolean checkID(int id) throws IUserDAO.DALException{
        if(!(id >= 11 && id <= 99) ){
            return false; //ID is not within boundaries
        }
        else {
            for (UserDTO tempuser : data.getUserList()) { //Is ID used before?
                if (tempuser.getUserId() == id) {
                    return false; //ID is already in use
                }
            }
            return true; //ID is good
        }
    }

    public boolean checkCPR(String cpr){
        if(cpr.length() != 10){
            return false; //wrong length
        }
        for(int i = 0; i < cpr.length(); i++){
            if(cpr.charAt(i) > '9' || cpr.charAt(i) < '0'){
                return false; //cannot contain characters other than numbers
            }
        }
        return true; //CPR good
    }

    public boolean checkUserName(String userName){
        if(userName.length() < 2 || userName.length() > 20){
            return false; //wrong length must be 2-20 characters
        }
        return true;
    }

    public boolean checkRoles(ArrayList<String> roles){
        if(roles.isEmpty()){
            return false; //user must have a role
        }
        for(String role : roles){
            if(! (role.equals("Pharmacist") ||
                    role.equals("Admin") ||
                    role.equals("Foreman") ||
                    role.equals("Operator"))){

                return false; //atleast one role is not viable
            }
        }
        return true;
    }

    public String generatePassword(){
        Codegenerator passGen = new Codegenerator();
        return passGen.generateCode();
    }
}
