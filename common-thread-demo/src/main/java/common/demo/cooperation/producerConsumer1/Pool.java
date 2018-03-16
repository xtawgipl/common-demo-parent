package common.demo.cooperation.producerConsumer1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 商品池 读写锁实现
 *
 * @author zhangjj
 * @create 2018-03-15 18:05
 **/
public class Pool<E> {
    private Logger logger = LoggerFactory.getLogger(Pool.class);

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    private Queue<E> queue;

    private int limit;

    public Pool(Queue<E> queue, int limit) {
        this.queue = queue;
        this.limit = limit;
    }

    public void put(E e) {
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if(queue.size() == limit){
                logger.info("池满，等待消费...");
            }else{
                queue.add(e);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public E take() {
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        E e = null;
        try {
            readLock.lock();
            if(queue.size() == 0){
                logger.info("池空，等待生产...");
            }else{
                e = queue.poll();
            }
        } finally {
            readLock.unlock();
        }
        return e;
    }
}
