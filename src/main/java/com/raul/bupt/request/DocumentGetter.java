package com.raul.bupt.request;

import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface DocumentGetter {

    /**
     * 访问url，并将其解析为Document类
     *
     * @param url
     * @return
     */
    Document getDocument(String url);

}
