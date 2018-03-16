package common.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 主要工作是为每一个连接成功后返回的SocketChannel关联一个Handler，详见Handler的构造函数
 *
 * @author zhangjj
 * @create 2017-12-19 15:56
 **/
public class Acceptor implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Acceptor.class);

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private boolean isWithThreadPool;

    public Acceptor(Selector selector, ServerSocketChannel serverSocketChannel, boolean isWithThreadPool) {
        this.selector = selector;
        this.serverSocketChannel = serverSocketChannel;
        this.isWithThreadPool = isWithThreadPool;
    }

    public void run() {
        try {
            SocketChannel socketChannel = this.serverSocketChannel.accept();
            if(socketChannel != null){
                if(isWithThreadPool){
                    new HandlerWithThreadPool(this.selector, socketChannel);
                }else{
                    new Handler(this.selector, socketChannel);
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        }

    }
}
