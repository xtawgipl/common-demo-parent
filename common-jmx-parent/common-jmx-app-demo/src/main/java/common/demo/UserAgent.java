package common.demo;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;

/**
 * @author zhangjj
 * @create 2017-12-21 16:17
 **/
public class UserAgent {


    public static void main(String[] args) {
        int waitTime = 0;
        if(args[0] != null && Integer.valueOf(args[0]) >0){
            waitTime = Integer.valueOf(args[0]);
        }
        try {
            MBeanServer server = MBeanServerFactory.createMBeanServer("jmxrmi");
            ObjectName objectName = new ObjectName("jmxBean:name=user");

            JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:" + 1234 + "/jmxrmi");
            System.out.println("JMXServiceURL: " + url.toString());
            JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, server);
            jmxConnServer.start();

            final User user = new User();
            server.registerMBean(user, objectName);
            Thread t = new Thread(new Runnable() {
                public void run() {
                    while (!Thread.interrupted()){
                        user.setName("name + " + new Date());
                        System.out.println("---");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            return;
                        }
                    }
                }
            });
            t.start();
            t.join(waitTime);
            t.interrupt();
            System.out.println("main finish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
