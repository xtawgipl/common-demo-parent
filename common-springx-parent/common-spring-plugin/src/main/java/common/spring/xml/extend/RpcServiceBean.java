package common.spring.xml.extend;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangjj
 * @create 2018-07-29 15:03
 **/
@Getter
@Setter
public class RpcServiceBean {

    private String id;

    private String ref;

    private String interfaceName;

    private String group;

    private String registry;

    private String version;

    private String timeout;

    private String retries;

    private boolean async;
}
