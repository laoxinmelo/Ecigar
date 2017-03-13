package com.raul.bupt.request;

import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface DocumentGetter {

    Document getDocument(String url);

}
