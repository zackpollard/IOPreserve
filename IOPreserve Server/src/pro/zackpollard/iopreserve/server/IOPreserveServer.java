package pro.zackpollard.iopreserve.server;

import pro.zackpollard.iopreserve.server.io.ConnectionListener;

/**
 * @Author zack
 * @Date 14/11/14.
 **/
public class IOPreserveServer {

    public void main(String[] args) {

        if(args.length == 0) {

            initListener();
        }
    }

    public void initListener() {

        new Thread(new ConnectionListener()).start();
    }
}