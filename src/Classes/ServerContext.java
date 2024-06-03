package Classes;

import Server.CommandExecution.CommandManager;
import Server.Network.ServerCommunicationsArray;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.lang.ref.Cleaner;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Общий контекст, содержит ссылки на переменные, используемые в разных частях программы, ибо мне лень их передавать
 **/
@Getter
public class ServerContext {

    protected final StructureStorage structureStorage = new StructureStorage(this);
    protected final FileManager fileManager = new FileManager(this);
    protected final String pathVar = "HOME";
    protected boolean exitCommandUsed = false;
    protected final ZonedDateTime initDate = ZonedDateTime.now();
    protected ServerCommunicationsArray serverCommunicationsArray;

    {
        try {
            serverCommunicationsArray = new ServerCommunicationsArray();
        } catch (IOException e) {
            Logger.getAnonymousLogger().log(Level.SEVERE, e.getMessage());
        }
    }
    @Setter
    protected int maxRecursionDepth = 100;
    protected CommandManager commandManager = new CommandManager(this);
    public ServerContext(){
        structureStorage.load();
        Runtime.getRuntime().addShutdownHook(new Thread(structureStorage.getOnCleanRunnable()));
        Cleaner cl = Cleaner.create();
        cl.register(structureStorage, structureStorage.getOnCleanRunnable());
    }




    public String getPath() {
        return System.getenv(pathVar) + "/saved.xml";
    }

    public void setExitCommandUsed() {
        exitCommandUsed = true;
    }

}
