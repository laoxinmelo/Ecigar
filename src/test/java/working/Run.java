package working;

import com.raul.bupt.dataobject.Article;
import com.raul.bupt.parse.HtmlParser;
import com.raul.bupt.request.DocumentGetter;
import org.jsoup.nodes.Document;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */
public class Run {

    public static void main(String[] args) throws IOException{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        HtmlParser htmlParser = (HtmlParser) applicationContext.getBean("htmlparser");
        DocumentGetter documentGetter = (DocumentGetter) applicationContext.getBean("documentgetter");

        String url = "";
        String nextUrl = "http://chuansong.me/account/ecigbar";
        List<Article> articleList = new ArrayList<Article>();
        int pageNum = 1;

        BufferedWriter bw = new BufferedWriter(new FileWriter("article/articles.txt"));

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

                    System.out.println(e.getMessage() + "---" + articleLink);

                    //404 error
                    if(!e.getMessage().contains("404 error")) {
                        i = i - 1;
                    }else {
                        e.printStackTrace();
                    }
                    continue;
                }

                String content = htmlParser.getArticleContent(articleDoc).replaceAll("\t","");
                article.setContent(content);

                System.out.println(article);
                bw.write(article.toString() + "\r\n");
                articleList.add(article);
            }

            System.out.println("当前爬取第" + pageNum + "页：已完成页面 " + url + " 的爬取； 当前article的数量为：" + articleList.size() + "...");

            nextUrl = htmlParser.getNextPageUrl(doc);
            pageNum += 1;
        }

        bw.flush();  bw.close();
    }
}
