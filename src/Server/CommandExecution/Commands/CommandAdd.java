package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Classes.Flat;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;


public class CommandAdd extends Command {
    public CommandAdd(ServerContext serverContext) {
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
