package data;

import funktionalitet.Codegenerator;
import datatransfer.UserDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAO implements IUserDAO {
    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;
    private UserDTO user4;
    private List<UserDTO> userList = new ArrayList<>();


    public UserDAO(Codegenerator passGen) {
        //hardcoded objects that will load on creation of data layer
        // model public UserDTO(int userId, String userName, String ini, ArrayList<String> roles, String password, String cpr)
        user1 = new UserDTO(11, "Boris", "BOI", new ArrayList<String>(Arrays.asList("Admin")), passGen.generateCode(), "1211981234");
        user2 = new UserDTO(12, "OleBoi", "OB", new ArrayList<String>(Arrays.asList("Foreman")), passGen.generateCode(), "3008988995");
        user3 = new UserDTO(13, "Corona", "CV", new ArrayList<String>(Arrays.asList("Pharmacist")), passGen.generateCode(), "1103784321");
        user4 = new UserDTO(14, "360NoScopeX", "NS", new ArrayList<String>(Arrays.asList("Operator")), passGen.generateCode(), "0104691420");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }


    @Override
    public UserDTO getUser(int userId) throws DALException {
        //returns user with same id
        for (UserDTO tempUser : userList) {
            if (tempUser.getUserId() == userId) {
                return tempUser;
            }
        }
        System.out.println("User with ID: " + userId + " was not found.");
        return null;
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        return userList;
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        //adds user to userlist
        userList.add(user);
        System.out.println("New user added.");
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        //check if user is deletable and set the userlist without the user of given ID.
        boolean userDeleted = false;
        for (UserDTO tempUser : userList) {
            if (tempUser.getUserId() == user.getUserId()) {
                userDeleted = true;
            }
        }
        userList = userList.stream().filter(t -> t.getUserId() != user.getUserId()).collect(Collectors.toList());

        //if user was found and removed then add the updated user.
        if (userDeleted) {
            userList.add(user);
            System.out.println("User updated");
        } else {
            System.out.println("System was not able to update user");
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        //check if user is found. set userlist without the user with given ID.
        boolean userDeleted = false;
        for (UserDTO tempUser : userList) {
            if (tempUser.getUserId() == userId) {
                userDeleted = true;
            }
        }
        userList = userList.stream().filter(t -> t.getUserId() != userId).collect(Collectors.toList());
        //output whether deleted or not
        if (userDeleted) {
            System.out.println("User with ID: " + userId + " has been deleted.");
        } else {
            System.out.println("User with ID: " + userId + " was not found.");
        }
    }
}
