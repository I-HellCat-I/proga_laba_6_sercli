package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;
import Server.Network.ClientHandler;
import Server.Network.PasswordHandler;
import Server.Network.PostgresManager;

import java.io.IOException;

public class NetworkCommandLogin extends NetworkCommand {
    public NetworkCommandLogin(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) throws IOException {
        PostgresManager manager = new PostgresManager(serverContext);
        int id = manager.authUser(message.userData().username(), message.userData().password().toCharArray());
        if (id == -1){
            return "Вы ввели неверный логин и/или пароль. Попробуйте снова";
        }
        return "Вы залогинены как: " + message.userData().username() + ". Ваш id: " + id;
    }

    @Override
    public String toString() {
        return null;
    }
}
