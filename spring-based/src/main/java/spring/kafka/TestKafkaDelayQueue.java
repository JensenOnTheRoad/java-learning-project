package spring.kafka;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.KafkaContainer;

public class TestKafkaDelayQueue {

  private KafkaContainer kafkaContainer;
  private KafkaProducer<String, String> producer;
  private KafkaConsumer<String, String> consumer;

  @BeforeEach
  void setUp() {
    kafkaContainer = new KafkaContainer();
    kafkaContainer.start();

    Properties producerProperties = new Properties();
    producerProperties.setProperty(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
    producerProperties.setProperty(
        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    producerProperties.setProperty(
        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    producer = new KafkaProducer<>(producerProperties);

    Properties consumerProperties = new Properties();
    consumerProperties.setProperty(
        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaContainer.getBootstrapServers());
    consumerProperties.setProperty(
        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    consumerProperties.setProperty(
        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
    consumerProperties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, UUID.randomUUID().toString());
    consumer = new KafkaConsumer<>(consumerProperties);
  }

  @AfterEach
  public void stopKafka() {
    kafkaContainer.stop();
    producer.close();
    consumer.close();
  }

  @Test
  @DisplayName("kafka延迟队列测试")
  void test_delay_queue() {
    // 订阅消息主题
    String topic = "delay-queue";

    // 生产一条消息到消息队列
    ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, "Key", "Value");
    producer.send(producerRecord);

    // 订阅消息
    List<String> topics = Collections.singletonList(topic);
    consumer.subscribe(topics);

    // 轮询来自主题的消息
    ConsumerRecords<String, String> consumerRecords = consumer.poll(Duration.ofSeconds(1));
    for (ConsumerRecord<String, String> consumerRecord : consumerRecords) {
      Assertions.assertThat(consumerRecord.key()).isEqualTo("Key");
      Assertions.assertThat(consumerRecord.value()).isEqualTo("Value");
    }
  }
}
