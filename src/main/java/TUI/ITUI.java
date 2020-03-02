package TUI;

import data.IUserDAO;

public interface ITUI {

    void showMenu() throws IUserDAO.DALException;
    void addUser() throws IUserDAO.DALException;
    void showUser() throws IUserDAO.DALException;
    void updateUser();
    void editUser();
    void deleteUser();
    void endProgram();

}
