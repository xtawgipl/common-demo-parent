package common.demo.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.concurrent.Callable;

/**
 * 解析器
 *
 * @author zhangjj
 * @create 2018-03-15 14:18
 **/
public class UrlTitleParser implements Callable<String>{
    private String url;

    public UrlTitleParser(String url){
        this.url = url;
    }

    @Override
    public String call() throws Exception {
        Document document = Jsoup.connect(this.url).get();
        String cssQuery = "head title";
        Elements select = document.select(cssQuery);
        if(select.size() > 0){
            return select.get(0).text();
        }
        return null;
    }
}
