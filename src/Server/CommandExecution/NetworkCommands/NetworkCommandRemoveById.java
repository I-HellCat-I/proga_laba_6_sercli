package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandRemoveById extends NetworkCommand {
    public NetworkCommandRemoveById(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        if (!serverContext.getStructureStorage().removeFlatById(message.numericArgument()))
            return ("Квартиры с таким Id не найдено, ничего не удалено");
        return "Ok";
    }

    @Override
    public String toString() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
