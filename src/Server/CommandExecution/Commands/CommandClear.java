package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

public class CommandClear extends Command {
    public CommandClear( ServerContext serverContext) {
        super(serverContext);
    }


    @Override
    public String execute(CommandMessage message) {
        serverContext.getStructureStorage().clearCollection();
        return "Ok";
    }

    @Override
    public String toString() {
        return "clear : очистить коллекцию";
    }
}
