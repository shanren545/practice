package txw.kafka;

import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;

public class AdminTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "in.shanren.ink:9093,in.shanren.ink:9094,in.shanren.ink:9095");
        AdminClient client = KafkaAdminClient.create(props);
        Set<String> topicNames = client.listTopics().names().get();
        System.out.println(topicNames);
    }

}
