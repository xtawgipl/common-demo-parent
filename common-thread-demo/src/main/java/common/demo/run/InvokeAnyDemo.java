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
 * 执行任何一个，取最早执行完的任务结果
 *
 * @author zhangjj
 * @create 2018-03-15 15:00
 **/
public class InvokeAnyDemo {

    private static Logger logger = LoggerFactory.getLogger(InvokeAnyDemo.class);

    public static void main(String[] args) {
        List<String> urls = Arrays.asList(new String[]{"http://www.baidu.com", "http://www.qq.com"});
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> tasks = new ArrayList<>(urls.size());
        for(String url : urls){
            tasks.add(new UrlTitleParser(url));
        }

        try {
            String result = executorService.invokeAny(tasks);
            logger.info("result = {}", result);
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            executorService.shutdown();
        }
    }
}
