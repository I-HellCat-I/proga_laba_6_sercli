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
        return  String.valueOf(manager.authUser(message.userData().username(), message.userData().password().toCharArray()));
    }

    @Override
    public String toString() {
        return null;
    }
}
