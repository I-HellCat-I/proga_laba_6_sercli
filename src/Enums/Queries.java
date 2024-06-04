package Enums;

public enum Queries {
    BASE_SELECT("SELECT * FROM FLATS");

    private String query;
    Queries(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
