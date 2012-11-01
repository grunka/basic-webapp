package se.grunka.basic.webapp;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class BasicConfiguration extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        return Guice.createInjector(Stage.PRODUCTION, new JerseyServletModule() {
            @Override
            protected void configureServlets() {
                install(new ApplicationModule());

                Map<String, String> initParams = new HashMap<>();
                initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
                initParams.put(ServletContainer.JSP_TEMPLATES_BASE_PATH, "/WEB-INF/templates");
                bind(JsonProvider.class).in(Singleton.class);
                bind(ServletContainer.class).in(Singleton.class);
                filter("/*").through(GuiceContainer.class, initParams);
            }
        });
    }
}
