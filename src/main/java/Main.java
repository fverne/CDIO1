import TUI.TUI;
import TUI.ITUI;
import data.IUserDAO;
import data.UserDAO;

public class Main {
    public static void main(String[] args) {
        UserDAO data = new UserDAO();
        ITUI ui = new TUI(data);
        while (true) {
            ui.showMenu();
        }
    }
}
