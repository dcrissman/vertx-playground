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

    private final Vertx vertx;

    @Autowired
    public HelloWorldVerticle(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.eventBus().send("greeting", "World", response -> {
            if (response.succeeded()) {
                LOGGER.info(response.result().body().toString());
            }
        });

        startFuture.complete();
    }

}
