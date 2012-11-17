package se.grunka.basic.webapp.example;

import java.util.Map;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import se.grunka.basic.webapp.jersey.JsonProvider;

public class ExampleClient {
    public static void main(String[] args) {
        ClientConfig config = new DefaultClientConfig();
        config.getClasses().add(JsonProvider.class);
        Client client = Client.create(config);
        client.setConnectTimeout(5);
        client.setReadTimeout(30);
        WebResource resource = client.resource("http://localhost:8080/example");

        Map<String, String> response = resource.path("json").get(new GenericType<Map<String, String>>() {});
        System.out.println("content = " + response.get("content"));
    }
}
