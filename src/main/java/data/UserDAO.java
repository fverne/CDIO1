package data;

import datatransfer.UserDTO;

import java.util.ArrayList;

public class UserDAO implements IUserDAO {
    @Override
    public UserDTO getUser(int userId) throws DALException {
        return null;
    }

    @Override
    public ArrayList<UserDTO> getUserList() throws DALException {
        return null;
    }

    @Override
    public void createUser(UserDTO user) throws DALException {

    }

    @Override
    public void deleteUser(int userId) throws DALException {

    }

    @Override
    public void updateUser(UserDTO user) throws DALException {

    }
}
