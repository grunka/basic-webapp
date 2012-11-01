package se.grunka.basic.webapp.example;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.jersey.api.view.Viewable;

@Path("/example")
public class ExampleResource {
    @GET
    @Path("/view")
    @Produces(MediaType.TEXT_HTML)
    public Response showView() {
        ExampleModel model = new ExampleModel();
        return Response.ok(new Viewable("/example/view.jsp", model)).build();
    }

    public static class ExampleModel {
        public String getMessage() {
            return "Hello World!";
        }

        public List<String> getCounter() {
            return Arrays.asList("One", "Two", "Three");
        }
    }

    @GET
    @Path("/json")
    @Produces(MediaType.APPLICATION_JSON)
    public Response showJson() {
        Map<String, String> content = new HashMap<>();
        content.put("content", "hello world");
        return Response.ok(content).build();
    }

    @POST
    @Path("/json")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response readJson(Map<String, String> jsonObject) {
        String result = "";
        for (Map.Entry<String, String> entry : jsonObject.entrySet()) {
            result += entry.getKey() + " = " + entry.getValue() + "\n";
        }
        return Response.ok(result).build();
    }
}
