package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandRemoveById extends NetworkCommand {
    public NetworkCommandRemoveById(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        if (!serverContext.getStructureStorage().removeFlatById(message.commandMessage().numericArgument(), message.userData().id()))
            return ("Квартиры с таким Id не найдено, ничего не удалено");
        return "Ok";
    }

    @Override
    public String toString() {
        return "remove_by_id id : удалить элемент из коллекции по его id";
    }
}
