package Client.Network;

import java.net.InetSocketAddress;

public record Message(CommandMessage commandMessage, InetSocketAddress address) {
}
