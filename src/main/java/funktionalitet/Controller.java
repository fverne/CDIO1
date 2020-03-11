package funktionalitet;

import data.*;
import datatransfer.UserDTO;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Controller implements iController {
    IUserDAO data;

    public Controller() {
        this.data = new UserDAOTXT();
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
