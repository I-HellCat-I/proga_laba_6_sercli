package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandClear extends NetworkCommand {
    public NetworkCommandClear(ServerContext serverContext) {
        super(serverContext);
    }


    @Override
    public String execute(CommandMessage message) {
        serverContext.getStructureStorage().clearCollection();
        return "Ok";
    }

    @Override
    public String toString() {
        return "clear : очистить коллекцию";
    }
}
