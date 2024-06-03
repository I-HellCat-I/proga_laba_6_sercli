package Server.Network;

import Classes.*;
import Enums.Furnish;
import Enums.Transport;
import Enums.View;

import java.io.IOException;
import java.security.SecureRandom;
import java.sql.*;
import java.time.ZonedDateTime;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgresManager {
    private ServerContext context;
    public PostgresManager(ServerContext context){
        this.context = context;

    }

    private Flat getFlatFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int creator_id = resultSet.getInt("creator_id");
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
        return new Flat(id,creator_id, name, creationDate, coordinates, area, numberOfRooms, furnish, view, transport, house);
    }

    public ResultSet getWithQuery(String query){
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
            System.out.println((info.get("username")));
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5433/studs", info);
            ResultSet rs = connection.createStatement().executeQuery(query);
            return rs;
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o ");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public void loadCollectionFromDB() throws IOException, SQLException {
        ResultSet resultSet = getWithQuery("SELECT * FROM Flat;");
        if (resultSet == null) {
            Logger.getAnonymousLogger().log(Level.SEVERE, "ResultSet is null, collection was not loaded");
            return;
        }
        while (resultSet.next()) {
            context.getStructureStorage().loadFlat(getFlatFromResultSet(resultSet));
        }
    }
    public int authUser(String name, char[] passwd) {
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
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
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
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

    public int deleteById(int id, int userId){
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            int changed = connection.createStatement().executeUpdate("DELETE * FROM Flat WHERE id = " + id + "AND creator_id" + userId + ";");
            return changed;
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o " + userId);
            e.printStackTrace();
        } return -1;
    }

    public int deleteByUserId(int id){
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            int changed = connection.createStatement().executeUpdate("DELETE * FROM Flat WHERE creator_id = " + id + ";");
            return changed;
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o " + id);
            e.printStackTrace();
        } return -1;
    }
    public int updateById(int id, FlatUpdateRecord flatUpdateRecord, int userId){
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            int changed = connection.createStatement().executeUpdate("UPDATE Flat " +
                    "SET name = " + "'"+flatUpdateRecord.name()+"', x = " + "'"+flatUpdateRecord.coordinates().x() + "', " +
                    "y = " + "'"+flatUpdateRecord.coordinates().y()+"', area = " + "'"+flatUpdateRecord.area() + "'," +
                    "number_of_rooms = " + "'"+flatUpdateRecord.numberOfRooms()+"', furnish = " + "'"+flatUpdateRecord.furnish() + "'," +
                    "view = " + "'"+flatUpdateRecord.view()+"', transport = " + "'"+flatUpdateRecord.transport() + "'," +
                    "house_name = " + "'"+flatUpdateRecord.house().name()+"', year = " + "'"+flatUpdateRecord.house().year() + "'," +
                    "number_of_lifts = " + "'"+flatUpdateRecord.house().numberOfLifts()+"', number_of_flats_on_floor = " + "'"+flatUpdateRecord.house().numberOfFlatsOnFloor() + "'," +
                    "WHERE creator_id = " + userId +
                    " AND id = " + id );
            return changed;
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o "); //todo: add causer id
            e.printStackTrace();
        } return -1;
    }

    public int addNewFlat(FlatUpdateRecord flatUpdateRecord, int userId){
        try {
            Properties info = new Properties();
            info.load(this.getClass().getResourceAsStream("/Server/resources/db.cfg"));
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", info);
            int changed = connection.createStatement().executeUpdate("INSERT INTO Flat (name, x, y, area, number_of_rooms, furnish," +
                    " view, transport, house_name, year, number_of_lifts, number_of_flats_on_floor, creator_id)" +
                    "VALUES (" + "'"+flatUpdateRecord.name()+"', " + ""+flatUpdateRecord.coordinates().x() + ", " +
                    ""+flatUpdateRecord.coordinates().y()+", " + ""+flatUpdateRecord.area() + ", " +
                    ""+flatUpdateRecord.numberOfRooms()+", " + "'"+flatUpdateRecord.furnish() + "'," +
                    "'"+flatUpdateRecord.view()+"', " + "'"+flatUpdateRecord.transport() + "', " +
                    "'"+flatUpdateRecord.house().name()+"', " + ""+flatUpdateRecord.house().year() + ", " +
                    ""+flatUpdateRecord.house().numberOfLifts()+", " + ""+flatUpdateRecord.house().numberOfFlatsOnFloor() + ", " +
                    + userId + ")");
            return changed;
        } catch (SQLException | IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE,"something went wrong during i/o "); //todo: add causer id
            e.printStackTrace();
        } return -1;
    }



}
