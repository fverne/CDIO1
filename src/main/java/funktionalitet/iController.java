package funktionalitet;

import data.IUserDAO;
import datatransfer.UserDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface iController {

    void addUser() throws IOException;

    UserDTO showUser(int userId) throws IUserDAO.DALException;

    void deleteUser(int userId) throws IOException;

    void updateUser(int userId);
}
