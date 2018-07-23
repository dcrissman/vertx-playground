package playground.vertx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

@Component
public class GreetingVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            GreetingVerticle.class);

    @Autowired
    private Vertx vertx;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.eventBus()
            .localConsumer("greeting")
            .handler(event -> LOGGER.info("Greetings " + event.body().toString()));
        startFuture.complete();
    }

}
