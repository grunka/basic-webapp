package se.grunka.basic.webapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import se.grunka.basic.webapp.example.ExampleModule;
import se.grunka.basic.webapp.modules.DataSourcesModule;
import se.grunka.basic.webapp.modules.JerseyConfigurationModule;
import se.grunka.basic.webapp.modules.WebResourcesModule;

public class BasicConfiguration extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
		//TODO change to a nicer pattern
		Logger.getRootLogger().setLevel(Level.INFO);
		Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));

        return Guice.createInjector(Stage.PRODUCTION, new AbstractModule() {
			@Override
			protected void configure() {
				bind(Gson.class).toInstance(new GsonBuilder().setPrettyPrinting().create());
				install(new JerseyConfigurationModule());
				install(new DataSourcesModule());
				install(new WebResourcesModule());

				install(new ExampleModule());
			}
		});
    }
}
