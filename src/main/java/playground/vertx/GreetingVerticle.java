package playground.vertx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;

@Component
public class GreetingVerticle extends AbstractVerticle {

    private final Vertx vertx;
    
    @Autowired
    public GreetingVerticle(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        vertx.eventBus()
                .localConsumer("greeting")
                .handler(event -> event.reply(
                        "Greetings " + event.body().toString()));
        startFuture.complete();
    }

}
