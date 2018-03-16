package common.demo.cooperation.producerConsumer2;

/**
 * 商品
 *
 * @author zhangjj
 * @create 2018-03-15 15:58
 **/
public class GoodBean {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "GoodBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
