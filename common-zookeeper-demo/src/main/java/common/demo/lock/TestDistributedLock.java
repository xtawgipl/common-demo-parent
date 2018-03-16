package common.demo.lock;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 测试类
 *
 * @author zhangjj
 * @create 2017-12-28 10:39
 **/
public class TestDistributedLock {
    private static String connString = "192.168.1.117:2181,192.168.1.117:2182,192.168.1.117:2183";

    public static void main(String[] args) {
        final ZkClient zkClientExt1 = new ZkClient(connString, 5000, 5000, new BytesPushThroughSerializer());
        final SimpleDistributedLockMutex mutex1 = new SimpleDistributedLockMutex(zkClientExt1, "/Mutex");

        final ZkClient zkClientExt2 = new ZkClient(connString, 5000, 5000, new BytesPushThroughSerializer());
        final SimpleDistributedLockMutex mutex2 = new SimpleDistributedLockMutex(zkClientExt2, "/Mutex");

        try {
            mutex1.acquire();
            System.out.println("Client1 locked");
            Thread client2Thd = new Thread(new Runnable() {

                public void run() {
                    try {
                        mutex2.acquire();
                        System.out.println("Client2 locked");
                        mutex2.release();
                        System.out.println("Client2 released lock");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            client2Thd.start();
            Thread.sleep(5000);
            mutex1.release();
            System.out.println("Client1 released lock");
            client2Thd.join();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
