package se.grunka.basic.webapp.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

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
}
