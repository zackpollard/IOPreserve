package pro.zackpollard.iopreserve.server;

import pro.zackpollard.iopreserve.server.config.Config;
import pro.zackpollard.iopreserve.server.config.ConfigException;

/**
 * @Author zack
 * @Date 14/11/14.
 **/
public class IOPreserveServer {

    Config config;

    public void main(String[] args) {

        if(args.length == 0) {


        }
    }

    public void initServer() {


    }

    public initiateConfig() {

        try {

            Config config = new Config("iopreserveserver.config");
        } catch(ConfigException e) {


        }
    }
}
