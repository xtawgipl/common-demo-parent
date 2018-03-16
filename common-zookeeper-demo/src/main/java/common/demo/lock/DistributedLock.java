package common.demo.lock;

import java.util.concurrent.TimeUnit;

/**
 * 分页式锁接口
 *
 * @author zhangjj
 * @create 2017-12-27 17:31
 **/
public interface DistributedLock {

    /**
     * 获取锁，如果没有得到就等待
     * @param
     * @author zhangjj
     * @Date
     * @return
     * @exception
     *
     */
    void acquire() throws Exception;

    /**
     * 获取锁，直到超时
     * @param time 超时时间,unit 参数的单位
     * @author zhangjj
     * @Date  
     * @return 
     * @exception 
     * 
     */
    boolean acquire(long time, TimeUnit unit) throws Exception;

    /**
     * 释放锁
     * @param 
     * @author zhangjj
     * @Date  
     * @return 
     * @exception 
     * 
     */
    void release() throws Exception;
}
