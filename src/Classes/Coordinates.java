package Classes;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Координаты. Довольно странные. Содержат в себе float x и Integer y.
 * Больше сказать нечего
 */
@Data
@Getter
@Setter
public class Coordinates {

    private float x; //Значение поля должно быть больше -146
    private Integer y; //Максимальное значение поля: 185, Поле не может быть null

    /**
     * Для jackson. Не стоит использовать в других случаях
     */
    Coordinates() {
    }

    public Coordinates(float x, Integer y) {
        checkVars(x, y);
        this.x = x;
        this.y = y;
    }

    public Coordinates(CoordinatesUpdateRecord coordinates) {
        this(coordinates.x(), coordinates.y());
    }

    public static void checkVars(float x, Integer y) {
        checkX(x);
        checkY(y);
    }

    public static void checkX(float x) {
        if (x <= -146) throw new IllegalArgumentException("x не может быть меньше чем -146");
    }

    public static void checkY(Integer y) {
        if (y == null) throw new NullPointerException("y не дожно быть null");
        if (y > 185) throw new IllegalArgumentException("y не может быть больше чем 185");
    }

    @Override
    public String toString() {
        return "\t\t<Coordinates>\n\t\t\t<X>" + x + "</X>\n" + "\t\t\t<Y>" + y + "</Y>\n\t\t</Coordinates>";
    }

    public static void checkX(String s) {
        checkX(Float.parseFloat(s));
    }

    public static void checkY(String s) {
        checkY(Integer.parseInt(s));
    }

    public void update(CoordinatesUpdateRecord coordinates) {
        this.x = coordinates.x() != null ? coordinates.x(): x;
        this.y = coordinates.y() != null ? coordinates.y(): y;
    }
}
