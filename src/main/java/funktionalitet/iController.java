package funktionalitet;

import datatransfer.UserDTO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface iController {
    void addUser() throws IOException;

    void showUser(String ID);

    void updateUser(UserDTO user);

    void deleteUser(UserDTO user);

    void editUser(UserDTO user);
}
