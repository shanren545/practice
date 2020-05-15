package txw.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

public class AutoCommitConsumerDemo {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "in.shanren.ink:9093,in.shanren.ink:9094,in.shanren.ink:9095");
        props.put("group.id", "test-group-1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        @SuppressWarnings("resource")
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("txw_topic1"));
        
        List<TopicPartition> partitions = new ArrayList<>(3);
        partitions.add(new TopicPartition("txw_topic1", 0));
        partitions.add(new TopicPartition("txw_topic1", 1));
        partitions.add(new TopicPartition("txw_topic1", 2));
        consumer.beginningOffsets(partitions).forEach((part, v) -> System.out.println(part.partition() +":" + v));;
        
        consumer.poll(Duration.ofSeconds(10));
        consumer.seek(new TopicPartition("txw_topic1", 2), 0);
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            @SuppressWarnings("unused")
            Set<TopicPartition> parts = consumer.assignment();
        }
    }

}
