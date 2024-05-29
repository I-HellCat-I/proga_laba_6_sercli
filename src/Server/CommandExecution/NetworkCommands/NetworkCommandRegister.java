package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;
import Server.Network.PostgresManager;

import java.io.IOException;

public class NetworkCommandRegister extends NetworkCommand {
    public NetworkCommandRegister(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) throws IOException {
        PostgresManager manager = new PostgresManager(serverContext);
        int id = manager.regUser(message.userData().username(), message.userData().password().toCharArray());
        if (id == -1){
            return "Что-то пошло не так";
        } return "Вы залогинены как: " + message.userData().username() + ". Ваш id: " + id;
    }

    @Override
    public String toString() {
        return null;
    }
}
