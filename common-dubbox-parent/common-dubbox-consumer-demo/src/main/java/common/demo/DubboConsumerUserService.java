package common.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author zhangjj
 * @create 2018-01-03 16:36
 **/
public class DubboConsumerUserService {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-dubbo-consumer.xml");
        context.start();
        IUserService userService = (IUserService)context.getBean("userService");
        System.out.println(userService.login());
    }

}
