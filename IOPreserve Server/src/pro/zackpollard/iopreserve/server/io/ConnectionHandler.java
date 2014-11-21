package pro.zackpollard.iopreserve.server.io;

import pro.zackpollard.iopreserve.server.utils.DataStore;
import pro.zackpollard.iopreserve.server.utils.Logger;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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

        PrintWriter out;
        BufferedReader in;

        try {

            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null && running) {

                if (!authenticated) {

                    if (inputLine.startsWith("AUTHCODE")) {

                        String authCode = in.readLine();

                        if (authedServers.contains(authCode)) {

                            authenticated = true;
                            out.println("202:Access Granted!");
                        } else {

                            out.println("401:Access Denied!");
                        }
                    } else {

                        out.println("403:Access Denied!");
                    }
                } else {

                    if (inputLine.startsWith("BACKUP")) {

                        String folderPath = in.readLine();
                        String fileName = in.readLine();

                        File storeFile = new File("./backupStore" + File.separator + socket.getInetAddress().getHostAddress() + File.separator + folderPath);

                        storeFile.mkdirs();

                        storeFile = new File(storeFile + File.separator + fileName);
                        storeFile.createNewFile();
                        byte[] byteArray = new byte[1024];

                        InputStream fileInput = socket.getInputStream();
                        BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(storeFile));

                        int bytesRead = fileInput.read(byteArray, 0, byteArray.length);
                        fileOutput.write(byteArray, 0, bytesRead);
                        fileOutput.close();

                    } else if (inputLine.equals("GETBACKUPLIST")) {

                        String folderPath = in.readLine();

                        File folder = new File("./backupStore" + File.separator + socket.getInetAddress().getHostAddress() + File.separator + folderPath);

                        File[] zipFiles = folder.listFiles(new FileFilter() {
                            @Override
                            public boolean accept(File file) {
                                return (file.getName().endsWith(".zip") || file.getName().endsWith(".tar.gz"));
                            }
                        });

                        for (File file : zipFiles) {

                            out.println(file.getName());
                        }

                        out.println("200:List Sent!");
                    }
                }
            }
        } catch (IOException e) {

            Logger.log(Logger.LoggerLevel.ERROR, "Something went wrong and the input or output stream could not be retrieved for a client.", e);
        }
    }
}