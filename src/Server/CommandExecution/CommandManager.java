package Server.CommandExecution;

import Classes.CommandMessage;
import Classes.Message;
import Classes.ServerContext;
import Client.Network.CommunicationsArray;
import Server.CommandExecution.LocalCommands.LocalCommandSave;
import Server.CommandExecution.NetworkCommands.*;
import Server.Network.ServerCommunicationsArray;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
    private CommandThreadFactory commandThreadFactory;

    private ExecutorService executorService;

    /*
      Блок, задающий базовые команды
      */
    public CommandManager(ServerContext serverContext) {
        this.serverContext = serverContext;
        addCommand("Client.CommandExecution.Commands.CommandInfo", NetworkCommandInfo.class);
        addCommand("Client.CommandExecution.Commands.CommandShow", NetworkCommandShow.class);
        addCommand("Client.CommandExecution.Commands.CommandAdd", NetworkCommandAdd.class);
        addCommand("Client.CommandExecution.Commands.CommandSort", NetworkCommandSort.class);
        addCommand("Client.CommandExecution.Commands.CommandUpdate", NetworkCommandUpdate.class);
        addCommand("Client.CommandExecution.Commands.CommandRemoveById", NetworkCommandRemoveById.class);
        addCommand("Client.CommandExecution.Commands.CommandClear", NetworkCommandClear.class);
        addCommand("Client.CommandExecution.Commands.CommandRemoveAt", NetworkCommandRemoveAt.class);
        addCommand("Client.CommandExecution.Commands.CommandRemoveLast", NetworkCommandRemoveLast.class);
        addCommand("Client.CommandExecution.Commands.CommandCountLTFurnish", NetworkCommandCountLTFurnish.class);
        addCommand("Client.CommandExecution.Commands.CommandRemoveById", NetworkCommandRemoveById.class);
        addCommand("Client.CommandExecution.Commands.CommandExecuteScript", NetworkCommandExecuteScript.class);
        addCommand("Client.CommandExecution.Commands.CommandSumOfNumberOfRooms", NetworkCommandSumOfNumberOfRooms.class);
        addCommand("Client.CommandExecution.Commands.CommandPrintUniqueHouse", NetworkCommandPrintUniqueHouse.class);
        addCommand("Client.CommandExecution.Commands.CommandCheckIfExists", NetworkCommandCheckIfExists.class);
        addCommand("Client.CommandExecution.Commands.CommandHandShake", NetworkCommandHandShake.class);
        addCommand("Client.CommandExecution.Commands.CommandLogin", NetworkCommandLogin.class);
        addCommand("Client.CommandExecution.Commands.CommandRegister", NetworkCommandRegister.class);
        addNotNetoworkCommand("save", LocalCommandSave.class);
        commandThreadFactory = new CommandThreadFactory(serverContext);
        executorService = Executors.newCachedThreadPool(commandThreadFactory);
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

    public <T> void exec(Message message, SocketAddress client) {
        try {
            if (!commands.containsKey(message.commandMessage().commandClass())){
                ServerCommunicationsArray.sendMessage("No such command", client);
                Logger.getAnonymousLogger().log(Level.WARNING, "Server got wrong command");
                System.out.println(message);
                return;
            }
            executorService.execute(new CommandRunnable(commands.get(message.commandMessage().commandClass()).getConstructor(ServerContext.class)
                    .newInstance(serverContext),client, message));

        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException |
                 IOException e) {
            Logger.getAnonymousLogger().log(Level.WARNING, "CommandManager.exec", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
