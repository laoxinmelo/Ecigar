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

    private static final int maxSleepTime = 5;

    private static final int minSleepTime = 3;


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
//                System.out.println(temp);
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
    public Document getDocument(String url) throws Exception {

        try{

            Connection conn = Jsoup.connect(url);

            //配置头文件
            Map<String, String> header = new HashMap<String, String>();

            header.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            header.put("Accept-Language", "zh-CN,zh;q=0.8");
            header.put("Connection", "keep-alive");
            header.put("Accept-Encoding","gzip, deflate, sdch");
            header.put("Host","chuansong.me");
            header.put("Cookie","__utmt=1; Hm_lvt_a60dba8cb25e746640878eb1b20f67eb=1489419029,1489454961,1489454992,1489456197; __utma=240057436.306028526.1488943240.1489419029.1489454962.6; __utmb=240057436.29.10.1489454962; __utmc=240057436; __utmz=240057436.1489391585.3.3.utmcsr=baidu|utmccn=(organic)|utmcmd=organic|utmctr=%E4%BC%A0%E9%80%81%E9%97%A8");

            Document doc = conn.ignoreContentType(true).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36").data(header).timeout(20000).get();

            int sleepSecond = random.nextInt(maxSleepTime);
            while(sleepSecond<minSleepTime) {
                sleepSecond = random.nextInt(maxSleepTime);
            }

            try{
                Thread.sleep(sleepSecond*1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            return doc;

        } catch(Exception e) {
            throw e;
        }
    }
}
