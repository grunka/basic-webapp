package se.grunka.basic.webapp.example;

import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;

public class ExampleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ExampleResource.class).in(RequestScoped.class);
    }
}
