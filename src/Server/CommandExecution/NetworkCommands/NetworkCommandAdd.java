package Server.CommandExecution.NetworkCommands;

import Classes.Flat;
import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;


public class NetworkCommandAdd extends NetworkCommand {
    public NetworkCommandAdd(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        return String.valueOf(serverContext.getStructureStorage().addFlat(new Flat(message.commandMessage().sendedFlatUpdateRecord().flatUpdateRecord()), message.userData().id()));
    }

    @Override
    public String toString() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
