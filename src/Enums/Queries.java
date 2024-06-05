package Enums;

import java.time.DayOfWeek;

public enum Queries {
    CONFIG("/Server/resources/db.cfg"),
    PSQL_ADDRESS("jdbc:postgresql://localhost:5433/studs"),
    BASE_SELECT("SELECT * FROM Flats "),
    BASE_ADD("INSERT INTO Flats "),
    INSERT_USER_QUERY("INSERT INTO Users (name, passwd_hash, passwd_salt) VALUES (?, ?, ?)"),
    BASE_DELETE_QUERY("DELETE FROM Flats WHERE "),
    SELECT_USER_QUERY("SELECT id, passwd_hash, passwd_salt FROM Users WHERE name = ? ");

    private String query;
    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
