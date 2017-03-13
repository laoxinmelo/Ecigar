package com.raul.bupt.request.impl;

import com.raul.bupt.request.DocumentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by Administrator on 2017/3/13.
 */
public class DocumentGetterImpl implements DocumentGetter {

    /**
     * 访问url，并将其解析为Document类
     *
     * @param url
     * @return
     */
    public Document getDocument(String url) {

        Document doc = null;

        try{
            doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31")
                    .timeout(20000)
                    //.cookies(cookies)
                    .get();


        } catch(Exception e) {
            e.printStackTrace();
        }finally {
            return doc;
        }
    }
}
