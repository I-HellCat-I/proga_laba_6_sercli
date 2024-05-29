package Client.CommandExecution;

import Classes.*;
import Enums.Furnish;
import Enums.Transport;
import Enums.View;
import lombok.Getter;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Класс, отвечающий за обработку пользовательского ввода.
 */
@Getter
public class Interactor {

    private Scanner scanner = new Scanner(System.in);
    private int nowRecursion = 0;
    private Stack<Scanner> scannerStack = new Stack<>();
    private TreeSet<String> executingFilenames = new TreeSet<>();
    private ClientContext clientContext;
    public Interactor(ClientContext clientContext){
        this.clientContext = clientContext;
    }

    /**
     * Костыль, который убирает проблемы с рекурсией
     * @param input Строка, которая подаётся на обработку
     */
    public void masterProcessInput(String input) { //
        try {
            processInput(input);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            scanner = scannerStack.get(0);
            scannerStack.clear();
            nowRecursion = 0;
            executingFilenames.clear();
        }
    }

    /**
     * Базовый обработчик ввода, скидывает обработанную команду менеждеру команд.
     * @param input вводимая строка
     */
    private void processInput(String input) {
        String[] words = input.trim().split(" ");
        String[] args = words.length > 1 ? new String[words.length - 1] : null;
        if (args != null) System.arraycopy(words, 1, args, 0, words.length - 1);
        try {
            System.out.println(clientContext.getCommandManager().exec(words[0], args));
        } catch (NullPointerException e) {
            System.out.println("Такой команды нет");
        }
    }

    /**
     * Читает и обрабатывает скрипты.
     * @param filename очев
     * @return То, что надо вывести
     * @throws RuntimeException если глубина рекурсии превысит допустимый лимит, или если файлы будут вызывать друг друга, образуя цикл
     */
    public String executeScript(String filename) {  // todo: remake for client-server
        if (executingFilenames.contains(filename)) {
            throw new RuntimeException("Файлы ссылаются друг на друга");
        }
        if (nowRecursion > clientContext.getMaxRecursionDepth()) {
            throw new RuntimeException("Вы превысили максимальную допустимую глубину рекурсии: " + clientContext.getMaxRecursionDepth());
        }
        executingFilenames.add(filename);
        nowRecursion++;
        scannerStack.add(scanner);
        try (BufferedInputStream inStream = new BufferedInputStream(new FileInputStream(filename))) {
            scanner = new Scanner(inStream);
            while (scanner.hasNextLine()) {
                processInput(scanner.nextLine());
            }
        } catch (FileNotFoundException exc) {
            return ("Ваш файл не найден, введите имя существующего файла");
        } catch (IOException e) {
            return (e.getMessage());
        }
        nowRecursion--;
        scanner = scannerStack.pop();
        return "Done.\n";
    }

    /**
     * Вводит квартиру из нынешнего сканнера
     *
     * @param isUpdate
     * @return null, если toUpdate не null, Flat, если toUpdate = null
     */
    public FlatUpdateRecord inputFlat(boolean isUpdate) {

        String name = inputFoolProof("Введите название квартиры:", Flat::checkName, (a) -> {
            return a;
        }, false);
        Float x = inputFoolProof("Введите координату Х (число с плавающей точкой): ", Coordinates::checkX, Float::parseFloat, true);
        Integer y = inputFoolProof("Введите координату Y (целое число): ", Coordinates::checkY, Integer::parseInt, true);

        Double area = inputFoolProof("Введите площадь (число с плавающей точкой): ", Flat::checkArea, Double::parseDouble, true);
        Integer numberOfRooms = inputFoolProof("Введите количество комнат (целое число): ", Flat::checkNumberOfRooms, Integer::parseInt, true);
        System.out.println("Введите показатель furnish (Одно из трёх: NONE, BAD, FINE): ");
        Furnish furnish = inputEnumValue(Furnish.class);
        System.out.println("Введите показатель view (Одно из трёх: YARD, BAD, TERRIBLE): ");
        View view = inputEnumValue(View.class);
        System.out.println("Введите показатель transport (Одно из четырёх: NONE, FEW, NORMAL, ENOUGH): ");
        Transport transport = inputEnumValue(Transport.class);
        String housename = inputFoolProof("Введите название дома:", House::checkName, (a) -> {
            return a;
        }, false);
        int year = inputFoolProof("Введите возраст дома (целое число): ", House::checkYear, Integer::parseInt, true);
        long numberOfFlatsOnFloor = inputFoolProof("Введите количество квартир на этаже (целое число): ", House::checkNumberOfFlatsOnFloor, Long::parseLong, true);
        Integer numberOfLifts = inputFoolProof("Введите количество лифтов (целое число): ", House::checkNumberOfLifts, Integer::parseInt, true);



        return new FlatUpdateRecord(name, new CoordinatesUpdateRecord(x, y), area, numberOfRooms,
                furnish, view, transport, new HouseUpdateRecord(housename, year, numberOfFlatsOnFloor, numberOfLifts));
    }

