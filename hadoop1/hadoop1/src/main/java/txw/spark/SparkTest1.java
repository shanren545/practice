package txw.spark;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkTest1 {
    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf();
        conf.setAppName("Java Spark local");
        conf.setMaster("local");

        // 根据Spark配置生成Spark上下文
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // 读取本地的文本文件成内存中的RDD集合对象
        JavaRDD<String> lineRdd = jsc.textFile("d:/tmp.txt");

        // 切分每一行的字串为单词数组,并将字串数组中的单词字串释放到外层的JavaRDD集合中
        JavaRDD<String> flatMapRdd = lineRdd.flatMap(new FlatMapFunction<String, String>() {
            // @Override
            public Iterator<String> call(String line) throws Exception {
                String[] words = line.split(" ");
                List<String> list = Arrays.asList(words);
                Iterator<String> its = list.iterator();
                return its;
            }
        });


        // 为JavaRDD集合中的每一个单词进行计数,将其转换为元组
        //// 注意下面一定是调用的mapToPair函数，而不是map函数,否则返回的类型无法续调reduceByKey方法,因为只有元组列表才能实现分组统计
        JavaPairRDD<String, Integer> mapRdd = flatMapRdd.mapToPair(new PairFunction<String, String, Integer>() {
            // @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });

        // 根据元组中的第一个元素(Key)进行分组并统计单词出现的次数
        JavaPairRDD<String, Integer> reduceRdd = mapRdd.reduceByKey(new Function2<Integer, Integer, Integer>() {
            // @Override
            public Integer call(Integer pre, Integer next) throws Exception {
                return pre + next;
            }
        });

        // 将单词元组中的元素反序以方便后续排序
        JavaPairRDD<Integer, String> mapRdd02 = reduceRdd.mapToPair(new PairFunction<Tuple2<String, Integer>, Integer, String>() {
            // @Override
            public Tuple2<Integer, String> call(Tuple2<String, Integer> wordTuple) throws Exception {
                return new Tuple2<Integer, String>(wordTuple._2, wordTuple._1);
            }
        });

        // 将JavaRDD集合中的单词按出现次数进行将序排列
        JavaPairRDD<Integer, String> sortRdd = mapRdd02.sortByKey(false, 1);

        // 排序之后将元组中的顺序换回来
        JavaPairRDD<String, Integer> mapRdd03 = sortRdd.mapToPair(new PairFunction<Tuple2<Integer, String>, String, Integer>() {
            // @Override
            public Tuple2<String, Integer> call(Tuple2<Integer, String> wordTuple) throws Exception {
                return new Tuple2<String, Integer>(wordTuple._2, wordTuple._1);
            }
        });

        // 存储统计之后的结果到磁盘文件中去
        Files.deleteIfExists(FileSystems.getDefault().getPath("d:/tmp/sparkout"));
        mapRdd03.saveAsTextFile("d:/tmp/sparkout");

        // 关闭Spark上下文
        jsc.close();
    }
}
