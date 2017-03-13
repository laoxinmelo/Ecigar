package com.raul.bupt.request.impl;

import com.raul.bupt.request.DocumentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.annotation.Resource;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/3/13.
 */
public class DocumentGetterImpl implements DocumentGetter {


    private static final List<String> IP_LIST = getIpList();

    private static final String IP_PROXY_PATH = "proxy/ip.txt";

    private static final String SPLIT_MARK = ":";

    @Resource(name = "random")
    Random random;

    /**
     * 获取代理ip
     *
     * @return
     */
    private static List<String> getIpList() {

        List<String> ipList = new ArrayList<String>();
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


    private void setProxy() {

        int ipIndex = random.nextInt(IP_LIST.size());
        String ipSelect = IP_LIST.get(ipIndex);

        String ip = ipSelect.substring(0,ipSelect.lastIndexOf(SPLIT_MARK));
        String port = ipSelect.substring(ipSelect.lastIndexOf(SPLIT_MARK)+1);

        System.getProperties().setProperty("http.proxyHost", ip);
        System.getProperties().setProperty("http.proxyPort", port);

    }

    /**
     * 访问url，并将其解析为Document类
     *
     * @param url
     * @return
     */
    public Document getDocument(String url) {

        Document doc = null;

        try{

            setProxy();

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
