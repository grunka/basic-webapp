package se.grunka.basic.webapp;

import com.google.inject.AbstractModule;
import se.grunka.basic.webapp.example.ExampleModule;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ExampleModule());
    }
}
