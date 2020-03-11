package funktionalitet;

import data.IUserDAO;
import datatransfer.UserDTO;

import java.io.IOException;

interface iController {


    void addUser(UserDTO user) throws IUserDAO.DALException;

    UserDTO showUser(int userId) throws IUserDAO.DALException;

    void deleteUser(int userId) throws IOException, IUserDAO.DALException;

    void updateUser(int userId) throws IUserDAO.DALException;

}
