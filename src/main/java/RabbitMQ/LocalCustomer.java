package RabbitMQ;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;

public class LocalCustomer {
	private final static String QUEUE_NAME = "rabbitMQ.test";//rabbitMQ.test

	public static void main(String[] args) throws Throwable {
		// 创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置RabbitMQ地址
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		/*factory.setUsername("guest");
        factory.setPassword("guest");*/
		// 创建一个新的连接
		com.rabbitmq.client.Connection connection = factory.newConnection();
		// 创建一个通道
		com.rabbitmq.client.Channel channel = connection.createChannel();
		// 声明要关注的队列
		channel.queueDeclare(QUEUE_NAME, false, false, true, null);
		//channel.queueDeclare();
		System.out.println("LocalCustomer Waiting Received messages");
		// DefaultConsumer类实现了Consumer接口，通过传入一个频道，
		// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
		Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag,
					com.rabbitmq.client.Envelope envelope,
					AMQP.BasicProperties properties, byte[] body)
					throws IOException {
				String message = new String(body, "UTF-8");
				System.out.println("LocalCustomer Received '" + message + "'");
			}
		};
		// 自动回复队列应答 -- RabbitMQ中的消息确认机制
		channel.basicConsume(QUEUE_NAME, true, consumer);
		
		CreateThread();
	}
	
	private static void CreateThread() throws Throwable, TimeoutException{
		// 创建连接工厂
				ConnectionFactory factory = new ConnectionFactory();
				// 设置RabbitMQ地址
				factory.setHost("127.0.0.1");
				factory.setPort(5672);
				/*factory.setUsername("guest");
		        factory.setPassword("guest");*/
				// 创建一个新的连接
				com.rabbitmq.client.Connection connection = factory.newConnection();
				// 创建一个通道
				com.rabbitmq.client.Channel channel = connection.createChannel();
				// 声明要关注的队列
				channel.queueDeclare("otherTest", false, false, true, null);
				//channel.queueDeclare();
				System.out.println("otherTest Waiting Received messages");
				// DefaultConsumer类实现了Consumer接口，通过传入一个频道，
				// 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
				Consumer consumer = new DefaultConsumer(channel) {
					@Override
					public void handleDelivery(String consumerTag,
							com.rabbitmq.client.Envelope envelope,
							AMQP.BasicProperties properties, byte[] body)
							throws IOException {
						String message = new String(body, "UTF-8");
						System.out.println("otherTest Received '" + message + "'");
					}
				};
				// 自动回复队列应答 -- RabbitMQ中的消息确认机制
				channel.basicConsume("otherTest", true, consumer);
	}

}
