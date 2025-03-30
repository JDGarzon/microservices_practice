package co.analisys.gimnasio.config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.analisys.gimnasio.service.NotificacionService;

@Configuration
public class RabbitMQConfig {

    @Autowired
    NotificacionService notificacionService;

    @Bean
    public Queue horarioQueue() {
        return new Queue("horario.queue", true);
    }
    
    @Bean
    public Queue notificacionQueue() {
        return new Queue("notificacion.queue", true);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter());
        return factory;
    }

    @Bean
    public Queue pagosDlq() {
        return new Queue("pagos-dlq", true);
    }

    @Bean
    public DirectExchange pagosExchange() {
        return new DirectExchange("pagos-exchange");
    }

    @Bean
    public Queue pagosQueue() {
        return new Queue("pagos-queue", true);
    }

    
    @Bean
    public Binding bindingPagosQueue() {
        return BindingBuilder.bind(pagosQueue()).to(pagosExchange()).with("pagos");
    }

    @Bean
    public Binding bindingPagosDlq() {
        return BindingBuilder.bind(pagosDlq()).to(pagosExchange()).with("pagos-dlq");
    }
    
}
