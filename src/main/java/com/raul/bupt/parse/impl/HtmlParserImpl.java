package com.raul.bupt.parse.impl;

import com.raul.bupt.dataobject.Article;
import com.raul.bupt.parse.HtmlParser;
import com.raul.bupt.request.DocumentGetter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/13.
 */
public class HtmlParserImpl implements HtmlParser {

    private static final String BASIC_URL = "http://chuansong.me";

    private static final String START_URL = "http://chuansong.me/account/ecigbar";

    /**
     * html的对应标签
     */
    private static final String DIV_TAG = "div";
    private static final String A_TAG = "a";
    private static final String SPAN_TAG = "span";

    private static final String CLASS_LABEL = "class";
    private static final String HREF_LABEL = "href";

    //各篇文章所对应div标签
    private static final String ARTICLE_MARK = "feed_item_question";
    //文章标题&链接所对应class标签
    private static final String TITLE_LINK_MARK = "question_link";
    //文章时间所对应class标签
    private static final String TIME_MARK = "timestamp";


    @Resource(name = "documentgetter")
    private DocumentGetter documentGetter;

    @Resource(name = "random")
    private Random random;





    /**
     * 从网页document所对应的element中获取所需要的内容
     *
     * @param url
     * @return
     */
    public List<Article> getArticleList(String url) {

        Document doc = documentGetter.getDocument(url);
        if(doc == null) {
            int sleepSecond = random.nextInt(100);
            try {
                Thread.sleep(sleepSecond);
            }catch (InterruptedException e) {e.printStackTrace();}

            return null;
        }

        List<Article> articleList = new ArrayList<Article>();
        Elements elements = doc.select(DIV_TAG);
        for(Element element:elements) {
            if(element.attr(CLASS_LABEL).equals(ARTICLE_MARK))
            {
                Elements titleLinkElements = element.select(A_TAG);
                Elements timeElements = element.select(SPAN_TAG);

                Article article = new Article();

                for(Element titleLinkElement:titleLinkElements) {
                    if(titleLinkElement.attr(CLASS_LABEL).equals(TITLE_LINK_MARK)) {

                        String title = titleLinkElement.text().trim();
                        String link = BASIC_URL + titleLinkElement.attr(HREF_LABEL).trim();

                        article.setTitle(title);
                        article.setLink(link);
                        break;
                    }
                }

                for(Element timeElement:timeElements) {
                    if(timeElement.attr(CLASS_LABEL).equals(TIME_MARK)) {
                        String time = timeElement.text().trim();

                        article.setDate(time);
                        break;
                    }
                }

                System.out.println(article);
            }
        }

        return null;
    }
}
