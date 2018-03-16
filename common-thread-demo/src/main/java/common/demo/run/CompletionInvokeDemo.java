package common.demo.run;

import common.demo.task.UrlTitleParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 按执行完成顺序取结果
 *
 * @author zhangjj
 * @create 2018-03-15 15:06
 **/
public class CompletionInvokeDemo {
    private static Logger logger = LoggerFactory.getLogger(CompletionInvokeDemo.class);

    public static void main(String[] args) {
        List<String> urls = Arrays.asList(new String[]{"http://www.baidu.com", "http://www.qq.com"});
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletionService<String> completionService = new ExecutorCompletionService<>(executorService);

        try {
            for(String url : urls){
                completionService.submit(new UrlTitleParser(url));
            }
            for(int i = 0; i < urls.size(); ++i){
                Future<String> future = completionService.take();
                logger.info("result = {}", future.get());
            }
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            executorService.shutdown();
        }
    }
}
