package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

public class CommandCountLTFurnish extends Command {
    public CommandCountLTFurnish(ServerContext serverContext) {
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
