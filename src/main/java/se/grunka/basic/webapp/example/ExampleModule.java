package se.grunka.basic.webapp.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.servlet.RequestScoped;

public class ExampleModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ExampleResource.class).in(RequestScoped.class);
    }
}
