package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

import java.util.EmptyStackException;

public class CommandRemoveLast extends Command {

    public CommandRemoveLast(ServerContext serverContext) {
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
