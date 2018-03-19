# common-demo
常用JDK API及工具使用demo
## JDK API
### jmx使用
### fork/join 使用
### nio-reactor 实现
### Thread 多种实现
1. 线程调度
 - FutureTask 执行单个任务 返回结果
 - Executors.newSingleThreadExecutor 线程池提交 单个任务 返回结果
 - invokeAll线程池执行多个任务 返回结果
 - invokeAny线程池执行多个任务取第一个执行完的任务结果 
 - CompletionService 线程池执行多个任务，并按执行完成先后顺序取结果
2. 线程之间的协作
 - 生产者消费者实现
        `Object.awat Object.notifyAll 实现`
        `ReentrantReadWriteLock 实现`
        `ReentrantLock实现`
        `Condition 实现`
 - 多线程同时开始实现   `common.demo.cooperation.fire.Main`
 - 多次集合点实现
        自实现 `common.demo.cooperation.barrier.Main` 实现集合点
        `CyclicBarrier` 实现集合点
 - 一次集合点实现
        自实现 `common.demo.cooperation.latch.MyLatch` 实现一次集合点
        `CountDownLatch` 实现一次集合点
 - 条件（信号量）限制
        `Semaphore` 模拟实现系统最多登录用户数
## 工具操作
### dubbox 使用
### kafka使用
### zookeeper 使用
1. *zookeeper* 基本操作
2. 基于 *zookeeper* 实现分布式事务锁 的使用示例
