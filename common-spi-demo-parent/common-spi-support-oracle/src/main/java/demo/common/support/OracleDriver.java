package demo.common.support;

import demo.common.api.Driver;

/**
 * @author zhangjj
 * @create 2018-08-28 15:15
 **/
public class OracleDriver implements Driver {
    @Override
    public boolean connect(String ip, int port) {
        System.out.println(String.format("oracle driver spi impl, %s, %s", ip, port));
        return true;
    }
}
