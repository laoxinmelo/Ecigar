package working;

import com.raul.bupt.parse.HtmlParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Run {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        HtmlParser htmlParser = (HtmlParser) applicationContext.getBean("htmlparser");

        String url = "http://chuansong.me/account/ecigbar";

        htmlParser.getArticleList(url);

    }
}
