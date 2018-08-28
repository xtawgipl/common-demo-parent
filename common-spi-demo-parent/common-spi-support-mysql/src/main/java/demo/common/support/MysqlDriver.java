package demo.common.support;

import demo.common.api.Driver;

/**
 * @author zhangjj
 * @create 2018-08-28 14:33
 **/
public class MysqlDriver implements Driver {
    @Override
    public boolean connect(String ip, int port) {
        System.out.println(String.format("mysql driver spi impl, %s, %s", ip, port));
        return true;
    }
}
