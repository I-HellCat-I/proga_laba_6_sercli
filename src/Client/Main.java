package Client;

import Classes.ClientContext;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        ClientContext clientContext;
        try {
            clientContext = new ClientContext();
        } catch (RuntimeException e){
            System.out.println(Arrays.toString(e.getStackTrace()));
            return;
        }
        System.out.println("Перед использованием программы надо залогиниться или зарегистрироваться" +
                "\nlogin - для логина\nregister - для регистрации\nhelp - если вы что-то забыли");
        while (true){
            System.out.print(">");
            clientContext.getInteractor().masterProcessInput(clientContext.getInteractor().getScanner().nextLine());
        }
    }
}