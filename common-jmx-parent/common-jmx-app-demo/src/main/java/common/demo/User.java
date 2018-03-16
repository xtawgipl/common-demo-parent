package common.demo;

/**
 * @author zhangjj
 * @create 2017-12-21 16:15
 **/
public class User implements UserMBean {

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
