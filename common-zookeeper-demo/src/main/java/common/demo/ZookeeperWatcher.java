package common.demo;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

import java.util.List;

/**
 *  watcher 机制
 * @author zhangjj
 * @create 2017-12-27 10:54
 **/
public class ZookeeperWatcher {
    private static String connString = "192.168.1.117:2181,192.168.1.117:2182,192.168.1.117:2183";
    private static String node = "/testNode";
    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient(connString,10000,10000,new SerializableSerializer());
        System.out.println("conneted ok!");

        //"/testUserNode" 监听的节点，可以是现在存在的也可以是不存在的
        zkClient.subscribeChildChanges(node, new IZkChildListener() {
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println("parentPath = " + parentPath);
                System.out.println("currentChilds = " + currentChilds);
            }
        });

        zkClient.subscribeDataChanges(node, new IZkDataListener() {
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println(dataPath+":"+data.toString());
            }

            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println(dataPath);
            }
        });
        boolean exists = zkClient.exists(node);
        System.out.println(exists);
        if(!exists){
            String path = zkClient.create(node,"", CreateMode.PERSISTENT);
            System.out.println("path = " + path);
        }
        zkClient.writeData(node, "2222");
        zkClient.deleteRecursive(node);
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
