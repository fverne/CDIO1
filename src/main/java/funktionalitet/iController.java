package funktionalitet;

import data.IUserDAO;
import datatransfer.UserDTO;

import java.io.IOException;

public interface iController {

    void addUser() throws IOException;

    UserDTO showUser(int userId) throws IUserDAO.DALException;

    void deleteUser(int userId) throws IOException;

    void updateUser(int userId);
}
