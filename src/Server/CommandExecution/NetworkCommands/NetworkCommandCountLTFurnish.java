package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandCountLTFurnish extends NetworkCommand {
    public NetworkCommandCountLTFurnish(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        return String.valueOf(serverContext.getStructureStorage().countLTFurnish(message.numericArgument()));
    }

    @Override
    public String toString() {
        return "count_less_than_furnish furnish : вывести количество элементов, значение поля furnish которых меньше заданного";
    }
}
