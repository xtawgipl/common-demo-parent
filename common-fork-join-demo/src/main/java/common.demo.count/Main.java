package common.demo.count;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ForkJoinPool;

/**
 * 执行任务类
 *
 * @author zhangjj
 * @create 2017-12-20 15:09
 **/
public class Main {

    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        CountTask countTask = new CountTask(1, 4);
        Integer count = forkJoinPool.invoke(countTask);
        logger.info("count = " + count);

        if(countTask.isCompletedAbnormally()){
            logger.error("", countTask.getException());
        }
    }

}
