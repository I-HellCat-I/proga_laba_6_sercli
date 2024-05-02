package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

public class CommandUpdate extends Command {
    public CommandUpdate(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        if (serverContext.getStructureStorage().updateFlatByRecord(message.sendedFlatUpdateRecord())) {
            return ("Квартиры с таким Id не найдено, ничего обновляться не будет");
        }
        return "Ok";
    }

    @Override
    public String toString() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
