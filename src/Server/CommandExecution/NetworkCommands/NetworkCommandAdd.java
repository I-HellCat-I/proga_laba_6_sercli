package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.Flat;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;


public class NetworkCommandAdd extends NetworkCommand {
    public NetworkCommandAdd(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        serverContext.getStructureStorage().addFlat(new Flat(message.sendedFlatUpdateRecord().flatUpdateRecord()));
        return "Ok";
    }

    @Override
    public String toString() {
        return "add {element} : добавить новый элемент в коллекцию";
    }
}
