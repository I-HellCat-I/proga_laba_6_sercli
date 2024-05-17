package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

import java.util.EmptyStackException;

public class NetworkCommandRemoveLast extends NetworkCommand {

    public NetworkCommandRemoveLast(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        try {
            serverContext.getStructureStorage().removeLastFlat();
        } catch (EmptyStackException e){
            return "Стэк пуст, ничего не удалено";
        }
        return "Ok";
    }

    @Override
    public String toString() {
        return "remove_last : удалить последний элемент из коллекции";
    }
}
