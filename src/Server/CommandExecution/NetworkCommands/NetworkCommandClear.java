package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandClear extends NetworkCommand {
    public NetworkCommandClear(ServerContext serverContext) {
        super(serverContext);
    }


    @Override
    public String execute(Message message) {
        serverContext.getStructureStorage().clearCollection(message.userData().id());
        return "Ok";
    }

    @Override
    public String toString() {
        return "clear : очистить коллекцию";
    }
}
