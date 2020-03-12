package data;

import datatransfer.UserDTO;

import java.sql.*;
import java.util.ArrayList;

public class UserDAODB implements IUserDAO {
    String username;
    String password;
    String driver;
    String url;


    // Constructor details the database connection info
    public UserDAODB() {
        String host = "localhost";
        String port = "3306";
        String database = "crud_db";
        username = "test";
        password = "124";

        //Edit only if needed
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    }

    // queries the database with the given information
    public ResultSet makeDBQuery(String query) throws DALException {
        ResultSet resultSet;

        try {
            Class.forName(driver);

            System.out.println("Querying SQL...");

            Connection connection = DriverManager.getConnection(this.url, username, password);
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with makeDBQuery()");
        }

        return resultSet;
    }

    public Statement makeDBManipulation(String manipulation) throws DALException {
        Statement statement;

        try {
            Class.forName(driver);

            System.out.println("Manipulating SQL...");

            Connection connection = DriverManager.getConnection(this.url, username, password);
            statement = connection.createStatement();
            statement.executeUpdate(manipulation);

        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with makeDBQuery()");
        }

        return statement;
    }

    public void closeDBConnection() throws DALException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(this.url, username, password);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with closeDBConnection()");
        }
    }

    @Override
    public UserDTO getUser(int userId) throws DALException {
        UserDTO userDTO;
        try {
            ArrayList<String> tempRolesArray = new ArrayList<>();
            ArrayList<String> tempDTOArray = new ArrayList<>();

            ResultSet resultSet = makeDBQuery("SELECT * FROM users WHERE userID = " + userId + "");

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            //Print all table rows
            resultSet.beforeFirst(); //Set pointer for resultSet.next()
            while (resultSet.next()) {
                //Print all values in a row
                for (int i = 1; i <= columnCount; i++) {

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
            }

            userDTO = new UserDTO(Integer.parseInt(tempDTOArray.get(0)), tempDTOArray.get(1), tempDTOArray.get(2), tempRolesArray, tempDTOArray.get(3), tempDTOArray.get(4));

            closeDBConnection();

        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with getUser()");
        }

        return userDTO;
    }

    @Override
    public ArrayList<UserDTO> getUserList() throws DALException {
        ArrayList<UserDTO> multipleDTOArray = new ArrayList<>();

        ArrayList<String> tempRolesArray = new ArrayList<>();
        ArrayList<String> tempDTOArray = new ArrayList<>();
        try {
            ResultSet resultSet = makeDBQuery("SELECT * FROM users");

            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();

            //Print all table rows
            resultSet.beforeFirst(); //Set pointer for resultSet.next()
            while (resultSet.next()) {
                //Print all values in a row
                tempDTOArray.clear();
                tempRolesArray.clear();
                for (int i = 1; i <= columnCount; i++) {

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

                multipleDTOArray.add(new UserDTO(Integer.parseInt(tempDTOArray.get(0)), tempDTOArray.get(1), tempDTOArray.get(2), tempRolesArray, tempDTOArray.get(3), tempDTOArray.get(4)));
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with getUserList()");
        }


        return multipleDTOArray;
    }

    @Override
    public void createUser(UserDTO user) throws DALException {
        int isAdmin = 0;
        int isPharmacist = 0;
        int isForeman = 0;
        int isOperator = 0;

        for (int i = 1; i <= user.getRoles().size(); i++) {

            if (user.getRoles().contains("Admin")) {
                isAdmin = 1;
            }
            if (user.getRoles().contains("Pharmacist")) {
                isPharmacist = 1;
            }
            if (user.getRoles().contains("Foreman")) {
                isForeman = 1;
            }
            if (user.getRoles().contains("Operator")) {
                isOperator = 1;
            }
        }

        try {
            makeDBManipulation("INSERT INTO users VALUES (" + user.getUserId() + ", '" + user.getUserName() + "', '" + user.getIni() + "', '" + user.getPassword() + "', '" + user.getCpr() + "', " + isAdmin + ", " + isPharmacist + ", " + isForeman + ", " + isOperator + ")");
        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with createUser()");
        }
    }


    @Override
    public void deleteUser(int userId) throws DALException {
        try {
            makeDBManipulation("DELETE FROM users WHERE userID = " + userId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DALException("Something went wrong with deleteUser()");
        }
    }

    @Override
    public void updateUser(UserDTO user) throws DALException {
        deleteUser(user.getUserId());
        createUser(user);
    }
}
