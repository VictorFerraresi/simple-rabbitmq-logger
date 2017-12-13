import com.rabbitmq.client.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import java.io.IOException;

public class Receiver {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        String queueName = channel.queueDeclare().getQueue(); //Random Queue Name
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println("Listening for logging events...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");

                System.out.println("[LOG] " + msg);
            }
        };
        channel.basicConsume(queueName, true, consumer);
    }
}