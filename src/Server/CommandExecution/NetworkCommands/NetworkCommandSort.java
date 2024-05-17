package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandSort extends NetworkCommand {
    public NetworkCommandSort(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        serverContext.getStructureStorage().sort();
        return "Ok";
    }

    @Override
    public String toString() {
        return "sort : отсортировать коллекцию в естественном порядке";
    }
}
