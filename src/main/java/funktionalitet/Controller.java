package funktionalitet;

import codegenerator.Codegenerator;
import data.*;
import datatransfer.UserDTO;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Controller implements iController {

    IUserDAO data;

    public Controller() {
        //only 1 data implementation at a time
        this.data = new UserDAO(new Codegenerator());//no save. Initializing 4 hardcoded users on creation.
       // this.data = new UserDAOTXT();// read/write to txt file on disk
    }

    public void createUser(UserDTO user) throws IUserDAO.DALException {
        this.data.createUser(user);
    }

    @Override
    public UserDTO getUser(int userId) throws IUserDAO.DALException {
        return this.data.getUser(userId);
    }

    @Override
    public List<UserDTO> getUserList() {
        return null;
    }

    @Override
    public void deleteUser(int userId) throws IUserDAO.DALException {
        this.data.getUser(userId).deleteUser();
    }


    @Override
    public void updateUser(int userId) throws IUserDAO.DALException {
        this.data.updateUser(data.getUser(userId));
    }
}
