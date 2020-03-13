package funktionalitet;

import data.IUserDAO;
import datatransfer.UserDTO;

import java.io.IOException;
import java.util.List;

public interface IController {

    void createUser(UserDTO user) throws IUserDAO.DALException;

    UserDTO getUser(int userId) throws IUserDAO.DALException;

    List<UserDTO> getUserList() throws IUserDAO.DALException;

    void deleteUser(int userId) throws IOException, IUserDAO.DALException;

    void updateUser(int userId) throws IUserDAO.DALException;
}
