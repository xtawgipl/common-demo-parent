package common.nio.server;

import common.nio.Reactor;
import java.io.IOException;

/**
 * @author zhangjj
 * @create 2017-12-19 16:38
 **/
public class Main {
    public static void main(String[] args) throws IOException {
        int port = 9900;
        boolean withThreadPool = false;
        Reactor reactor  = new Reactor(port, withThreadPool);
        new Thread(reactor).start();
    }
}
