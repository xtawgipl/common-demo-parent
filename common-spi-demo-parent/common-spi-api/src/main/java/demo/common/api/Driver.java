package demo.common.api;

/**
 * spi驱动接口
 *
 * @author zhangjj
 * @create 2018-08-28 14:11
 **/
public interface Driver {

    boolean connect(String ip, int port);

}
