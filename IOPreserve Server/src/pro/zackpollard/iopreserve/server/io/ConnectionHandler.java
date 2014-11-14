package pro.zackpollard.iopreserve.server.io;

import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionHandler {

    Socket socket;

    public ConnectionHandler(Socket socket) {

        this.socket = socket;
    }
}
