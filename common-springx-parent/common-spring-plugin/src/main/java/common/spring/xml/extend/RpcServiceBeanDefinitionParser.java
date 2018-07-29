package common.spring.xml.extend;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * bean定义解析器
 *
 * @author zhangjj
 * @create 2018-07-29 14:58
 **/
public class RpcServiceBeanDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        BeanDefinitionBuilder builder = BeanDefinitionBuilder.rootBeanDefinition(RpcServiceBean.class);
        String id = element.getAttribute("id");
        if(StringUtils.hasText(id)){
            builder.addPropertyValue("id", id);
        }
        String ref = element.getAttribute("ref");
        builder.addPropertyValue("ref", ref);

        String interfaceName = element.getAttribute("interfaceName");
        builder.addPropertyValue("interfaceName", interfaceName);

        String group = element.getAttribute("group");
        if (StringUtils.hasText(group)) {
            builder.addPropertyValue("group", group);
        }

        String registry = element.getAttribute("registry");
        if (StringUtils.hasText(registry)) {
            builder.addPropertyValue("registry", registry);
        }

        String version = element.getAttribute("version");
        if (StringUtils.hasText(version)) {
            builder.addPropertyValue("version", version);
        }

        String timeout = element.getAttribute("timeout");
        if (StringUtils.hasText(timeout)) {
            builder.addPropertyValue("timeout", Integer.parseInt(timeout));
        }

        String retries = element.getAttribute("retries");
        if (StringUtils.hasText(retries)) {
            builder.addPropertyValue("retries", Integer.parseInt(retries));
        }

        String async = element.getAttribute("async");
        if (StringUtils.hasText(async)) {
            builder.addPropertyValue("async", Boolean.valueOf(async));
        }

        return builder.getBeanDefinition();
    }
}
