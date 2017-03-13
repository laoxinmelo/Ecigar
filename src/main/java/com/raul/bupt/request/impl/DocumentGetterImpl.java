package com.raul.bupt.request.impl;

import com.raul.bupt.request.DocumentGetter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javax.annotation.Resource;
import java.io.*;
import org.jsoup.Connection;

import java.util.*;

/**
 * Created by Administrator on 2017/3/13.
 */
public class DocumentGetterImpl implements DocumentGetter {


    private static final List<String> IP_LIST = getIpList();

    private static final String IP_PROXY_PATH = "proxy/ip.txt";

    private static final String PORT = "16816";

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
                System.out.println(temp);
                temp = bufferedReader.readLine();
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            return ipList;
        }
    }

    /**
     * 设置代理ip
     *
     */
    public void setProxy() {

        int ipIndex = random.nextInt(IP_LIST.size());
        String ipSelect = IP_LIST.get(ipIndex);

        System.getProperties().setProperty("http.proxyHost", ipSelect);
        System.getProperties().setProperty("http.proxyPort", PORT);
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

            Connection conn = Jsoup.connect(url);

            //配置头文件
            Map<String, String> header = new HashMap<String, String>();
            header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:5.0) Gecko/20100101 Firefox/5.0");
//            header.put("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//            header.put("Accept-Language", "zh-cn,zh;q=0.5");
//            header.put("Connection", "keep-alive");
            header.put("Accept-Encoding","gzip");

            doc = conn.ignoreContentType(true).data(header).timeout(20000).get();

        } catch(Exception e) {
            e.printStackTrace();
        }finally {

            return doc;
        }
    }
}
