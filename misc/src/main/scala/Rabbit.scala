package org.sample

import akka.actor.{Props, Actor, ActorSystem}
import com.github.sstone.amqp.{ChannelOwner, ConnectionOwner, Amqp}
import com.rabbitmq.client.ConnectionFactory
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import com.github.sstone.amqp._
import com.github.sstone.amqp.Amqp._
import java.util.concurrent.TimeUnit
import scala.concurrent.duration._

object Producer extends App {
  def exec() = {
    implicit val system = ActorSystem("mySystem")
    println("Producer")
    // create an AMQP connection
    val connFactory = new ConnectionFactory()
    connFactory.setHost(sys.env("RABBIT_PORT_5672_TCP_ADDR"));
    val conn = system.actorOf(ConnectionOwner.props(connFactory, 1 second))
    val producer = ConnectionOwner.createChildActor(conn, ChannelOwner.props())
    
    // wait till everyone is actually connected to the broker
    waitForConnection(system, conn, producer).await(5, TimeUnit.SECONDS)
    
    // send a message
    producer ! Publish("amq.direct", "my_key", "yo!!".getBytes, properties = None, mandatory = true, immediate = false)
    
    // give it some time before shutting everything down
    Thread.sleep(500)
    system.shutdown()
  }
  def execJava() = {
    var factory = new ConnectionFactory();
    factory.setHost(sys.env("RABBIT_PORT_5672_TCP_ADDR"));        
    var connection = factory.newConnection();
    var channel = connection.createChannel();
    channel.queueDeclare("hello", false, false, false, null);
    val message = "Hello World!";
    channel.basicPublish("", "hello", null, message.getBytes());
    channel.close();
    connection.close();
  }
}

object Consumer1 extends App {
  def exec() = {
    implicit val system = ActorSystem("mySystem")
    println("Consumer1")

    // create an AMQP connection
    val connFactory = new ConnectionFactory()
    connFactory.setHost(sys.env("RABBIT_PORT_5672_TCP_ADDR"));
    val conn = system.actorOf(ConnectionOwner.props(connFactory, 1 second))

    // create an actor that will receive AMQP deliveries
    val listener = system.actorOf(Props(new Actor {
      def receive = {
        case Delivery(consumerTag, envelope, properties, body) => {
          println("got a message: " + new String(body))
          sender ! Ack(envelope.getDeliveryTag)
        }
      }
    }))

    // create a consumer that will route incoming AMQP messages to our listener
    // it starts with an empty list of queues to consume from
    val consumer = ConnectionOwner.createChildActor(conn, Consumer.props(listener, channelParams = None, autoack = false))

    // wait till everyone is actually connected to the broker
    Amqp.waitForConnection(system, consumer).await()

    // create a queue, bind it to a routing key and consume from it
    // here we don't wrap our requests inside a Record message, so they won't replayed when if the connection to
    // the broker is lost: queue and binding will be gone

    // create a queue
    val queueParams = QueueParameters("my_queue", passive = false, durable = false, exclusive = false, autodelete = true)
    consumer ! DeclareQueue(queueParams)
    // bind it
    consumer ! QueueBind(queue = "my_queue", exchange = "amq.direct", routing_key = "my_key")
    // tell our consumer to consume from it
    consumer ! AddQueue(QueueParameters(name = "my_queue", passive = false))

    // run the Producer sample now and see what happens
    println("press enter...")

    System.in.read()
    Producer.exec()
    system.shutdown()
  }
  def execJava() = {
    // Rabbit MQとのチャンネルを確立
    val factory = new ConnectionFactory();
    factory.setHost(sys.env("RABBIT_PORT_5672_TCP_ADDR"));
    val connection = factory.newConnection();
    val channel = connection.createChannel();
    channel.queueDeclare("hello", false, false, false, null);
    println(" [*] Waiting for messages. To exit press CTRL+C");
    val consumer = new QueueingConsumer(channel);
    channel.basicConsume("hello", true, consumer);
    while (true) {
        val delivery = consumer.nextDelivery();
        val message = new String(delivery.getBody());
        println(" [x] Received '" + message + "'");
    }
  }
}
