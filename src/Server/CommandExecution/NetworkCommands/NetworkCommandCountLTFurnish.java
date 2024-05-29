package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandCountLTFurnish extends NetworkCommand {
    public NetworkCommandCountLTFurnish(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        return String.valueOf(serverContext.getStructureStorage().countLTFurnish(message.commandMessage().numericArgument()));
    }

    @Override
    public String toString() {
        return "count_less_than_furnish furnish : вывести количество элементов, значение поля furnish которых меньше заданного";
    }
}
