package txw.hadoop1.wordcount;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {

    @Override
    protected void map(
            LongWritable key,
            Text value,
            Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {

        // key在默认情况下是maptask所读取到的一行文本的起始偏移量
        // value在默认情况下是maptask所读取到的一行文本的字符内容

        // 将文本按空格符号切分成单词数组
        String[] words = value.toString().split(" ");
        // 遍历数组中的每一个单词，将每个单词变成 (单词，1)这样的kv对数据，通过context返回给maptask
        for (String word : words) {
            context.write(new Text(word), new IntWritable(1));
        }

    }

}
