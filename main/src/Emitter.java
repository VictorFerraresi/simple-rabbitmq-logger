import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Emitter {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        String msg = getMessage(argv);

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy @ HH:mm:ss");
        String sDate = sdf.format(date);

        String finalMsg = sDate + ": " + msg;

        channel.basicPublish(EXCHANGE_NAME, "", null, finalMsg.getBytes("UTF-8"));
        System.out.println("Emmited message: " + finalMsg);

        channel.close();
        conn.close();
    }

    private static String getMessage(String[] strings){
        if (strings.length < 1)
            return "Default MSG";
        return joinStrings(strings, " ");
    }

    private static String joinStrings(String[] strings, String delimiter) {
        int length = strings.length;
        if (length == 0) return "";
        StringBuilder words = new StringBuilder(strings[0]);
        for (int i = 1; i < length; i++) {
            words.append(delimiter).append(strings[i]);
        }
        return words.toString();
    }
}