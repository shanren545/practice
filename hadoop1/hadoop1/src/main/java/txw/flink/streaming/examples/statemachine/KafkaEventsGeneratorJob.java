//package txw.flink.streaming.examples.statemachine;
//
//import org.apache.flink.api.java.utils.ParameterTool;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
//import txw.flink.streaming.examples.statemachine.generator.EventsGeneratorSource;
//import txw.flink.streaming.examples.statemachine.kafka.EventDeSerializer;
//
///**
// * Job to generate input events that are written to Kafka, for the {@link StateMachineExample} job.
// */
//public class KafkaEventsGeneratorJob {
//
//	public static void main(String[] args) throws Exception {
//
//		final ParameterTool params = ParameterTool.fromArgs(args);
//
//		double errorRate = params.getDouble("error-rate", 0.0);
//		int sleep = params.getInt("sleep", 1);
//
//		String kafkaTopic = params.get("kafka-topic");
//		String brokers = params.get("brokers", "localhost:9092");
//
//		System.out.printf("Generating events to Kafka with standalone source with error rate %f and sleep delay %s millis\n", errorRate, sleep);
//		System.out.println();
//
//		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//		env
//			.addSource(new EventsGeneratorSource(errorRate, sleep))
//			.addSink(new FlinkKafkaProducer<>(brokers, kafkaTopic, new EventDeSerializer()));
//
//		// trigger program execution
//		env.execute("State machine example Kafka events generator job");
//	}
//
//}
