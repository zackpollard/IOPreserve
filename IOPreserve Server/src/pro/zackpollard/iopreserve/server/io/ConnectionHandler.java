package pro.zackpollard.iopreserve.server.io;

import pro.zackpollard.iopreserve.server.utils.DataStore;
import pro.zackpollard.iopreserve.server.utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectionHandler implements Runnable {

    private Socket socket;
    private DataStore authedServers;

    private Boolean authenticated = false;
    private Boolean running = true;

    public ConnectionHandler(Socket socket, DataStore authedServers) {

        this.socket = socket;
        this.authedServers = authedServers;

    }

    public void run() {

        PrintWriter out = null;
        BufferedReader in = null;

        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null && running) {

                if (authenticated) {

                    if (inputLine.startsWith("AUTHCODE:")) {

                        String authCode = inputLine.split(":")[1];

                        if (authedServers.contains(authCode)) {

                            authenticated = true;
                            out.println("202:Access Granted!");
                        } else {

                            out.println("401:Access Denied!");
                        }
                    }
                } else {

                    //Commands here!
                }
            }
        } catch (IOException e) {

            Logger.log(Logger.LoggerLevel.ERROR, "Something went wrong and the input or output stream could not be retrieved for a client.", e);
        }
    }
}