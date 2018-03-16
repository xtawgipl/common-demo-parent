package common.demo;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.util.Set;

/**
 * 读取jmx数据
 *
 * @author zhangjj
 * @create 2017-12-22 11:07
 **/
public class Monitor {

    public static void main(String[] args) throws IOException {
        String host = "localhost";  // or some A.B.C.D
        int port = 1234;
        String url = "service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi";
        System.out.println(url);
        JMXServiceURL serviceUrl = new JMXServiceURL(url);
        JMXConnector jmxConnector = JMXConnectorFactory.connect(serviceUrl, null);
        try {
            MBeanServerConnection mbeanConn = jmxConnector.getMBeanServerConnection();
            // now query to get the beans or whatever
            Set<ObjectName> beanSet = mbeanConn.queryNames(null, null);
            for(ObjectName obj : beanSet){
                System.out.println("obj ----> " + obj.getKeyPropertyListString());
            }
        } finally {
            jmxConnector.close();
        }
    }
}
