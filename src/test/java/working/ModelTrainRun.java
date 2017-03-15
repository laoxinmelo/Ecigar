package working;

import com.raul.bupt.jgibblda.EstimatorNew;
import com.raul.bupt.jgibblda.RunEstimation;

/**
 * Created by Administrator on 2017/3/15.
 */
public class ModelTrainRun {


    //LDA模型构建对象
    private static final EstimatorNew estimator = new EstimatorNew();

    public static void main(String[] args) {

        for (int i = 3; i <= 10; i++) {
            RunEstimation.modelTraining(estimator, i);
            System.out.println("______________________________________");
        }

    }

}
