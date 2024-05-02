package Classes;

import Enums.Furnish;
import Enums.Transport;
import Enums.View;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.ArrayDeque;
import java.util.HashSet;

/**
 * Квартира из ТЗ.
 * Сравнивается между собой по именам без учёта регистра.
 */
@Data
@Getter
@Setter
public class Flat implements Comparable<Flat> {

    private final Integer id; //Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    @JacksonXmlProperty(isAttribute = false, localName = "creationDate")
    private ZonedDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double area; //Значение поля должно быть больше 0
    private Integer numberOfRooms; //Значение поля должно быть больше 0
    private Furnish furnish; //Поле не может быть null
    private View view; //Поле может быть null
    private Transport transport; //Поле не может быть null
    private House house; //Поле не может быть null

    public Flat(boolean b) { // Used for testing purposes
        this("2", new Coordinates(0.1F, 2), 0.2, 3, Furnish.NONE, View.TERRIBLE, Transport.NONE, new House("4", 5, 6, 7));
    }

    /**
     * Для jackson. Не стоит использовать в других случаях
     */
    Flat() {
        id = null;
    }

    public Flat(FlatUpdateRecord flatUpdateRecord) {
        this.id = IdGenerator.generateId();
        this.update(flatUpdateRecord);
    }

    /**
     * Генератор Id для Flat'ов. В случае загрузки файла, загруженные Id не будет использовать для выдачи новым Flat'ам
     */
    private static class IdGenerator {

        private static int next_id = 1;
        private static ArrayDeque<Integer> missingIds = new ArrayDeque<>();
        private static HashSet<Integer> loadedIds = new HashSet<>();

        public static int generateId() {
            if (missingIds.isEmpty()) {
                next_id++;
                return next_id - 1;
            }
            return missingIds.pop();
        }


        public static void addMissing(Integer missing) {
            missingIds.add(missing);
        }

        public static void clear() {
            ArrayDeque<Integer> missingIds = new ArrayDeque<>();
            next_id = 1;
        }

        protected static void onPartlyLoaded(int id) {
            loadedIds.add(id);
        }

        public static void onFullyLoaded() {
            int maxId = 0;
            for (int a : loadedIds) {
                if (a > maxId) {
                    maxId = a;
                }
            }
            next_id = maxId + 1;
            for (int i = 1; i < maxId; i++) {
                if (!loadedIds.contains(i)) {
                    missingIds.add(i);
                }
            }
            loadedIds.clear();
        }
    }


    public Flat(String name, Coordinates coordinates, double area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        checkIfRight(name, coordinates, area, numberOfRooms, furnish, view, transport, house);
        id = IdGenerator.generateId();
        creationDate = ZonedDateTime.now();
        update(name, coordinates, area, numberOfRooms, furnish, view, transport, house);
    }

    private Flat(int id, String name, ZonedDateTime creationDate, Coordinates coordinates, double area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        checkIfRight(name, coordinates, area, numberOfRooms, furnish, view, transport, house);
        this.id = id;
        this.creationDate = creationDate;
        update(name, coordinates, area, numberOfRooms, furnish, view, transport, house);
    }

    public void update(String name, Coordinates coordinates, double area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        this.name = name == null ? this.name : name;
        this.coordinates = coordinates == null ? this.coordinates : coordinates;
        this.area = area == 0 ? this.area : area;
        this.numberOfRooms = numberOfRooms == null ? this.numberOfRooms : numberOfRooms;
        this.furnish = furnish == null ? this.furnish : furnish;
        this.view = view == null ? this.view : view;
        this.transport = transport == null ? this.transport : transport;
        this.house = house == null ? this.house : house;
    }

    private void checkIfRight(String name, Coordinates coordinates, double area, Integer numberOfRooms, Furnish furnish, View view, Transport transport, House house) {
        checkArea(area);
        checkNumberOfRooms(numberOfRooms);
        checkName(name);
        if (furnish == null) throw new NullPointerException("furnish не может быть null");
        if (view == null) throw new NullPointerException("View не может быть null");
        if (transport == null) throw new NullPointerException("Transport не может быть null");
        if (house == null) throw new NullPointerException("House не может быть null");
        if (coordinates == null) throw new NullPointerException("coordinates не может быть null");
    }

    public static void checkArea(double area) {
        if (area < 0) throw new IllegalArgumentException("Значение area должно быть больше 0");
    }

    public static void checkNumberOfRooms(Integer numberOfRooms) {
        if (numberOfRooms < 0) throw new IllegalArgumentException("Значение numberOfRooms должно быть больше 0");
    }

    public static void checkName(String name) {
        if (name == null) throw new NullPointerException("Значение name не должно быть null");
    }

    public void markForDeletion() {
        IdGenerator.addMissing(id);
    }

    @Override
    public int compareTo(Flat o) {
        return String.CASE_INSENSITIVE_ORDER.compare(this.name, o.name);
    }

    public static void clearIndicator() {
        IdGenerator.clear();
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }


    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public void setArea(double area) {
        this.area = area;
    }


    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }


    public void setFurnish(Furnish furnish) {
        this.furnish = furnish;
    }


    public void setView(View view) {
        this.view = view;
    }


    public void setTransport(Transport transport) {
        this.transport = transport;
    }


    public void setHouse(House house) {
        this.house = house;
    }

    @Override
    public String toString() {
        return "\t<Flat>\n" +
                "\t\t<Id>" + id + "</Id>\n" +
                "\t\t<Name>" + name + "</Name>\n" +
                coordinates + "\n" +
                "\t\t<CreationDate>" + creationDate + "</CreationDate>\n" +
                "\t\t<Area>" + area + "</Area>\n" +
                "\t\t<NumberOfRooms>" + numberOfRooms + "</NumberOfRooms>\n" +
                "\t\t<Furnish>" + furnish + "</Furnish>\n" +
                "\t\t<View>" + view + "</View>\n" +
                "\t\t<Transport>" + transport + "</Transport>\n" +
                house + "\n" +
                "\t</Flat>\n";
    }

    public void update(FlatUpdateRecord flatUpdateRecord) {
        this.name = flatUpdateRecord.name() != null ? flatUpdateRecord.name(): name;
        if (coordinates != null) {
            this.coordinates.update(flatUpdateRecord.coordinates());
        }else {
            this.coordinates = new Coordinates(flatUpdateRecord.coordinates());
        }
        this.area = flatUpdateRecord.area() != null ? flatUpdateRecord.area(): area;
        this.numberOfRooms = flatUpdateRecord.numberOfRooms() != null ? flatUpdateRecord.numberOfRooms(): numberOfRooms;
        this.furnish = flatUpdateRecord.furnish() != null ? flatUpdateRecord.furnish(): furnish;
        this.view = flatUpdateRecord.view() != null ? flatUpdateRecord.view(): view;
        this.transport = flatUpdateRecord.transport() != null ? flatUpdateRecord.transport(): transport;
        if (this.house != null){
            this.house.update(flatUpdateRecord.house());
        } else {
            this.house = new House(flatUpdateRecord.house());
        }
    }

    void addLoadedId() {
        IdGenerator.onPartlyLoaded(this.id);
    }

    static void finishLoadingIds() {
        IdGenerator.onFullyLoaded();
    }
}
