import org.apache.commons.math3.stat.descriptive.moment.Mean;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import java.util.List;
import java.util.Map;

/**
 * Class for the result presentation.
 */
public class ResultPresentation {

    private STGroup templates_h = new STGroupFile("./src/main/java/layout_pretty_print_h.stg");
    private STGroup templates_median_mean = new STGroupFile("./src/main/java/layout_pretty_print_median_mean.stg");

    /**
     * Method which represents the frequency distribution of parking cars.
     * @param h frequency of parking cars.
     */
    void logResult(Map<Integer, ? extends Number> h){
        h.forEach((k, v) -> {
            ST st_h = templates_h.getInstanceOf("h");
            st_h.add("number", k);
            st_h.add("incidence", v);
            System.out.println(st_h.render());
        });
    }

    /**
     * Method which represents the median number of free parking spaces that are lager than two meters, but still to small for the car.
     * @param iList Number of free parking spaces that are larger than two meters, but still too small for the car.
     */
    void logResult(List<Double> iList){

        ST st_median = templates_median_mean.getInstanceOf("median");
        ST st_mean = templates_median_mean.getInstanceOf("mean");

        double[] iListArray = iList.stream().mapToDouble(d -> d).toArray();

        st_median.add("median", new Median().evaluate(iListArray));
        st_mean.add("mean", new Mean().evaluate(iListArray));

        System.out.println("\n" + st_median.render() + "\n" + st_mean.render());
    }

    /**
     * Method which represents the median number of free parking spaces that are lager than two meters, but still to small for the car.
     * @param median The median of free parking spaces that are larger than two meters, but still too small for the car.
     * @param mean The mean of free parking spaces that are larger than two meters, but still too small for the car.
     */
    void logResult(double median, double mean){
        STGroup templates_median_mean = new STGroupFile("./src/main/java/layout_pretty_print_median_mean.stg");
        ST st_median = templates_median_mean.getInstanceOf("median");
        ST st_mean = templates_median_mean.getInstanceOf("mean");

        st_median.add("median", median);
        st_mean.add("mean", mean);

        System.out.println("\n" + st_median.render() + "\n" + st_mean.render());
    }
}
