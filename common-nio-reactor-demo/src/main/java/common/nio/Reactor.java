package common.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 *
 *   单线程的实现
     Server端用一个Selector利用一个线程（在main方法里面start）来响应所有请求
     1.当ACCEPT事件就绪，Acceptor被选中，执行它的run方法：创建一个Handler（例如为handlerA），并将Handler的interestOps初始为READ
     2.当READ事件就绪，handlerA被选中，执行它的run方法：它根据自身的当前状态，来执行读或写操作
     因此，每一个Client连接过来，Server就创建一个Handler，但都所有操作都在一个线程里面

     Selection Key   Channel                 Handler     Interested Operation
     ------------------------------------------------------------------------
     SelectionKey 0  ServerSocketChannel     Acceptor    Accept
     SelectionKey 1  SocketChannel 1         Handler 1   Read and Write
     SelectionKey 2  SocketChannel 2         Handler 2   Read and Write
     SelectionKey 3  SocketChannel 3         Handler 3   Read and Write

     如果采用多个selector，那就是所谓的“Multiple Reactor Threads”，大体思路如下：

     Selector[] selectors; // also create threads
     int next = 0;
     class Acceptor { // ...
     public synchronized void run() { ...
     Socket connection = serverSocket.accept();
     if (connection != null)
     new Handler(selectors[next], connection);
     if (++next == selectors.length) next = 0;
     }
     }
 * @author zhangjj
 * @create 2017-12-19 15:47
 **/
public class Reactor implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Reactor.class);

    private final Selector selector;
    private final ServerSocketChannel serverSocketChannel;
    private final boolean isWithThreadPool;

    /**
     * Reactor的主要工作：
     * 1.给ServerSocketChannel设置一个Acceptor，接收请求
     * 2.给每一个一个SocketChannel（代表一个Client）关联一个Handler
     * 要注意其实Acceptor也是一个Handler（只是与它关联的channel是ServerSocketChannel而不是SocketChannel）
    * @param
    * @author zhangjj  
    * @Date 2017/12/19 15:51
    * @return 
    * @exception 
    */
    public Reactor(Integer port, boolean isWithThreadPool) throws IOException {
        this.isWithThreadPool = isWithThreadPool;
        this.selector = Selector.open();
        this.serverSocketChannel = ServerSocketChannel.open();
        this.serverSocketChannel.socket().bind(new InetSocketAddress(port));
        this.serverSocketChannel.configureBlocking(false);
        SelectionKey register = this.serverSocketChannel.register(this.selector, SelectionKey.OP_ACCEPT);
        register.attach(new Acceptor(this.selector, this.serverSocketChannel, this.isWithThreadPool));
    }

    public void run() {
        logger.info(String.format("Server listening to port: %s", serverSocketChannel.socket().getLocalPort()));
        try {
            while (!Thread.interrupted()){
                int readySelectionKeyCount = this.selector.select();
                if(readySelectionKeyCount == 0){
                    continue;
                }
                Set<SelectionKey> selected = this.selector.selectedKeys();
                Iterator<SelectionKey> iterator = selected.iterator();
                while(iterator.hasNext()){
                    dispatch(iterator.next());
                }
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    /**
     * 从SelectionKey中取出Handler并执行Handler的run方法，没有创建新线程
    * @param
    * @author zhangjj
    * @Date 2017/12/19 16:08
    * @return
    * @exception
    */
    private void dispatch(SelectionKey selectionKey){
        Runnable attachment = (Runnable) selectionKey.attachment();
        if(attachment != null){
            attachment.run();
        }
    }


}
