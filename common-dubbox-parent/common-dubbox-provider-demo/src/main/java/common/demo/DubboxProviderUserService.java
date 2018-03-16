package common.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 发布服务
 * @author zhangjj
 * @create 2018-01-03 16:06
 **/
public class DubboxProviderUserService {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-dubbo-provider.xml");
        context.start();
        Thread.sleep(2000000l);
    }
}
