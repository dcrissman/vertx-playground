package playground.vertx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

@Component
public class ServerVerticle extends AbstractVerticle {

    private final Vertx vertx;
    
    @Autowired
    public ServerVerticle(Vertx vertx) {
        this.vertx = vertx;
    }

    @Override
    public void start(Future<Void> startFuture) throws Exception {
        final Router router = Router.router(vertx);
        router.get("/jones")
            .handler(routingContext -> {
                routingContext.response().setChunked(true);
                
                vertx.eventBus().send("greeting", "Mr. Jones", response -> {
                    if (response.succeeded()) {
                        routingContext.response().write(response.result().body().toString());
                    }
                    else {
                        routingContext.response().write("failed");
                    }
                });
                
                
        });

        vertx.createHttpServer()
                .requestHandler(router::accept)
                .listen(config().getInteger("http.port", 8181));
        
        startFuture.complete();
    }

}
