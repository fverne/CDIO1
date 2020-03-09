package funktionalitet;

import datatransfer.UserDTO;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Controller implements iController {


    public void addUser() throws IOException {
        UserDTO newUser = new UserDTO();
        Scanner sc = new Scanner(System.in);
        System.out.print("Indtast ID: ");
        String ID = sc.next();
        newUser.setUserId(Integer.parseInt(ID));

        System.out.print("Indtast fornavn: ");
        String fornavn = sc.next();
        System.out.print("Indtast efternavn: ");
        String efternavn = sc.next();
        newUser.setUserName(fornavn + " " + efternavn);
        newUser.setIni(String.valueOf(fornavn.charAt(0) + efternavn.charAt(0)));

        System.out.print("Indtast cpr: ");
        String cpr = sc.next();
        newUser.setCpr(cpr);

        System.out.print("Vælg kodeord: ");
        String password = sc.next();
        newUser.setPassword(password);
        //newUser.setRoles();
        //saveUser(newUser);
        save(newUser);
    }

    public void showUser(String ID) {
        final String file = "test.txt";
        String line = null;
        ArrayList<String> fileContents = new ArrayList<>();

        try {
            FileReader fReader = new FileReader(file);
            BufferedReader fileBuff = new BufferedReader(fReader);
            while ((line = fileBuff.readLine()) != null) {
                if (line.matches("ID: " + ID)) {
                        fileBuff.readLine();

                        System.out.println(line);

                }
            }
            fileBuff.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //System.out.println(line);
    }


    public void updateUser(UserDTO user) {

    }

    public void deleteUser(UserDTO user) {

    }

    public void editUser(UserDTO user) {

    }

    public void save(UserDTO user) throws IOException {
        File file = new File("test.txt");
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(toString(user));
        bw.newLine();
        bw.close();
    }

    public String toString(UserDTO user) {
        return "**********************\n" + "ID: " + user.getUserId() + "\n" + "Navn: " + user.getUserName() + "\n" + "CPR: " + user.getCpr() + "\n" + "Initialer: " + user.getIni() + "\n" + "Kodeord: " + user.getPassword() + "\n**********************\n";
    }
}
