package common.spring.xml.extend;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * NamespaceHandler
 *
 * @author zhangjj
 * @create 2018-07-29 15:10
 **/
public class RpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("service", new RpcServiceBeanDefinitionParser());
    }
}
