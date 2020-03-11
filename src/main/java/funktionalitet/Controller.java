package funktionalitet;

import codegenerator.Codegenerator;
import data.*;
import datatransfer.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller implements iController {
    IUserDAO data;

    public Controller() {
        //only 1 at a time!
        this.data = new UserDAO(new Codegenerator());
        this.data = new UserDAOTXT();
    }

    public void addUser(UserDTO user) throws IUserDAO.DALException {
        this.data.createUser(user);
    }


    @Override
    public UserDTO showUser(int userId) throws IUserDAO.DALException {
        System.out.println(this.data.getUser(userId).toString());
        return this.data.getUser(userId);
    }

    @Override
    public void deleteUser(int userId) throws IUserDAO.DALException {
        this.data.getUser(userId).deleteUser();
    }


    @Override
    public void updateUser(int userId) throws IUserDAO.DALException {
        this.data.updateUser(data.getUser(userId));
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
