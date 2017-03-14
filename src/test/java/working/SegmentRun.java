package working;

import com.raul.bupt.segment.WordParticiple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;

/**
 * 分词主程序
 *
 * Created by Administrator on 2017/3/14.
 */
public class SegmentRun {

    private static final String articleSavePath = "article/afterProcess.txt";
    private static final int contentIndex = 2;

    public static void main(String[] args) throws IOException{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        WordParticiple wordParticiple = (WordParticiple) applicationContext.getBean("wordparticiple");

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(articleSavePath)));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String temp = bufferedReader.readLine();


        BufferedWriter bw = new BufferedWriter(new FileWriter("article/articlesTraining.txt"));
        while(temp != null) {
            String[] articleArray = temp.split("\t");
            String content = articleArray[contentIndex];

//            wordParticiple.findNewWords(content);
//            wordParticiple.findKeyWords(content);

            String segmentSentence = wordParticiple.wordSegmentWithoutStopWord(content);
            System.out.println(segmentSentence);
            bw.write(segmentSentence + "\r\n");

            temp = bufferedReader.readLine();
        }

        bufferedReader.close();
        bw.flush();  bw.close();
    }
}
