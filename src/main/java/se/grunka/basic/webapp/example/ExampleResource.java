package se.grunka.basic.webapp.example;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

import com.sun.jersey.api.view.Viewable;

@Path("/example")
public class ExampleResource {
    @GET
    @Path("/view")
    @Produces(MediaType.TEXT_HTML)
    public Response showView() {
        Map<String, String> model = new HashMap<>();
        model.put("message", "hello world");
        return Response.ok(new Viewable("/example/view.jsp", model)).build();
    }
}
