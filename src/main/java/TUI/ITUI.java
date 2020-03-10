package TUI;

import data.IUserDAO;

import java.io.IOException;

public interface ITUI {

    void showMenu() throws IOException, IUserDAO.DALException;
    void addUser();
    void showUser();
    void updateUser();
    void editUser();
    void deleteUser();

}
