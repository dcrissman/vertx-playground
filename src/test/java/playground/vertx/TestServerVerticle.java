package playground.vertx;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.rules.SpringClassRule;
import org.springframework.test.context.junit4.rules.SpringMethodRule;

import io.vertx.core.Vertx;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

@RunWith(VertxUnitRunner.class)
@ContextConfiguration(classes = TestServerVerticle.Configuration.class)
public class TestServerVerticle {

    @ClassRule
    public static final SpringClassRule SPRING_CLASS_RULE = new SpringClassRule();

    @Rule
    public final SpringMethodRule springMethodRule = new SpringMethodRule();

    @Autowired
    private Vertx vertx;

    @Autowired
    private ServerVerticle serverVerticle;

    @Before
    public void before(TestContext tc) {
        vertx.deployVerticle(new GreetingVerticle(vertx));
        vertx.deployVerticle(serverVerticle,
                tc.asyncAssertSuccess());
    }

    @After
    public void after(TestContext tc) {
        vertx.close(tc.asyncAssertSuccess());
    }

    @Test
    public void testResponse(TestContext tc) {
        final Async async = tc.async();

        vertx.createHttpClient()
            .getNow(8181, "localhost", "/jones", response -> {
                response.handler(responseBody -> {
                    tc.assertTrue(responseBody.toString().equalsIgnoreCase("Greetings Mr. Jones"));
                });
                async.complete();
            });
    }

    static class Configuration {

        @Bean
        public Vertx vertx() {
            return Vertx.vertx();
        }

        @Bean
        public ServerVerticle serverVerticle(Vertx vertx) {
            return new ServerVerticle(vertx);
        }

    }

}
