package data;

import datatransfer.UserDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAODB implements IUserDAO {
    String username;
    String password;
    String driver;
    String url;


    public UserDAODB() {
        String host = "localhost";
        String port = "3306";
        String database = "cdio1_crud";
        username = "test";
        password = "124";

        //Edit only if needed
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

    }

    public static String rightPad(String stringToPad, int width){
        while(stringToPad.length() <= width){
            stringToPad = stringToPad + " ";
        }
        return stringToPad;
    }


    @Override
    public UserDTO getUser(int userId) throws DALException {
        UserDTO userDTO;
        try {
            ArrayList<String> tempRolesArray = new ArrayList<>();
            ArrayList<String> tempDTOArray = new ArrayList<>();

            Class.forName(driver);
            String sqlQuery;

            System.out.println("Querying SQL...");
            //A query statement like "SELECT * FROM instructor;" or "SHOW TABLES;"
            sqlQuery = "SELECT * FROM users WHERE userID = " + userId + "";

            Connection connection = DriverManager.getConnection(this.url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
//            int[] columnWidths = new int[columnCount+1]; //columnWidths[0] to be ignored
//            int valueLength;

            //Find maximun width for each column and store in columnWidths[]
//            for (int i = 1;i <= columnCount; i++) {
//                columnWidths[i] = resultSetMetaData.getColumnName(i).length();
//            }
//            while (resultSet.next()) {
//                for (int i = 1; i <= columnCount; i++) {
//                    valueLength = resultSet.getString(i).length();
//                    if (valueLength > columnWidths[i]) {columnWidths[i] = valueLength;}
//                }
//            }

            //Print all attribute names
//            for (int i = 1; i <= columnCount; i++) {
//                System.out.print(rightPad(resultSetMetaData.getColumnName(i), columnWidths[i]));
//            }
//              DEBUG // System.out.println();

            //Print all table rows
            resultSet.beforeFirst(); //Set pointer for resultSet.next()
            while (resultSet.next()) {
                //Print all values in a row
                for (int i = 1; i <= columnCount; i++) {
                    // DEBUG // System.out.print(rightPad(resultSet.getString(i), columnWidths[i]));

                    if (i < 6) {
                        tempDTOArray.add(resultSet.getString(i));
                    }

                    if (resultSetMetaData.getColumnName(i).equals("isAdmin") && resultSet.getString(i).equals("1")) {
                        tempRolesArray.add("Admin");
                    }
                    if (resultSetMetaData.getColumnName(i).equals("isPharmacist") && resultSet.getString(i).equals("1")) {
                        tempRolesArray.add("Pharmacist");
                    }
                    if (resultSetMetaData.getColumnName(i).equals("isForeman") && resultSet.getString(i).equals("1")) {
                        tempRolesArray.add("Foreman");
                    }
                    if (resultSetMetaData.getColumnName(i).equals("isOperator") && resultSet.getString(i).equals("1")) {
                        tempRolesArray.add("Operator");
                    }
                }
                // DEBUG // System.out.println();
            }
            connection.close();

            userDTO = new UserDTO(Integer.parseInt(tempDTOArray.get(0)), tempDTOArray.get(1), tempDTOArray.get(2), tempRolesArray, tempDTOArray.get(3), tempDTOArray.get(4));

        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with getUser()");
        }

        return userDTO;
    }

    @Override
    public ArrayList<UserDTO> getUserList() throws DALException {
        ArrayList<UserDTO> multipleDTOArray = new ArrayList<>();

        try {
            Class.forName(driver);
            String sqlQuery;

            System.out.println("Querying SQL...");
            //A query statement like "SELECT * FROM instructor;" or "SHOW TABLES;"
            sqlQuery = "SELECT * FROM users";

            Connection connection = DriverManager.getConnection(this.url, username, password);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sqlQuery);

            int useriterator = 0;
            resultSet.beforeFirst(); //Set pointer for resultSet.next()
            while (resultSet.next()) {
                useriterator = useriterator + 1;
                multipleDTOArray.add(getUser(useriterator));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with getUserList()");
        }


        return multipleDTOArray;
    }

    @Override
    public void createUser(UserDTO user) throws DALException {

    }


    @Override
    public void deleteUser(int userId) throws DALException {

    }

    @Override
    public void updateUser(UserDTO user) throws DALException {

    }
}
