package funktionalitet;

import data.IUserDAO;
import data.UserDAODISK;
import datatransfer.UserDTO;

import java.util.List;

public class Controller implements iController {
    IUserDAO data;

    public Controller() throws IUserDAO.DALException {
        this.data = new UserDAODISK();
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
    public void updateUser(int userId) {

    }
}
