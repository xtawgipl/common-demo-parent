package common.demo.cooperation.fire;

/**
 * 同时开始控制位
 *
 * @author zhangjj
 * @create 2018-03-15 16:26
 **/
public class FireFlag {
    /** 是否可以同时开始标志位 */
    private volatile boolean fired = false;

    /**
     * 等待条件
     * @param
     * @author zhangjj
     * @Date 2018/3/15 16:28
     * @return
     * @exception
     *
     */
    public synchronized void waitForFire() throws InterruptedException {
        while(!fired){
            wait();
        }
    }

    /**
     * 设置条件成立，并同时唤醒所有
     * @param
     * @author zhangjj
     * @Date 2018/3/15 16:28
     * @return
     * @exception
     *
     */
    public synchronized void fire(){
        fired = true;
        notifyAll();
    }
}
