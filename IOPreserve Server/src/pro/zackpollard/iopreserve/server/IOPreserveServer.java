package pro.zackpollard.iopreserve.server;

import pro.zackpollard.iopreserve.server.io.ConnectionListener;
import pro.zackpollard.iopreserve.server.utils.DataStore;

/**
 * @Author zack
 * @Date 14/11/14.
 **/

public class IOPreserveServer {

    private DataStore authedServers = new DataStore("./authedServers.conf");

    public void main(String[] args) {

        if(args.length == 0) {

            initListener();
        }
    }

    public void initListener() {

        new Thread(new ConnectionListener(authedServers)).start();
    }

    public DataStore getAuthedServers() {

        return authedServers;
    }
}