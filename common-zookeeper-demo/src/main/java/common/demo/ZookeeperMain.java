package common.demo;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;

/** crud 操作
 * @author zhangjj
 * @create 2017-12-26 22:36
 **/
public class ZookeeperMain {

    private static String connString = "192.168.1.117:2181,192.168.1.117:2182,192.168.1.117:2183";

    public static void main(String[] args) {
        ZkClient zc = new ZkClient(connString,10000,10000,new SerializableSerializer());
        UserBean user = new UserBean();
        user.setId(1);
        user.setName("user123");
        boolean exists = zc.exists("/zkClient_01");
        System.out.println(exists);
        if(!exists){
            String path = zc.create("/zkClient_01",user, CreateMode.PERSISTENT);
            System.out.println("path = " + path);
        }else{
//            zc.delete("/zkClient_01");
            UserBean user1 = zc.readData("/zkClient_01");
            System.out.println("user1 = " + user1);

            user1.setName("user1231111111111");
            zc.writeData("/zkClient_01", user1);
            user1 = zc.readData("/zkClient_01");
            System.out.println("user1 ==== " + user1);
        }
    }
}
