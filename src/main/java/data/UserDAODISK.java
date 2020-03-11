package data;

import datatransfer.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserDAODISK implements IUserDAO {
    private List<UserDTO> userList;

    public UserDAODISK() {
        userList = new ArrayList<>();
        File tempFile = new File("UserDTO.dat");
        if (tempFile.exists()) {
            userList = getUserList();
        } else {
            saveUsersToDisk(userList);
        }
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
    public void deleteUser(int userId) {
        userList = userList.stream().filter(t -> t.getUserId() != userId).collect(Collectors.toList());
        saveUsersToDisk(userList);
    }

    @Override
    public void updateUser(UserDTO user) {
        for (int i = 0; i < userList.size(); i++) {
            if (userList.get(i).getUserId() == user.getUserId()) {
                userList = userList.stream().filter(t -> t.getUserId() != user.getUserId()).collect(Collectors.toList());
                userList.add(i, user);
                break;
            }
        }
        saveUsersToDisk(userList);
    }

    @Override
    public UserDTO getUser(int userId) {
        List<UserDTO> tempList = userList.stream().filter(t -> t.getUserId() == userId).collect(Collectors.toList());
        return tempList.get(0);
    }

    //virker
    @Override
    public void createUser(UserDTO user) {
        userList.add(user);
        saveUsersToDisk(userList);
    }

    @Override
    public List<UserDTO> getUserList() {
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
}
