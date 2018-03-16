package common.demo.cooperation.semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;

/**
 * 限制同时可以登录系统的用户数
 *
 * @author zhangjj
 * @create 2018-03-16 17:41
 **/
public class AccessControlService {

    private Logger logger = LoggerFactory.getLogger(AccessControlService.class);

    private static final int MAX_PERMIT = 10;

    private Semaphore permits = new Semaphore(MAX_PERMIT);

    public boolean login(){
        boolean b = permits.tryAcquire();
        if(b){
            logger.info("登录成功 {}", Thread.currentThread().getName());
            return true;
        }else{
            logger.error("登录用户超过上限 {}", MAX_PERMIT);
            return false;
        }
    }

    public static void main(String[] args) {
        AccessControlService accessControlService = new AccessControlService();
        for(int i =0; i < 30; ++i){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    accessControlService.login();
                }
            }).start();
        }
    }
}
