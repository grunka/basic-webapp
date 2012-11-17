package se.grunka.basic.webapp;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import se.grunka.basic.webapp.modules.ApplicationModule;

public class BasicConfiguration extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
		//TODO change to a nicer pattern
		Logger.getRootLogger().setLevel(Level.INFO);
		Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));

        return Guice.createInjector(Stage.PRODUCTION, new ApplicationModule());
    }
}
