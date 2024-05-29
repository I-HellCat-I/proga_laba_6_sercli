package Server.Network;

import Classes.Coordinates;
import Classes.Flat;
import Classes.House;
import Classes.ServerContext;
import Enums.Furnish;
import Enums.Transport;
import Enums.View;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Properties;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgresManager {
    private ServerContext context;
    public PostgresManager(ServerContext context){
        this.context = context;

    }

    public ResultSet getWithQuery(String query){
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            ResultSet rs = connection.createStatement().executeQuery(query);
            return rs;
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o "); //todo: add causer id
            e.printStackTrace();
        }
        return null;
    }
    public void loadCollectionFromDB() throws IOException, SQLException {
        ResultSet resultSet = getWithQuery("SELECT * FROM Flat;");
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String name = resultSet.getString("name");
            Coordinates coordinates = new Coordinates(resultSet.getFloat("x"), resultSet.getInt("y"));
            ZonedDateTime creationDate = ZonedDateTime.parse(resultSet.getString("creation_date"));
            double area = resultSet.getDouble("area");
            int numberOfRooms = resultSet.getInt("number_of_rooms");
            Furnish furnish = Furnish.valueOf(resultSet.getString("furnish"));
            View view = View.valueOf(resultSet.getString("view"));
            Transport transport = Transport.valueOf(resultSet.getString("transport"));

            String house_name = resultSet.getString("house_name"); //Поле может быть null
            int year = resultSet.getInt("year"); //Максимальное значение поля: 630, Значение поля должно быть больше 0
            long numberOfFlatsOnFloor = resultSet.getLong("number_of_flats_on_floor"); //Значение поля должно быть больше 0
            Integer numberOfLifts = resultSet.getInt("number_of_lifts"); //Значение поля должно быть больше 0
            House house = new House(house_name, year, numberOfFlatsOnFloor, numberOfLifts);
            Flat flat = new Flat(id, name, creationDate, coordinates, area, numberOfRooms, furnish, view, transport, house);
            context.getStructureStorage().addFlat(flat);
        }
    }
    public int authUser(String name, char[] passwd) {
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);

            String selectUserQuery = "SELECT id, passwd_hash, passwd_salt FROM \"User\" WHERE name = ?";
            PreparedStatement selectUserStatement = connection.prepareStatement(selectUserQuery);
            selectUserStatement.setString(1, name);
            ResultSet resultSet = selectUserStatement.executeQuery();

            if (resultSet.next()) {
                String passwdHash = resultSet.getString("passwd_hash");
                String passwdSalt = resultSet.getString("passwd_salt");
                String inputPasswdHash = PasswordHandler.hashPassword(passwd, passwdSalt);

                if (passwdHash.equals(inputPasswdHash)) {
                    return resultSet.getInt("id");
                }
            }
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o ");
        }
        return -1;
    }

    public int regUser(String name, char[] passwd) {
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);

            // Check if a user with the provided name already exists
            String selectUserQuery = "SELECT COUNT(*) FROM \"User\" WHERE name = ?";
            PreparedStatement selectUserStatement = connection.prepareStatement(selectUserQuery);
            selectUserStatement.setString(1, name);
            ResultSet resultSet = selectUserStatement.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return -1; // A user with the provided name already exists
            }

            // Generate a unique salt for the new user
            SecureRandom random = new SecureRandom();
            byte[] saltBytes = new byte[16];
            random.nextBytes(saltBytes);
            String salt = Base64.getEncoder().encodeToString(saltBytes);

            // Hash the provided password with the generated salt
            String passwdHash = PasswordHandler.hashPassword(passwd, salt);

            // Insert the new user into the "User" table
            String insertUserQuery = "INSERT INTO \"User\" (name, passwd_hash, passwd_salt) VALUES (?, ?, ?)";
            PreparedStatement insertUserStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
            insertUserStatement.setString(1, name);
            insertUserStatement.setString(2, passwdHash);
            insertUserStatement.setString(3, salt);

            int rowsAffected = insertUserStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = insertUserStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o ");
        }
        return -1;
    }



}
