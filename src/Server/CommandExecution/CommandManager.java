package Server.CommandExecution;

import Classes.CommandMessage;
import Classes.ServerContext;
import Server.CommandExecution.LocalCommands.LocalCommandSave;
import Server.CommandExecution.NetworkCommands.*;
import Server.Network.ServerCommunicationsArray;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс, отвечающий за обработку команд
 */
public class CommandManager {

    @Getter
    private HashMap<String, Class<? extends NetworkCommand>> commands = new HashMap<>(); // Хранит в себе команды
    private HashMap<String, Class<? extends LocalCommand>> notNetworkCommands = new HashMap<>();
    private ServerContext serverContext;

    /*
      Блок, задающий базовые команды
      */
    public CommandManager(ServerContext serverContext) {
        this.serverContext = serverContext;
        addCommand("Server.CommandExecution.Commands.CommandInfo", NetworkCommandInfo.class);
        addCommand("Server.CommandExecution.Commands.CommandShow", NetworkCommandShow.class);
        addCommand("Server.CommandExecution.Commands.CommandAdd", NetworkCommandAdd.class);
        addCommand("Server.CommandExecution.Commands.CommandSort", NetworkCommandSort.class);
        addCommand("Server.CommandExecution.Commands.CommandUpdate", NetworkCommandUpdate.class);
        addCommand("Server.CommandExecution.Commands.CommandRemoveById", NetworkCommandRemoveById.class);
        addCommand("Server.CommandExecution.Commands.CommandClear", NetworkCommandClear.class);
        addCommand("Server.CommandExecution.Commands.CommandRemoveAt", NetworkCommandRemoveAt.class);
        addCommand("Server.CommandExecution.Commands.CommandRemoveLast", NetworkCommandRemoveLast.class);
        addCommand("Server.CommandExecution.Commands.CommandCountLTFurnish", NetworkCommandCountLTFurnish.class);
        addCommand("Server.CommandExecution.Commands.CommandRemoveById", NetworkCommandRemoveById.class);
        addCommand("Server.CommandExecution.Commands.CommandExecuteScript", NetworkCommandExecuteScript.class);
        addCommand("Server.CommandExecution.Commands.CommandSumOfNumberOfRooms", NetworkCommandSumOfNumberOfRooms.class);
        addCommand("Server.CommandExecution.Commands.CommandPrintUniqueHouse", NetworkCommandPrintUniqueHouse.class);
        addCommand("Server.CommandExecution.Commands.CommandCheckIfExists", NetworkCommandCheckIfExists.class);
        addCommand("Server.CommandExecution.Commands.CommandHandShake", NetworkCommandHandShake.class);
        addNotNetoworkCommand("save", LocalCommandSave.class);
    }

    @SneakyThrows
    public String serverExec(String word, String[] args) {
        if (notNetworkCommands.containsKey(word)){
            return notNetworkCommands.get(word).getConstructor(String[].class, ServerContext.class)
                    .newInstance(args, serverContext).execute();
        } return ("Такой команды нет");
    }

    public void addCommand(String s, Class<? extends NetworkCommand> f) {
        commands.put(s, f);
    }

    public void addNotNetoworkCommand(String s, Class<? extends LocalCommand> f) {
        notNetworkCommands.put(s, f);
    }

    public <T> void exec(CommandMessage message, ServerCommunicationsArray communicationsArray) {
        try {
            if (!commands.containsKey(message.commandClass())){
                communicationsArray.sendMessage("No such command");
                Logger.getAnonymousLogger().log(Level.WARNING, "Server got wrong command");
                System.out.println(message);
                return;
            }
            communicationsArray.sendMessage(
                    commands.get(message.commandClass()).getConstructor(ServerContext.class)
                            .newInstance(serverContext).execute(message)
            );
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "CommandManager.exec", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
