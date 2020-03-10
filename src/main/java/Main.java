import TUI.TUI;
import TUI.ITUI;
import data.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Main {
    public static void main(String[] args) throws IOException, IUserDAO.DALException {
        IUserDAO data = new UserDAODB();
        TUI ui = new TUI(data);
        while (true) {
            ui.showMenu();
        }
    }
}
