package common.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * 单线程版本的Handler
 *
 * @author zhangjj
 * @create 2017-12-19 16:09
 **/
public class Handler implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Handler.class);

    final SocketChannel socketChannel;
    final SelectionKey selectionKey;
    ByteBuffer input = ByteBuffer.allocate(1024);
    static final int READING = 0;
    static final int SENDING = 1;

    int state = READING;
    String clientName = "";

    /**
     * 在handler里面设置interestOps，而且这个interestOps是会随着事件的进行而改变的
    * @param
    * @author zhangjj
    * @Date 2017/12/19 16:15
    * @return
    * @exception
    */
    public Handler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        this.socketChannel.configureBlocking(false);
        this.selectionKey = this.socketChannel.register(selector, 0);
        this.selectionKey.attach(this);
        this.selectionKey.interestOps(SelectionKey.OP_READ);
        selector.wakeup();
    }

    /**
     * 在Reactor的dispatch方法里面被调用，但是直接的方法调用，没有创建新线程
    * @param
    * @author zhangjj
    * @Date 2017/12/19 16:20
    * @return
    * @exception
    */
    public void run() {
        try {
            if(state == READING){
                read();
            }else if(state == SENDING){
                send();
            }
        } catch (IOException e) {
            logger.error("", e);
        }
    }

    void read() throws IOException {
        int readCount = this.socketChannel.read(this.input);
        if(readCount > 0){
            readProcess(readCount);
        }
        state = SENDING;
        selectionKey.interestOps(SelectionKey.OP_WRITE);
    }

    /**
     * 非IO操作（业务逻辑，实际应用中可能会非常耗时）：将Client发过来的信息（clientName）转成字符串形式
    * @param
    * @author zhangjj
    * @Date 2017/12/19 16:25
    * @return
    * @exception
    */
    synchronized void readProcess(int readCount){
        StringBuilder sb = new StringBuilder();
        input.flip();
        byte[] subStringBytes = new byte[readCount];
        byte[] array = input.array();
        System.arraycopy(array, 0, subStringBytes, 0, readCount);
        sb.append(new String(subStringBytes));
        input.clear();
        clientName = sb.toString().trim();
    }

    void send() throws IOException {
        System.out.println("Saying hello to " + clientName);
        ByteBuffer output = ByteBuffer.wrap(("Hello " + clientName + "\n").getBytes());
        socketChannel.write(output);
        selectionKey.interestOps(SelectionKey.OP_READ);
        state = READING;
    }
}
