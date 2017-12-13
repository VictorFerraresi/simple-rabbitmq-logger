# Simple RabbitMQ Logger
A simple RabbitMQ pair of Producers and Consumers to generate logging events using Java

## Compiling and Running

1. Have RabbitMQ and it's dependencies installed

 and running (I've used the 3.7.0 version for this)
2. Clone the repository

   > https://help.github.com/articles/cloning-a-repository/
3. Travel to the src path `(cd simple-rabbitmq-logger/main/src)`

4. Build the Emitter and Receiver .java files:
```javac -cp amqp-client-4.0.2.jar Emitter.java Receiver.java```
5. Run both the Receiver and the Emitter. If you choose to run the Emitter before, the Receiver will proccess all it's pending messages once it gets started too.
```java -cp .;amqp-client-4.0.2.jar;slf4j-api-1.7.21.jar;slf4j-simple-1.7.22.jar Receiver > filename.log```


```java -cp .:amqp-client-4.0.2.jar:slf4j-api-1.7.21.jar:slf4j-simple-1.7.22.jar Emitter```


6. If there's no inline arguments passed @ the Emmiter's run command, it will use the default message to log, otherwise, it will use your custom message.


7. Times are based on the emitter and not on the receiver, so, if somehow a message suffers from a delay before getting processed by the consumer, the registered log will have the date time of the emmiter event execution.
