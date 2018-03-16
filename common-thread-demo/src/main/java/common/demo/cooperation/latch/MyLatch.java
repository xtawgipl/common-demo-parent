package common.demo.cooperation.latch;

/**
 * 等待线程执行完
 *
 * @author zhangjj
 * @create 2018-03-15 16:40
 **/
public class MyLatch {

    private volatile int count;

    public MyLatch(int count) {
        this.count = count;
    }

    public synchronized void countDown(){
        if (--count == 0) {
            notifyAll();
        }
    }

    public synchronized void await() throws InterruptedException {
        if(count != 0){
            wait();
        }
    }
}
