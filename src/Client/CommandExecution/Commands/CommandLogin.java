package Client.CommandExecution.Commands;

import Classes.ClientContext;
import Classes.CommandMessage;
import Classes.UserData;
import Client.CommandExecution.Command;
import Client.Network.CommunicationsArray;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Objects;

public class CommandLogin extends Command {

    public CommandLogin(String[] args, ClientContext clientContext) {
        super(args, clientContext);
    }

    @Override
    public String execute() throws IOException {
        String username = clientContext.getInteractor().simpleInput("Введите имя пользователя: ");
        String password =  clientContext.getInteractor().simpleInput("Введите пароль: ");
        clientContext.login(username, password);
        clientContext.getCommunicationsArray().sendMessage(new CommandMessage(this.getClass().getName(), -1, null));
        try {
            String answer = clientContext.getCommunicationsArray().getMessage(String.class);
            if (Objects.equals(answer, "Вы ввели неверный логин и/или пароль. Попробуйте снова")){
                clientContext.logout();
            }
            return answer;
        } catch (SocketTimeoutException e){
            clientContext.logout();
            throw new SocketTimeoutException(e.getMessage());
        }
    }
}
