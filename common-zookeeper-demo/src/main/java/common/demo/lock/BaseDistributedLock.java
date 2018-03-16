package common.demo.lock;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 实现了基于Zookeeper实现分布式锁的细节
 *
 * @author zhangjj
 * @create 2017-12-27 17:43
 **/
public class BaseDistributedLock {

    private Logger logger = LoggerFactory.getLogger(BaseDistributedLock.class);

    private final ZkClient client;

    private final String path;

    private final String basePath;

    private final String lockName;

    private static final Integer MAX_RETRY_COUNT = 10;

    public BaseDistributedLock(ZkClient client, String path, String lockName) {
        this.client = client;
        this.basePath = path;
        this.path = path.concat("/").concat(lockName);
        this.lockName = lockName;

        createPresistent(this.basePath);
    }

    private void deleteOurPath(String ourPath){
        logger.info("deleting : " + ourPath);
        client.delete(ourPath);
    }

    private void createPresistent(String path){
        boolean exists = client.exists(path);
        if(!exists){
            client.create(path, null, CreateMode.PERSISTENT);
        }
    }


    /**
     * 创建一个临时有序节点
     * @param
     * @author zhangjj
     * @Date
     * @return
     * @exception
     *
     */
    private String createLockNode(String path){
        String ephemeralSequential = client.createEphemeralSequential(path, null);
        logger.info(String.format("createEphemeralSequential : parent path = %s, ephemeralSequential = %s", path, ephemeralSequential));
        return ephemeralSequential;
    }

    private boolean waitToLock(Long startMillis, Long millisToWait, String ourPath){
        boolean haveTheLock = false;
        boolean doDelete = false;
        try {
            while (!haveTheLock){
                //获取lock节点下的所有节点
                List<String> children = getSortedChildren();
                String sequenceNodeName = ourPath.substring(basePath.length()+1);
                logger.info("children--> " + children.toString());
                //获取当前节点的在所有节点列表中的位置
                int ourIndex = children.indexOf(sequenceNodeName);
                logger.info("sequenceNodeName--> " + sequenceNodeName);
                //节点位置小于0,说明没有找到节点
                if (ourIndex < 0){
                    throw new ZkNoNodeException("节点没有找到: " + sequenceNodeName);
                }
                //节点位置大于0说明还有其他节点在当前的节点前面，就需要等待其他的节点都释放
                boolean isGetTheLock = ourIndex == 0;
                String pathToWatch = isGetTheLock ? null : children.get(ourIndex - 1);
                if(isGetTheLock){
                    haveTheLock = true;
                }else{
                    //获取当前节点的次小的节点，并监听节点的变化
                    String  previousSequencePath = basePath.concat( "/" ).concat(pathToWatch);
                    logger.info("previousSequencePath = " + previousSequencePath);
                    final CountDownLatch latch = new CountDownLatch(1);

                    //如果节点不存在会出现异常
                    try {
                        //新增子节点、减少子节点、删除节点
                        client.subscribeChildChanges(previousSequencePath, new IZkChildListener() {
                            @Override
                            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                                latch.countDown();
                            }
                        });

                        if(millisToWait != null){
                            millisToWait -= (System.currentTimeMillis() - startMillis);
                            startMillis = System.currentTimeMillis();
                            if(millisToWait < 0){
                                doDelete = true;    // timed out - delete our node
                                break;
                            }
                            latch.await(millisToWait, TimeUnit.MICROSECONDS);
                        }else{
                            latch.await();
                        }
                    } catch (InterruptedException e) {
                        logger.error("", e);
                    } finally {
                        client.unsubscribeAll();
                    }

                }
            }
        } catch (ZkNoNodeException e) {
            //发生异常需要删除节点
            doDelete = true;
            throw e;
        } finally {
            //如果需要删除节点
            if ( doDelete )
            {
                deleteOurPath(ourPath);
            }
        }
        return haveTheLock;
    }

    private List<String> getSortedChildren(){
        List<String> children = null;
        try {
            children = client.getChildren(basePath);
            Collections.sort
                    (
                            children,
                            new Comparator<String>()
                            {
                                public int compare(String lhs, String rhs)
                                {
                                    return getLockNodeNumber(lhs, lockName).compareTo(getLockNodeNumber(rhs, lockName));
                                }
                            }
                    );
            return children;
        } catch (Exception e) {
            logger.error("", e);
            client.createPersistent(basePath, true);
            return getSortedChildren();
        }
    }

    private String getLockNodeNumber(String str, String lockName){
        int index = str.lastIndexOf(lockName);
        if (index >= 0){
            index += lockName.length();
            return index <= str.length() ? str.substring(index) : "";
        }
        return str;
    }

    protected void releaseLock(String lockPath) throws Exception{
        deleteOurPath(lockPath);
    }

    /**
     * 尝试获取锁
     * @param
     * @author zhangjj
     * @Date
     * @return
     * @exception
     *
     */
    protected String attemptLock(long time, TimeUnit unit) throws Exception{
        final long startMillis = System.currentTimeMillis();
        final Long millisToWait = (unit != null) ? unit.toMillis(time) : null;
        String ourPath = null;
        boolean hasTheLock = false;
        boolean isDone = false;
        int retryCount = 0;

        //网络闪断需要重试一试
        while (!isDone){
            isDone = true;
            try{
                ourPath = createLockNode(path);
                hasTheLock = waitToLock(startMillis, millisToWait, ourPath);
            }
            catch (ZkNoNodeException e){
                if (retryCount++ < MAX_RETRY_COUNT){
                    isDone = false;
                }
                else{
                    throw e;
                }
            }
        }
        if (hasTheLock){
            return ourPath;
        }
        return null;
    }
}













