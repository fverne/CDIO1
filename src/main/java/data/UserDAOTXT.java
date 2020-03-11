package data;

import datatransfer.UserDTO;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserDAOTXT implements IUserDAO {
    final String TextDB = "TextDB.txt";
    private List<UserDTO> userList = new ArrayList<>();

    public void createUser(UserDTO user) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Indtast fornavn: ");
        String firstName = sc.next();

        System.out.println("Indtast efternavn: ");
        String lastName = sc.next();

        user.setUserName(firstName + " " + lastName);

        System.out.println("Indtast Cpr nr: ");
        String cprNR = sc.next();
        user.setCpr(cprNR);

        user.setIni(String.valueOf(firstName.charAt(0) + lastName.charAt(0)));
        userList.add(user);
    }


    @Override
    public UserDTO getUser(int userId) throws DALException {
        return this.getUser(userId);
    }

    public List<UserDTO> getUserList() {
        return userList;
    }


    public void updateUser(UserDTO user) {
        Scanner sc = new Scanner(System.in);
        String updatedInfo;
        int userSelection;
        System.out.println("Hvilke informationer vil du opdaterer?");
        System.out.println("1. Navn");
        System.out.println("2. Cpr nr.");
        System.out.println("3. Password.");
        System.out.println("4. Roller.");
        userSelection = sc.nextInt();

        switch (userSelection) {
            case 1:
                System.out.println("Indtast navn: ");
                updatedInfo = sc.next();
                user.setUserName(updatedInfo);
                break;
            case 2:
                System.out.println("Indtast Cpr nr.");
                updatedInfo = sc.next();
                user.setCpr(updatedInfo);
                break;
            case 3:
                System.out.println("Indtast nyt kodeord: ");
                updatedInfo = sc.next();
                user.setPassword(updatedInfo);
                break;
            case 4:
//                System.out.println("Indtast nye roller: ");
//                updatedInfo = sc.next();
//                cont.updateUser(ID,userSelection,updatedInfo);
                break;
            default:
                break;
        }
    }

    @Override
    public void deleteUser(int userId) throws DALException {

    }

}
