package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

public class CommandSumOfNumberOfRooms extends Command {
    public CommandSumOfNumberOfRooms(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        return String.valueOf(serverContext.getStructureStorage().getNumberOfRoomsSum());
    }

    @Override
    public String toString() {
        return "sum_of_number_of_rooms : вывести сумму значений поля numberOfRooms для всех элементов коллекции";
    }
}
