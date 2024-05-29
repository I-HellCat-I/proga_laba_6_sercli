package Server.CommandExecution.NetworkCommands;

import Classes.Message;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

public class NetworkCommandSumOfNumberOfRooms extends NetworkCommand {
    public NetworkCommandSumOfNumberOfRooms(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(Message message) {
        return String.valueOf(serverContext.getStructureStorage().getNumberOfRoomsSum());
    }

    @Override
    public String toString() {
        return "sum_of_number_of_rooms : вывести сумму значений поля numberOfRooms для всех элементов коллекции";
    }
}
