package pro.zackpollard.iopreserve.server.io;

import pro.zackpollard.iopreserve.server.utils.DataStore;
import pro.zackpollard.iopreserve.server.utils.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author zack
 * @Date 14/11/14.
 */

public class ConnectionListener implements Runnable {

    private DataStore authedServers;

    public ConnectionListener(DataStore authedServers) {

        this.authedServers = authedServers;
    }

    public void run() {

        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(8123, 100);
        } catch (IOException e) {

            Logger.log(Logger.LoggerLevel.FATAL, "The server was not able to open the socket set in the config, is it already in use?", e);
            System.exit(-1);
        }

        while (true) {

            Socket socket = null;
            try {

                socket = serverSocket.accept();
            } catch (IOException e) {

                Logger.log(Logger.LoggerLevel.ERROR, "There was an error when accepting the connection from the ServerSocket.", e);
            }
            new ConnectionHandler(socket, authedServers);
        }
    }
}