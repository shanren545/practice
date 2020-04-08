package txw.hadoop1.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int count = 0;

        // 每迭代一次，迭代器就会取一对key-value数据：
        // 取到的key数据重新设置给方法中的参数key
        // 取到的value数据则会设置给以下for循环中的临时变量value
        for (IntWritable value : values) {
            // 将本次迭代到的value值累加到count变量
            count += value.get();
        }

        // 将这一组数据的聚合结果通过context返回给reduce task
        context.write(key, new IntWritable(count));
    }
}
