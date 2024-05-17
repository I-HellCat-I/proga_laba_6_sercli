package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandRemoveAt extends NetworkCommand {
    public NetworkCommandRemoveAt(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        serverContext.getStructureStorage().removeFlatAt(message.numericArgument());
        return "Ok";
    }

    @Override
    public String toString() {
        return "remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)";
    }
}
