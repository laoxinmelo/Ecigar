package working;

import com.raul.bupt.dataobject.Article;
import com.raul.bupt.parse.HtmlParser;
import com.raul.bupt.request.DocumentGetter;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Run {

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        HtmlParser htmlParser = (HtmlParser) applicationContext.getBean("htmlparser");
        DocumentGetter documentGetter = (DocumentGetter) applicationContext.getBean("documentgetter");
        Random random = (Random) applicationContext.getBean("random");

        String url = "";
        String nextUrl = "http://chuansong.me/account/ecigbar";
        List<Article> articleList = new ArrayList<Article>();
        int pageNum = 1;

        while((!url.equals(nextUrl)) && nextUrl!=null) {

            url = nextUrl;

            documentGetter.setProxy();

            Document doc = null;
            try {
                doc = documentGetter.getDocument(url);
            }catch (Exception e) {
                url = "";
                continue;
            }

            List<Article> pageArticleList = htmlParser.getArticleList(doc);
            for(int i=0;i<pageArticleList.size();i++) {

                Article article = pageArticleList.get(i);
                String articleLink = article.getLink();

                Document articleDoc = null;
                try{
                    articleDoc = documentGetter.getDocument(articleLink);
                }catch (Exception e) {

                    if(!e.getMessage().contains("404")) {
                        i = i - 1;
                    }
                    continue;
                }

                String content = htmlParser.getArticleContent(articleDoc);
                article.setContent(content);

                System.out.println(article);
                articleList.add(article);
            }

//            int sleepSecond = random.nextInt(maxSleepTime);
//            while(sleepSecond<minSleepTime) {
//                sleepSecond = random.nextInt(maxSleepTime);
//            }

            System.out.println("当前爬取第" + pageNum + "页：已完成页面 " + url + " 的爬取； 当前article的数量为：" + articleList.size() + "...");

//            try{
//                Thread.sleep(sleepSecond);
//            }catch (InterruptedException e){
//                e.printStackTrace();
//            }

            nextUrl = htmlParser.getNextPageUrl(doc);
            pageNum += 1;
        }

    }
}
