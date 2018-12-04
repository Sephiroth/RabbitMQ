package RabbitMQ;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 消息生成者
 */
public class Producer {
	public final static String QUEUE_NAME = "rabbitMQ.test";//rabbitMQ.test

	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		/*long time = 0;
		time = System.nanoTime();
		Hashtable<String,String> mapObj = new Hashtable<String,String>();
		for(int i=0;i<10;i++){
			mapObj.put(String.valueOf(i), "sender_lt");
		}
		CreateThread(mapObj);*/
		//System.out.println(String.valueOf(System.nanoTime()-time)+"----------------------------------------------");
		
		/*try {
			Socket socket = new Socket("127.0.0.1",5672);
	
			OutputStream os = socket.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.writeUTF("Legendary!");
            dos.close();
	        os.close();
	        socket.close();
		} catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
		
		/*SocketAddress address = new InetSocketAddress("127.0.0.1",5672);
		Socket s = new Socket();
		s.connect(address);
		String host = s.getLocalAddress().getHostAddress();
		s.close();*/
		
	}

	private static void CreateThread(final Hashtable<String,String> mapObj) {
		Runnable myRunnable = new Runnable() {
			public void run() {
				try {
					SendMsg(mapObj);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TimeoutException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Thread t = new Thread(myRunnable);
		t.start();
	}

	private static void SendMsg(Hashtable<String,String> mapObj) throws IOException,
			TimeoutException {
		// 创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置RabbitMQ相关信息
		factory.setHost("localhost");
		// factory.setUsername("lp");
		// factory.setPassword("");
		factory.setPort(5672);
		// 创建一个新的连接
		com.rabbitmq.client.Connection connection = factory.newConnection();
		// 创建一个通道
		Channel channel = connection.createChannel();
		// 声明一个队列 channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for(Iterator<String> iterator=mapObj.keySet().iterator();iterator.hasNext();){
			String key=iterator.next();
			String message = String.format("%s send message:%s", mapObj.get(key), key);
			// message = "aa";
			// 发送消息到队列中
			channel.basicPublish("", "otherTest", null, message.getBytes("UTF-8"));
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		}
		//System.out.println();
		// 关闭通道和连接
		channel.close();
		connection.close();
	}
	
	private static void SocketConnect(){
		
	}
}
