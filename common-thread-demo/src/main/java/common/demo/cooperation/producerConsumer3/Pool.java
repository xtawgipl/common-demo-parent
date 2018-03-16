package common.demo.cooperation.producerConsumer3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 商品池 重入锁实现
 *
 * @author zhangjj
 * @create 2018-03-15 18:05
 **/
public class Pool<E> {
    private Logger logger = LoggerFactory.getLogger(Pool.class);

    private ReentrantLock lock = new ReentrantLock();

    private Queue<E> queue;

    private int limit;

    public Pool(Queue<E> queue, int limit) {
        this.queue = queue;
        this.limit = limit;
    }

    public void put(E e) {
        try {
            lock.lock();
            if(queue.size() == limit){
                logger.info("池满，等待消费...");
            }else{
                queue.add(e);
            }
        } finally {
            lock.unlock();
        }
    }

    public E take() {
        E e = null;
        try {
            lock.lock();
            if(queue.size() == 0){
                logger.info("池空，等待生产...");
            }else{
                e = queue.poll();
            }
        } finally {
            lock.unlock();
        }
        return e;
    }
}
