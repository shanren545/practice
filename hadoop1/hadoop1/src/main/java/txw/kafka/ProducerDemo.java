package txw.kafka;

import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

public class ProducerDemo {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "in.shanren.ink:9093,in.shanren.ink:9094,in.shanren.ink:9095");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 5);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 1000; i++)
            producer.send(new ProducerRecord<String, String>("txw_topic1", Integer.toString(i), Integer.toString(i)), new Callback() {

                @Override
                public void onCompletion(RecordMetadata metadata, Exception exception) {
                    if (null != exception) {
                        exception.printStackTrace();
                    } else {
                        System.out.println("offset:" + metadata.offset() + ",ts:" + metadata.timestamp() + metadata.toString());
                    }

                }
            });

        Thread.sleep(10000L);
        producer.close();

    }

}
