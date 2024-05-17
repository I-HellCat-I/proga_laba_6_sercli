package Server.CommandExecution.NetworkCommands;

import Classes.CommandMessage;
import Classes.House;
import Classes.ServerContext;
import Server.CommandExecution.NetworkCommand;

import java.util.ArrayList;

public class NetworkCommandPrintUniqueHouse extends NetworkCommand {
    public NetworkCommandPrintUniqueHouse(ServerContext serverContext) {
        super(serverContext);
    }

    @Override
    public String execute(CommandMessage message) {
        ArrayList<House> uniques = serverContext.getStructureStorage().getUniqueHouse();
        return String.valueOf(uniques);
    }

    @Override
    public String toString() {
        return "print_unique_house : вывести уникальные значения поля house всех элементов в коллекции";
    }
}
