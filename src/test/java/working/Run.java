package working;

import com.raul.bupt.dataobject.Article;
import com.raul.bupt.parse.HtmlParser;
import com.raul.bupt.request.DocumentGetter;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Run {

    private static final String IP_PROXY_PATH = "proxy/ip.txt";

    /**
     * 获取代理ip
     *
     * @return
     */
    private static List<String> getIpList() {

        List<String> ipList = null;
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(IP_PROXY_PATH)));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String temp = bufferedReader.readLine();
            while(temp != null) {
                ipList.add(temp);
                temp = bufferedReader.readLine();
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            return ipList;
        }
    }

    public static void main(String[] args) {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        HtmlParser htmlParser = (HtmlParser) applicationContext.getBean("htmlparser");
        DocumentGetter documentGetter = (DocumentGetter) applicationContext.getBean("documentgetter");

        String url = "";
        String nextUrl = "http://chuansong.me/account/ecigbar";
        List<Article> articleList = new ArrayList<Article>();
        int pageNum = 1;

        while((!url.equals(nextUrl)) && nextUrl!=null) {

            url = nextUrl;

            documentGetter.setProxy();
            Document doc = documentGetter.getDocument(url);


            if(doc == null) {
                url = "";
                continue;
            }

//            List<Article> pageArticleList = htmlParser.getArticleList(doc);
//            for(Article article:pageArticleList) {
//                String articleLink = article.getLink();
//
//                Document articleDoc = documentGetter.getDocument(articleLink);
//                if(articleDoc == null) {
//                    continue;
//                }
//
//                String content = htmlParser.getArticleContent(articleDoc);
//                article.setContent(content);
//
//                System.out.println(article);
//                articleList.add(article);
//            }

            System.out.println("当前爬取第" + pageNum + "页：已完成页面 " + url + " 的爬取； 当前article的数量为：" + articleList.size() + "...");

            nextUrl = htmlParser.getNextPageUrl(doc);
            pageNum += 1;
        }




//        htmlParser.getArticleList(url);

    }
}
