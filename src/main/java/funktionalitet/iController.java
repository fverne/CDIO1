package funktionalitet;

import data.IUserDAO;
import datatransfer.UserDTO;

import java.io.IOException;
import java.util.List;

public interface iController {

    void createUser(UserDTO user) throws IUserDAO.DALException;

    UserDTO getUser(int userId) throws IUserDAO.DALException;

    List<UserDTO> getUserList();

    void deleteUser(int userId) throws IUserDAO.DALException;

    void updateUser(int userId);
}
