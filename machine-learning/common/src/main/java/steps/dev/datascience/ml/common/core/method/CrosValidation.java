/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import java.util.stream.LongStream;
import steps.dev.datascience.ml.common.core.data.DataSamples;
import steps.dev.datascience.ml.common.core.data.DataService;
import steps.dev.datascience.ml.common.core.data.DataType;
import steps.dev.datascience.ml.common.core.parameters.CVParameters;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;

/**
 *
 * @author stepin
 * Functional that assesses the quality of a method 
 * 
 */
public interface CrosValidation {

    //Assesses the quality of the method
    //for N random divisions of the source data (division to traning and validating samples)
    //train algorithm against training sample using given method and given ccvParameter 
    //and for the trained algorithm calculate emirical risk against validating sample
    //then get CV assessment as average empirical risk
    public static <T extends DataType, P extends CVParameters, Theta extends MethodParameters, A extends Algorithm<Theta>> double apply(
            final Method<T, Theta, A> method,
            final DataService<T> dataService,
            final LossFunction<T> lossFunction,
            final P ccvParameter,
            final long n,
            final boolean parallelCV,
            final boolean parallelEmpiricalRisk) {

        LongStream stream;
        if (parallelCV) {
            stream = LongStream.range(0, n).parallel();
        } else {
            stream = LongStream.range(0, n);
        }

        return stream
                
                // for each n:
                //devide randomly the data to training validating samle and validating samle
                .mapToObj(i -> DataService.getRandomDivision(dataService))

                //train algorithm against training sample using given method and given ccvParameter 
                //and for the trained algorithm calculate emirical risk against validating sample
                .mapToDouble((DataSamples<T> dataSamples) -> EmpiricalRisk.calculate(
                        dataSamples.getValidatingSample(),
                        method.train(dataSamples.getTrainingSample(), ccvParameter),
                        method,                                                
                        lossFunction,
                        parallelEmpiricalRisk))
                
                //get CV assessment as average empirical risk
                .average()
                .getAsDouble();
    }

}
