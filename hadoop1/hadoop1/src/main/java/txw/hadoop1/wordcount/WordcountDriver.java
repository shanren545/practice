package txw.hadoop1.wordcount;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordcountDriver {

    public static void main(String[] args) throws Exception {
        // 需要将大量的跟jar包程序运行相关的参数进行描述
        Configuration conf = new Configuration();

        // 本地调试时
       // conf.set("fs.default.name", "file:///");
       // conf.set("mapred.job.tracker", "local");


        Job job = Job.getInstance(conf);

        // job.setJar("/root/wc.jar");
        job.setJarByClass(WordcountDriver.class);

        // 指定本次job所要用的mapper类和reducer类
        job.setMapperClass(WordCountMapper.class);
        job.setCombinerClass(WordCountReducer.class);
        job.setReducerClass(WordCountReducer.class);

        // 指定本次job的mapper类和reducer类输出结果的数据类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 指定本次job读取源数据时所需要用的输入组件：源文件在hdfs的文本文件中，用TextInputFormat
        job.setInputFormatClass(TextInputFormat.class);
        // 指定本次job输出数据时所需要用的输出组件：输出到hdfs文本文件中，用TextOutputFormat
        job.setOutputFormatClass(TextOutputFormat.class);

        // 指定reduce task运行实例的个数
        job.setNumReduceTasks(3);

        // 指定源数据文件所在的路径
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        // FileInputFormat.setInputPaths(job, new Path("/user/txw/input"));
        // 指定输出结果数据文件所在的路径
        // FileOutputFormat.setOutputPath(job, new Path("/user/txw/output"));
        final Path outputDir = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, outputDir);

        FileSystem fs = FileSystem.get(conf);
        if (fs.exists(outputDir)) {
            fs.delete(outputDir, true);
        }

        // 核心代码： 提交jar包给yarn
        // job.submit(); // 提交完任务，客户端就退出了
        // 提交完任务，客户端打印集群中的运行进度信息，并等待最终运行状态
        boolean res = job.waitForCompletion(true);
        System.exit(res ? 0 : 1);

    }

}
