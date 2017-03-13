package com.raul.bupt.parse;

import com.raul.bupt.dataobject.Article;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface HtmlParser {

    /**
     * 从网页document所对应的element中获取所需要的内容
     *
     * @param url
     * @return
     */
    List<Article> getArticleList(String url);

    /**
     * 获取下一页的对应链接url
     *
     * @param doc
     * @return
     */
    String getNextPageUrk(Document doc);
}
