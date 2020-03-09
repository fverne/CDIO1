import TUI.TUI;
import TUI.ITUI;
import data.IUserDAO;
import data.UserDAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws IOException {
        UserDAO data = new UserDAO();
        TUI ui = new TUI(data);
        while (true) {
            ui.showMenu();
        }
    }
}
