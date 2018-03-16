package common.demo.cooperation.producerConsumer2;

import java.util.concurrent.LinkedBlockingDeque;

/**
 * 主协作程序，使用读写错实现 pool
 *
 * @author zhangjj
 * @create 2018-03-15 18:15
 **/
public class Main {

    public static void main(String[] args) {
        Pool<GoodBean> pool = new Pool<>(new LinkedBlockingDeque<>(), 10);

        for(int i = 0; i < 10; ++i){//模拟10个线程生产
            Producer producer = new Producer(pool);
            new Thread(producer).start();
        }

        for(int i = 0; i < 15; ++i){//模拟15个线程消费
            Cunsumer cunsumer = new Cunsumer(pool);
            new Thread(cunsumer).start();
        }
    }

}
