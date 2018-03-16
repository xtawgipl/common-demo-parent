package common.demo.run;

import common.demo.task.UrlTitleParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 *  原理是 在调用 FutureTask 中的  Callable 中的 call方法后 调用了 set方法，把线程的结果设置到一个变量中返回
 *  而在调用线程中调用get方法时，会判断线程的执行状态————如果是未完成状态则会调用Thread.yield()方法让出cpu，让所线程重新争用cpu
 *
 * @author zhangjj
 * @create 2017-12-21 11:38
 **/
public class SingleTaskDemo {

    private static Logger logger = LoggerFactory.getLogger(SingleTaskDemo.class);

    /**
     * 方法一
     * @param
     * @author zhangjj
     * @Date 2018/3/15 14:59
     * @return
     * @exception
     *
     */
    private static void invoke1(){
        FutureTask<String> futureTask = new FutureTask<>(new UrlTitleParser("http://www.baidu.com"));
        new Thread(futureTask).start();
        try {
            Thread.sleep(2000);
            logger.info("----");
            logger.info("----");
            logger.info("----");
            logger.info("----");
            Object result = futureTask.get();
            logger.info("result = " + result);
        } catch (InterruptedException e) {
            logger.error("", e);
        } catch (ExecutionException e) {
            logger.error("", e);
        }
    }

    /**
     * 方法二
     * @param
     * @author zhangjj
     * @Date 2018/3/15 15:00
     * @return
     * @exception
     *
     */
    private static void invoke2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(new UrlTitleParser("http://www.baidu.com"));
        //TODO something
        Thread.sleep(2000L);
        String result = future.get();
        logger.info("result = {}", result);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        invoke1();
        invoke2();

    }

}
