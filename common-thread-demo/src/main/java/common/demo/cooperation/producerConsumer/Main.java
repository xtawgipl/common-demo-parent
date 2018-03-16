package common.demo.cooperation.producerConsumer;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 主类 jdk Object 中的wait notify实现 生产者与消费者协作模式
 *
 * @author zhangjj
 * @create 2018-03-15 16:05
 **/
public class Main {

    public static void main(String[] args) {
        Pool<GoodBean> pool = new Pool<>(new LinkedBlockingDeque<>(), 10);

        Producer producer = new Producer(pool);
        new Thread(producer).start();

        Cunsumer cunsumer = new Cunsumer(pool);
        new Thread(cunsumer).start();
    }
}
