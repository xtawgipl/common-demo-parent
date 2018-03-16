package common.demo.cooperation.producerConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

/**
 * 生产物池, jdk Object 中的wait notify实现
 *
 * @author zhangjj
 * @create 2018-03-15 15:52
 **/
public class Pool<E> {

    private Logger logger = LoggerFactory.getLogger(Pool.class);

    private Queue<E> queue;

    private int limit;

    public Pool(Queue<E> queue, int limit) {
        this.queue = queue;
        this.limit = limit;
    }

    public synchronized void put(E e) throws InterruptedException {
        if(queue.size() == limit){
            logger.info("池满，等待消费...");
            wait();
        }
        queue.add(e);
        notifyAll();
    }

    public synchronized E take() throws InterruptedException {
        if(queue.size() == 0){
            logger.info("池空，等待生产...");
            wait();
        }
        E e = queue.poll();
        notifyAll();
        return e;
    }
}
