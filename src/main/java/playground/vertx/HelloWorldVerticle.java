package playground.vertx;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

@Component
public class HelloWorldVerticle extends AbstractVerticle {

    private static final Logger LOGGER = LoggerFactory.getLogger(
            HelloWorldVerticle.class);

    @Autowired
    private Vertx vertx;

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.eventBus().send("greeting", "Frank Castle");

        LOGGER.info("Hello World!");

        startFuture.complete();
    }

}
