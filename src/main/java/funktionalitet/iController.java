package funktionalitet;

import datatransfer.UserDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface iController {
    void addUser() throws IOException;

    void showUser(String ID);

    void deleteUser(String ID) throws IOException;

    void editUser(UserDTO user);
}