    /**
     * Отдельный ввод для Enum'ов, т.к их проверка немного отличается
     * @param enumClass
     * @return <T extends Enum<T>
     * @param <T> Enum
     */
    public <T extends Enum<T>> T inputEnumValue(Class<T> enumClass) { //
        while (true) {
            String toCheck = scanner.nextLine().toUpperCase();
            T[] constants = enumClass.getEnumConstants();
            try {
                return T.valueOf(enumClass, toCheck);
            } catch (IllegalArgumentException e) {
                try {
                    return constants[Integer.parseInt(toCheck)];
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e1) {
                    System.out.println("То, что вы ввели - не " + enumClass.getName());
                }
            }
        }
    }

     /**
     * Ввод, обёрнутый в while-true.
     * ПОЛЬЗОВАТЕЛЬ БУДЕТ В НЁМ, ПОКА НЕ ВВЕДЁТ ПРАВИЛЬНОЕ ЗНАЧЕНИЕ.
     * @param inputPrompt Подсказка для пользователя, чтобы он понял, что надо вводить
     * @param checker Consumer<T>, проверяет правильность вводимого значения
     * @param converter Function<String, T> конвертирует ввод в то, что вам надо
     * @param commaToDot Надо ли заменять точки на запятые
     * @return T
     * @param <T>
     */
    public <T> T inputFoolProof(String inputPrompt, Consumer<T> checker, Function<String, T> converter, boolean commaToDot) {
        // Ввод и проверка правильности значения
        int errorInRowCounter = 0;
        System.out.println(inputPrompt);
        T answer = null;
        if (nowRecursion != 0) {
            try {
                String buffer = scanner.nextLine();
                if (commaToDot) {
                    buffer = buffer.replace(',', '.');
                }
                if (!Objects.equals(buffer, "")) {
                    answer = converter.apply(buffer);
                } else {
                    answer = null;
                }
                checker.accept(answer);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                System.err.println("Вы ввели что-то не так. Попробуйте ещё раз =)");
            }
            return answer;
        }
        while (true) {
            try {
                String buffer = scanner.nextLine();
                if (commaToDot) {
                    buffer = buffer.replace(',', '.');
                }
                if (!Objects.equals(buffer, "")) {
                    answer = converter.apply(buffer);
                } else {
                    answer = null;
                }
                checker.accept(answer);
            } catch (RuntimeException e) {
                System.err.println(e.getMessage());
                System.err.println("Вы ввели что-то не так. Попробуйте ещё раз =)");
                errorInRowCounter++;
                if (errorInRowCounter > 5) {
                    System.err.println("Тут явно что-то не так.... Но что же????");
                }
                continue;
            }
            return answer;
        }
    }
    public String simpleInput(String prompt){
        System.out.println(prompt + ": ");
        return scanner.nextLine();
    }
}
