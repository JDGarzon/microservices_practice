package co.analisys.clase.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class RabbitMQConfig {


    public static final String ROUTING_KEY_HORARIO = "notificacion.horario";

    @Bean
    public Queue horarioQueue() {
        return new Queue("horario.queue", true);
    }

    @Bean
    public Queue notificacionQueue() {
        return new Queue("notificacion.queue", true);
    }

    @Bean
    public TopicExchange notificacionExchange() {
        return new TopicExchange("notificacion.exchange");
    }

    @Bean
    public Binding bindingHorario(Queue horarioQueue, TopicExchange notificacionExchange) {
        return
        BindingBuilder.bind(horarioQueue).to(notificacionExchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding binding(Queue notificacionQueue, TopicExchange notificacionExchange) {
        return
        BindingBuilder.bind(notificacionQueue).to(notificacionExchange).with("notificacion.routingkey");
    }
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }
}
   