package funktionalitet;

import TUI.TUI;
import data.IUserDAO;
import data.UserDAODISK;
import datatransfer.UserDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
    public List<UserDTO> getUserList() throws IUserDAO.DALException {
        return data.getUserList();
    }

    @Override
    public void deleteUser(int userId) throws IUserDAO.DALException {
        int index = userId - 10;
        this.data.deleteUser(index);
    }

    @Override
    public void updateUser(int userId) throws IUserDAO.DALException {
        Scanner sc = new Scanner(System.in);
        TUI.displayText("Hvilke informationer vil du redigerer: \n1. Navn\n2. Cpr\n3. Roller\n4. Password");
        int userSelection = sc.nextInt();
        switch (userSelection) {
            case 1:
                TUI.displayText("Indtast navn: ");
                String newName = sc.next();
                data.getUser(userId).setUserName(newName);
                break;
            case 2:
                TUI.displayText("Indtast cpr: ");
                String cpr = sc.next();
                getUser(userId).setCpr(cpr);
                break;

            case 3:
                this.data.getUser(userId).getRoles().clear();
                TUI.displayText("Tast y/n for at tilvælge roller: ");
                ArrayList<String> roles = new ArrayList<>();
                if (sc.next().equals("y")) {
                    roles.add("Admin");
                }
                TUI.displayText("Pharmacist");
                if (sc.next().equals("y")) {
                    roles.add("Pharmacist");
                }
                TUI.displayText("Foreman");
                if (sc.next().equals("y")) {
                    roles.add("Foreman");
                }
                TUI.displayText("Operator");
                if (sc.next().equals("y")) {
                    roles.add("Operator");
                }
                this.data.getUser(userId).setRoles(roles);
                break;

            case 4:
                TUI.displayText("Indtast password: ");
                String password = sc.next();
                getUser(userId).setUserName(password);
                break;
            default:
                break;
        }
        sc.close();
    }
}
