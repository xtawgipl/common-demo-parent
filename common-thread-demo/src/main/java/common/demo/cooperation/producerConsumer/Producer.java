package common.demo.cooperation.producerConsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生产者
 *
 * @author zhangjj
 * @create 2018-03-15 15:58
 **/
public class Producer implements Runnable{

    private Logger logger = LoggerFactory.getLogger(Producer.class);

    private Pool<GoodBean> pool;

    public Producer(Pool<GoodBean> pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(500L);//模拟生产时间
            } catch (InterruptedException e) {
                logger.error("", e);
            }
            GoodBean goodBean = new GoodBean();
            goodBean.setName("goodName_" + System.currentTimeMillis());
            try {
                logger.info("生产商品 : {}", goodBean.getName());
                this.pool.put(goodBean);
            } catch (InterruptedException e) {
                logger.error("", e);
            }
        }
    }
}
