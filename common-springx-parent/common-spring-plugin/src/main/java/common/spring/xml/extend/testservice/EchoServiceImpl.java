package common.spring.xml.extend.testservice;

import lombok.extern.slf4j.Slf4j;

/**
 * 实现
 *
 * @author zhangjj
 * @create 2018-07-29 15:15
 **/
@Slf4j
public class EchoServiceImpl implements IEchoService {

    @Override
    public void sayHello() {
        log.info("hello rpc");
    }
}
