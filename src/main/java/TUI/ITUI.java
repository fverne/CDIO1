package TUI;

import data.IUserDAO;

import java.io.IOException;

public interface ITUI {

    void showMenu() throws IOException, IUserDAO.DALException;
    void createUser();
    void showUser();
    void updateUser() throws IUserDAO.DALException;
    void deleteUser() throws IUserDAO.DALException;

}
