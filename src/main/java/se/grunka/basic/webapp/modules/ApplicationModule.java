package se.grunka.basic.webapp.modules;

import com.google.inject.AbstractModule;
import se.grunka.basic.webapp.example.ExampleModule;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
		install(new JerseyConfigurationModule());
		install(new DataSourcesModule());
		install(new WebResourcesModule());

        install(new ExampleModule());
    }
}
