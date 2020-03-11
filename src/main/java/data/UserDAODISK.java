package data;

import datatransfer.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAODISK implements IUserDAO {
    private List<UserDTO> userList;
    UserDTO test;

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
        Scanner sc = new Scanner(System.in);
        String userName;
        String userIni;
        System.out.print("Indtast fornavn: ");
        String fornavn = sc.next();
        System.out.print("Indtast efternavn: ");
        String efternavn = sc.next();

        userIni = String.valueOf(fornavn.charAt(0)+efternavn.charAt(0));
        userName = fornavn+" "+efternavn;

        System.out.print("Indtast cpr: ");
        String cpr = sc.next();

        userList.add(new UserDTO(4,userName,userIni,null,"password",cpr));
        saveUsersToDisk(userList);
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        System.out.println("Ikke implementeret");
        return test = new UserDTO();
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

    private static void saveUsersToDisk(List<UserDTO> userList){
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

    }

    @Override
    public void updateUser(UserDTO user) throws DALException {

    }
}
