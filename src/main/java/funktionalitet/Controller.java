package funktionalitet;

import data.*;
import datatransfer.UserDTO;

import java.io.*;
import java.util.Scanner;

public class Controller implements iController {
    IUserDAO data;

    public Controller() {
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
}
