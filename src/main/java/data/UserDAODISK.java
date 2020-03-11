package data;

import datatransfer.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAODISK implements IUserDAO {
    private List<UserDTO> userList;

    public UserDAODISK() throws DALException {
        userList = new ArrayList<>();
        File tempFile = new File("UserDTO.dat");
        if (tempFile.exists()) {
            userList = getUserList();
        } else {
//            userList.add(new UserDTO(1,"Username","Ini",null,"password","cpr"));
//            userList.add(new UserDTO(2,"Username","Ini",null,"password","cpr"));
//            userList.add(new UserDTO(3,"Username","Ini",null,"password","cpr"));
            saveUsersToDisk(userList);
        }
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        userList.add(user);
        saveUsersToDisk(userList);
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        return userList.get(userId);
    }

    @Override
    public List<UserDTO> getUserList() throws DALException {
        List<UserDTO> list = null;
        try {
            ObjectInputStream temp = new ObjectInputStream(new FileInputStream("UserDTO.dat"));
            list = (List<UserDTO>) temp.readObject();
            temp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static void saveUsersToDisk(List<UserDTO> userList) {
        try {
            FileOutputStream fos = new FileOutputStream("UserDTO.dat");
            ObjectOutputStream outputStream = new ObjectOutputStream(fos);
            outputStream.writeObject(userList);
            outputStream.flush();
            outputStream.close();
            fos.close();
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {
        userList.remove(userId);
        saveUsersToDisk(userList);
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        //
    }
}
