package Server.CommandExecution.Commands;

import Classes.ServerContext;
import Classes.House;
import Server.CommandExecution.Command;
import Server.Network.CommandMessage;

import java.util.ArrayList;

public class CommandPrintUniqueHouse extends Command {
    public CommandPrintUniqueHouse(ServerContext serverContext) {
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
