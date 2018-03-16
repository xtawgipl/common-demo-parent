package common.demo.cooperation.producerConsumer2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 商品池 条件式实现
 * Condition相比Object.await Condition.notify更细，await notify必须所有锁上的线程等待或唤醒
 * 而Condition 可以只唤醒某条件下的线程，比如只唤醒生产线程或只唤醒消费线程
 *
 * @author zhangjj
 * @create 2018-03-15 18:05
 **/
public class Pool<E> {
    private Logger logger = LoggerFactory.getLogger(Pool.class);

    private ReentrantLock lock = new ReentrantLock();

    private Queue<E> queue;

    private int limit;

    private Condition notFull;

    private Condition notEmpty;

    public Pool(Queue<E> queue, int limit) {
        this.queue = queue;
        this.limit = limit;
        notFull = lock.newCondition();
        notEmpty = lock.newCondition();
    }

    public void put(E e) {
        try {
            lock.lock();
            if(queue.size() == limit){
                logger.info("池满，等待消费...");
                notFull.await();
            }
            queue.add(e);
            notEmpty.signalAll();//只唤醒所有消费线程
        } catch (InterruptedException e1) {
            logger.error("", e);
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
                notEmpty.await();
            }
            e = queue.poll();
            notFull.signalAll();//只唤醒所有生产线程
        } catch (InterruptedException e1) {
            logger.error("", e1);
        } finally {
            lock.unlock();
        }
        return e;
    }
}
