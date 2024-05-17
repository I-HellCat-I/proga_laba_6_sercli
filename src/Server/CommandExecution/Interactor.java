package Server.CommandExecution;

import Classes.ServerContext;

import java.util.Scanner;
import java.util.Stack;
import java.util.TreeSet;

/**
 * Класс, отвечающий за обработку пользовательского ввода.
 */
public class Interactor {

    private static Scanner scanner = new Scanner(System.in);
    private static int nowRecursion = 0;
    private static Stack<Scanner> scannerStack = new Stack<>();
    private static TreeSet<String> executingFilenames = new TreeSet<>();
    private ServerContext serverContext;

    public Interactor(ServerContext serverContext){
        this.serverContext = serverContext;
    }

    /**
     * Костыль, который убирает проблемы с рекурсией
     * @param input Строка, которая подаётся на обработку
     */
    public void masterProcessInput(String input) { //
        try {
            processInput(input);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            scannerStack.clear();
            nowRecursion = 0;
        }
    }

    /**
     * Базовый обработчик ввода, скидывает обработанную команду менеждеру команд.
     * @param input вводимая строка
     */
    void processInput(String input) {
        String[] words = input.trim().split(" ");
        String[] args = words.length > 1 ? new String[words.length - 1] : null;
        if (args != null) System.arraycopy(words, 1, args, 0, words.length - 1);
        try {
            System.out.println(serverContext.getCommandManager().serverExec(words[0], args));
        } catch (NullPointerException e) {
            System.out.println("Такой команды нет");
        }
    }
}
