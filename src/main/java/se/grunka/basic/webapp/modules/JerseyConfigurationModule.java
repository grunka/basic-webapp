package se.grunka.basic.webapp.modules;

import java.util.HashMap;
import java.util.Map;

import com.google.inject.Singleton;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import se.grunka.basic.webapp.jersey.JsonProvider;

public class JerseyConfigurationModule extends JerseyServletModule {
	@Override
	protected void configureServlets() {
		Map<String, String> initParams = new HashMap<>();
		initParams.put(ServletContainer.FEATURE_FILTER_FORWARD_ON_404, "true");
		initParams.put(ServletContainer.JSP_TEMPLATES_BASE_PATH, "/WEB-INF/templates");
		bind(JsonProvider.class).in(Singleton.class);
		filter("/*").through(GuiceContainer.class, initParams);
	}
}
