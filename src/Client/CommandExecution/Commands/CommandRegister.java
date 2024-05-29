package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.CommandMessage;
import Client.CommandExecution.Command;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class CommandRegister extends Command {
    public CommandRegister(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        if (clientContext.isLogged()){
            return "Перед регистрацией нового аккаунта, пожалуйста разлогиньтесь";
        }
        String username = clientContext.getInteractor().simpleInput("Введите имя пользователя: ");
        String password =  clientContext.getInteractor().simpleInput("Введите пароль: ");
        clientContext.login(username, password);
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(), -1, null));
        try {
            String answer = clientContext.getCommunicationsArray().getMessage(String.class);
            if (Objects.equals(answer, "Что-то пошло не так")){
                clientContext.logout();
            }
            return answer;
        } catch (SocketTimeoutException e){
            clientContext.logout();
            throw new SocketTimeoutException(e.getMessage());
        }
    }
}