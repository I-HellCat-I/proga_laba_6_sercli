package Classes;

import java.net.InetSocketAddress;

public record Message(CommandMessage commandMessage, InetSocketAddress address, UserData userData) {
}
