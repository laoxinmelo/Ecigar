package working;

import com.raul.bupt.segment.WordParticiple;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;

/**
 * ·Ö´ÊÖ÷³ÌÐò
 *
 * Created by Administrator on 2017/3/14.
 */
public class SegmentRun {

    private static final String articleSavePath = "article/afterProcess.txt";
    private static final int contentIndex = 2;

    private static final String leftMark = "¡¾";
    private static final String rightMark = "¡¿";


    public static void main(String[] args) throws IOException{

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        WordParticiple wordParticiple = (WordParticiple) applicationContext.getBean("wordparticiple");

        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(new File(articleSavePath)));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String temp = bufferedReader.readLine();

        int sentenceNum = 0;
        BufferedWriter bw = new BufferedWriter(new FileWriter("article/articlesTraining.txt"));
        while(temp != null) {
            String[] articleArray = temp.split("\t");
            String content = articleArray[contentIndex];

            if (content.contains(leftMark) && content.contains(rightMark)) {
                String str = content.substring(content.indexOf(leftMark), content.indexOf(rightMark)+1);


                content = content.replaceAll(str, "");
            }

            String segmentSentence = wordParticiple.wordSegmentWithoutStopWord(content);
            sentenceNum += 1;
            System.out.println(sentenceNum + "  " + segmentSentence);
            bw.write(segmentSentence + "\r\n");

            temp = bufferedReader.readLine();
        }

        bufferedReader.close();
        bw.flush();  bw.close();
    }
}
