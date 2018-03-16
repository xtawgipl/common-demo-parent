package common.demo.count;

import java.util.concurrent.RecursiveTask;

/**
 * 任务类
 *
 * @author zhangjj
 * @create 2017-12-20 14:56
 **/
public class CountTask extends RecursiveTask<Integer>{

    private static final int THRESHOLD = 2;

    private int start;

    private int end;

    public CountTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    protected Integer compute() {
        int sum = 0;
        boolean canCompute = (end - start) <= THRESHOLD;
        if(canCompute){
            for(int i = start; i < end; ++i){
                sum += i;
            }
        }else{
            //如果任务大于阀值，就分裂成两个子任务计算
            int mid = (start + end) / 2;
            CountTask leftCountTask = new CountTask(start, mid);
            CountTask rightCountTask = new CountTask(mid, end);

            //执行子任务
            leftCountTask.fork();
            rightCountTask.fork();

            //等待子任务执行完，并得到结果 
            Integer leftCount = leftCountTask.join();
            Integer rightCount = rightCountTask.join();

            sum = leftCount + rightCount;
        }
        return sum;
    }
}
