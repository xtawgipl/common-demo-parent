package common.spring.xml.extend.bootstrap;

import com.alibaba.fastjson.JSON;
import common.spring.xml.extend.RpcServiceBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 启动
 *
 * @author zhangjj
 * @create 2018-07-29 15:19
 **/
@Slf4j
public class Application {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        RpcServiceBean rpcServiceBean = (RpcServiceBean) context.getBean("rpcService");
        log.info("rpcServiceBean : {}", JSON.toJSONString(rpcServiceBean));
    }
}
