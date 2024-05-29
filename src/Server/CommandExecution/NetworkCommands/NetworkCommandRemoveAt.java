package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandRemoveAt extends NetworkCommand {
    public NetworkCommandRemoveAt(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        serverContext.getStructureStorage().removeFlatAt(message.commandMessage().numericArgument());
        return "Ok";
    }

    @Override
    public String toString() {
        return "remove_at index : удалить элемент, находящийся в заданной позиции коллекции (index)";
    }
}
