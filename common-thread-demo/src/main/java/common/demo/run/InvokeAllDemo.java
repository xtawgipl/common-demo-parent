package common.demo.run;

import common.demo.task.UrlTitleParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 线程池执行多个任务
 *
 * @author zhangjj
 * @create 2018-03-15 14:33
 **/
public class InvokeAllDemo {
    private static Logger logger = LoggerFactory.getLogger(InvokeAllDemo.class);

    public static void main(String[] args) {
        List<String> urls = Arrays.asList(new String[]{"http://www.baidu.com", "http://www.qq.com"});
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> tasks = new ArrayList<>(urls.size());
        for(String url : urls){
            tasks.add(new UrlTitleParser(url));
        }

        try {
            List<Future<String>> futures = executorService.invokeAll(tasks);
            for(Future<String> future : futures){
                String result = future.get();
                logger.info("result = {}", result);
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            executorService.shutdown();
        }
    }
}
