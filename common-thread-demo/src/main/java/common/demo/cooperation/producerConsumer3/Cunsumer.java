package common.demo.cooperation.producerConsumer3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费者
 *
 * @author zhangjj
 * @create 2018-03-15 16:01
 **/
public class Cunsumer implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Cunsumer.class);

    private Pool<GoodBean> pool;

    public Cunsumer(Pool<GoodBean> pool) {
        this.pool = pool;
    }


    @Override
    public void run() {
        while(true){
            try {
                GoodBean good = this.pool.take();
                if(good != null){
                    logger.info("消费商品 : {}", good.getName());
                }
                Thread.sleep(800L);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }
}
