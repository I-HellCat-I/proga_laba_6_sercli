package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandUpdate extends NetworkCommand {
    public NetworkCommandUpdate(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        if (!serverContext.getStructureStorage().updateFlatByRecord(message.commandMessage().sendedFlatUpdateRecord())) {
            return ("Квартиры с таким Id не найдено, ничего обновляться не будет");
        }
        return "Ok";
    }

    @Override
    public String toString() {
        return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
    }
}
