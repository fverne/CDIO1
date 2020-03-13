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
        username = "root";
        password = "Isbjorn44";

        //Edit only if needed
        driver = "com.mysql.cj.jdbc.Driver";
        url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?characterEncoding=latin1&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";


    }

    public void checkDb() throws DALException{
        Connection conn = null;
        Statement stmt = null;

        try {
            //open connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost", username, password);
            stmt = conn.createStatement();

            //query for the database name
            ResultSet response = stmt.executeQuery("SELECT SCHEMA_NAME\n" + "  FROM INFORMATION_SCHEMA.SCHEMATA\n" + " WHERE SCHEMA_NAME = 'crud_db'");

            //see if the database is there
            if (!response.next())
                createDatabase();

        } catch (Exception e){
            e.printStackTrace();
            throw new DALException("Unable to connect to server");
        } finally {
            {
                try {
                    if (stmt != null)
                        stmt.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    if (conn != null)
                        conn.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void createDatabase() throws DALException{
        String dbUrl = "jdbc:mysql://localhost";
        Connection conn = null;
        Statement stmt = null;

        try{
            //register JDBC driver
            Class.forName(driver);

            //open connection
            conn = DriverManager.getConnection(dbUrl, username, password);

            //create statement
            stmt = conn.createStatement();

            //create db
            stmt.executeUpdate("Create Database crud_db");

            //create schemas
            stmt.executeUpdate("CREATE TABLE `crud_db`.`users` (" +
                    "userID int," +
                    "userName varchar(20)," +
                    "ini varchar(4)," +
                    "pass varchar(255)," +
                    "cpr varchar(13)," +
                    "isAdmin boolean," +
                    "isPharmacist boolean," +
                    "isForeman boolean," +
                    "isOperator boolean," +
                    "PRIMARY KEY (userID))");

        } catch (Exception e){
            e.printStackTrace();
            throw new DALException("problem creating database");
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (Exception e){
                e.printStackTrace();
            }
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // queries the database with the given information
    public ResultSet makeDBQuery(String query) throws DALException {
        checkDb();
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
        checkDb();
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
        UserDTO userDTO = null;
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

            try {
                userDTO = new UserDTO(Integer.parseInt(tempDTOArray.get(0)), tempDTOArray.get(1), tempDTOArray.get(2), tempRolesArray, tempDTOArray.get(3), tempDTOArray.get(4));
            } catch (Exception e){
                return new UserDTO(0, "Bruger ikke fundet", "Bruger ikke fundet", new ArrayList<String>(), "Bruger ikke fundet", "Bruger ikke fundet");
            }

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
                ArrayList<String> tempRolesArray = new ArrayList<>();
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
