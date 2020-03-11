package data;

import codegenerator.Codegenerator;
import datatransfer.UserDTO;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserDAO implements IUserDAO {
    private UserDTO user1;
    private UserDTO user2;
    private UserDTO user3;
    private UserDTO user4;
    private ArrayList<UserDTO> userList = new ArrayList<>();


    public UserDAO(Codegenerator passGen){
        //hardcoded objects that will load on creation of data layer
        // model public UserDTO(int userId, String userName, String ini, ArrayList<String> roles, String password, String cpr)
        user1 = new UserDTO(11, "Boris", "BOI", new ArrayList<String>(Arrays.asList("Admin")), passGen.generateCode(),"1211981234");
        user2 = new UserDTO(12, "OleBoi", "OB", new ArrayList<String>(Arrays.asList("Foreman")), passGen.generateCode(),"3008988995");
        user3 = new UserDTO(13, "Corona", "CV", new ArrayList<String>(Arrays.asList("Pharmacist")), passGen.generateCode(),"1103784321");
        user4 = new UserDTO(14, "360NoScopeX", "NS", new ArrayList<String>(Arrays.asList("Operator")), passGen.generateCode(),"0104691420");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        userList.add(user4);
    }


    @Override
    public UserDTO getUser(int userId) throws DALException {
        for(UserDTO tempUser : userList){
            if(tempUser.getUserId() == userId){
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
        userList.add(user);
        System.out.println("New user added.");
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        //updating a user should be possible by ".getUser.setX(y)"
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        boolean userDeleted = false;
        for(UserDTO tempUser : userList){
            if(tempUser.getUserId() == userId){
                userList.remove(tempUser);
                userDeleted = true;
            }
        }
        if(userDeleted){
            System.out.println("User with ID: " + userId + " has been deleted.");
        }
        else{
            System.out.println("User with ID: " + userId + " was not found.");
        }
    }
}