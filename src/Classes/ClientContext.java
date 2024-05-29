package Classes;

import Client.CommandExecution.CommandManager;
import Client.CommandExecution.Interactor;
import Client.Network.CommunicationsArray;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Общий контекст, содержит ссылки на переменные, используемые в разных частях программы, ибо мне лень их передавать
 **/
@Getter
public class ClientContext {

    protected final CommunicationsArray communicationsArray;
    protected final Interactor interactor = new Interactor(this);
    protected final String pathVar = "HOME";
    protected boolean exitCommandUsed = false;
    protected final ZonedDateTime initDate = ZonedDateTime.now();
    protected final CommandManager commandManager = new CommandManager(this);
    @Setter
    protected int maxRecursionDepth = 100;

    private String username;
    private String password;

    public ClientContext() {
        try {
            int incomePort = 3123;
            communicationsArray = new CommunicationsArray(this, InetAddress.getLocalHost(), incomePort);
        } catch (SocketException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void login(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void logout(){
        this.password = null;
        this.username = null;
    }
    public UserData getUserData(){
        return new UserData(username, password);
    }

    public boolean isLogged(){
        return !Objects.equals(password, "") && !Objects.equals(username, "");
    }

    public String getPath() {
        return System.getenv(pathVar) + "/saved.xml";
    }

    public void setExitCommandUsed() {
        exitCommandUsed = true;
    }

}
