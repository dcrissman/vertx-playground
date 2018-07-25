package playground.vertx;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.vertx.core.Vertx;

@SpringBootApplication
public class VertxSpringApplication {

    @Autowired
    private HelloWorldVerticle helloWorldVerticle;

    @Autowired
    private GreetingVerticle greetingVerticle;

    @Autowired
    private ServerVerticle serverVerticle;

    public static void main(String[] args) {
        SpringApplication.run(VertxSpringApplication.class, args);
    }

    @Bean
    public Vertx vertx() {
        return Vertx.vertx();
    }

    @PostConstruct
    public void VertxSprintApplication() {
        vertx().deployVerticle(greetingVerticle);
        vertx().deployVerticle(helloWorldVerticle);
        vertx().deployVerticle(serverVerticle);
    }

}
