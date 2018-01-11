package stock;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.text.DecimalFormat;
import java.util.Properties;
import java.util.Random;

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
        quotationInfo.setOpenPrivce(11.5f);
        quotationInfo.setLowPrice(10.5f);
        quotationInfo.setHighPrice(12.5f);
        quotationInfo.setStockCode(stockCode.toString());
        quotationInfo.setTradeTime(System.currentTimeMillis());
        quotationInfo.setStockName("stock-" + stockCode);
        return quotationInfo;
    }

    public static void main(String[] args) {
        ProducerRecord<String, String> record = null;
        StockQuotationInfo quotationInfo = null;
        try {
            int num = 0;
            for (int i = 0; i < MSG_SIZE; i++) {
                quotationInfo = createQuotationInfo();
                record = new ProducerRecord<String, String>(StockCommon.TOPIC, null, quotationInfo.getTradeTime(),
                        quotationInfo.getStockCode(), quotationInfo.toString());
                producer.send(record);
                if (num++ % 10 == 0) {
                    Thread.sleep(2000L);
                }
            }
        } catch (InterruptedException e){
            System.out.println(e.toString());
        } finally {
            producer.close();
        }
    }
}
