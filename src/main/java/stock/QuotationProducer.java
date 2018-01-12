package stock;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by maom3 on 2018/1/11.
 */
public class QuotationProducer {
    // set producer message size
    private static final int MSG_SIZE = 100;
    private static KafkaProducer<String, String> producer = null;

    static {
        // create Properties
        Properties config = initConfig();
        // create KafkaProducer
        producer = new KafkaProducer<String, String>(config);
    }

    private static Properties initConfig() {
        Properties properties = new Properties();
        // set Kafka broker list
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, StockCommon.BROKER_LIST);
        // set serializer class
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        return properties;
    }

    private static StockQuotationInfo createQuotationInfo() {
        StockQuotationInfo quotationInfo = new StockQuotationInfo();
        Random r = new Random();
        Integer stockCode = 600100 + r.nextInt(10);
        float random = (float) Math.random();
        if (random / 2 < 0.5) {
            random = -random;
        }
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        quotationInfo.setCurrentPrice(Float.valueOf(decimalFormat.format(11 + random)));
        quotationInfo.setPreClosePrice(11.08f);
        quotationInfo.setOpenPrice(11.5f);
        quotationInfo.setLowPrice(10.5f);
        quotationInfo.setHighPrice(12.5f);
        quotationInfo.setStockCode(stockCode.toString());
        quotationInfo.setTradeTime(System.currentTimeMillis());
        quotationInfo.setStockName("stock-" + stockCode);
        return quotationInfo;
    }

    public static void main(String[] args) {
        // if async is true, it will send message Asynchronously
        boolean async = false;
        ProducerRecord<String, String> record = null;
        StockQuotationInfo quotationInfo = null;
        try {
            int num = 0;
            for (int i = 0; i < MSG_SIZE; i++) {
                quotationInfo = createQuotationInfo();
                record = new ProducerRecord<String, String>(StockCommon.TOPIC, null, quotationInfo.getTradeTime(),
                        quotationInfo.getStockCode(), quotationInfo.toString());
                if (async) {
                    producer.send(record, new Callback() {
                        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                            System.out.println("#offset:"+ recordMetadata.offset());
                        }
                    });
                } else {
                    // Future.get() will block if no result returned
                    producer.send(record).get();
                }
                if (num++ % 10 == 0) {
                    Thread.sleep(2000L);
                }
            }
        } catch (InterruptedException e){
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}
