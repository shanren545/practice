/**
 * 
 */
package txw.hadoop1.spark;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.util.LongAccumulator;

import scala.Tuple2;

/**
 *
 * @author tanxianwen 2020年4月8日
 */
public class SparkTest {

    /**
     * @param args
     */
    public static void main(String[] args) {
        SparkSession spark =

                SparkSession.builder().appName("Java Spark SQL basic example").config("spark.some.config.option", "some-value").getOrCreate();
        // spark.createDataFrame(null, null);
        Dataset<String> ds = spark.read().textFile("/data/bin/spark/test.txt").cache();
        Function<String, String> f = s -> s.toString();
        ds.count();
        // ds.map(line -> line.split(" ").length).reduce((a, b) -> Math.max(a, b));
        // Boolean t = ds.filter(s->s.contains("d"));


        SparkConf conf = new SparkConf().setAppName("appname").setMaster("master");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data, 10);
        Integer i = distData.reduce((a, b) -> a + b);
        JavaRDD<String> distFile = sc.textFile("file", 3);
        distFile.cache();
        distFile.map(s -> s.length()).reduce((a, b) -> a + b);
        sc.wholeTextFiles("");
        // sc.newAPIHadoopFile(path, fClass, kClass, vClass, conf)


        JavaRDD<String> lines = sc.textFile("data.txt");
        JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2(new String(s), 1));
        JavaPairRDD<String, Integer> counts = pairs.reduceByKey((a, b) -> a + b);
        pairs.groupByKey();
        // pairs.aggregateByKey(zeroValue, seqFunc, combFunc)
        Broadcast<Integer> bv = sc.broadcast(3);
        bv.value();

        LongAccumulator dcount = sc.sc().longAccumulator("usercount");
        sc.parallelize(Arrays.asList(1, 2, 3, 4)).foreach(x -> dcount.add(x));
        dcount.add(2);
        dcount.value();
        IntStream is = null;
        is.flatMap(null);
        is.mapToObj(null);

        final int num = 100000;
        long sampleNum = IntStream.range(1, num).filter(count -> {
            double x = Math.random();
            double y = Math.random();
            return x * x + y * y < 1;
        }).count();
        System.out.println("Pi is roughly " + 4.0 * sampleNum / num);

        // Creates a DataFrame having a single column named "line"
        JavaRDD<String> textFile = sc.textFile("hdfs://...");
        JavaRDD<Row> rowRDD = textFile.map(RowFactory::create);
        List<StructField> fields = Arrays.asList(DataTypes.createStructField("line", DataTypes.StringType, true));
        StructType schema = DataTypes.createStructType(fields);
        // DataFrame df = sqlContext.createDataFrame(rowRDD, schema);

        // DataFrame errors = df.filter(col("line").like("%ERROR%"));
        // Counts all the errors
        // errors.count();
        // Counts errors mentioning MySQL
        // errors.filter(

        // col("line").like("%MySQL%")).count();
        // Fetches the MySQL errors as an array of strings
        // errors.filter(col("line").like("%MySQL%")).collect();
    }
}
