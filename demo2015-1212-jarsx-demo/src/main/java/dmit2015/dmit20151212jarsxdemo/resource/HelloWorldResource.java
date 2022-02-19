package dmit2015.dmit20151212jarsxdemo.resource;
    
      
      Welcome to the class!
      Try @mentioning the class name or student names to start a conversation.
      
    
    
  ​[2/1 7:03 AM] Sam Wu
    The agenda for this meeting:


	
Introduction how to build a web api using Jax-Rs
​[2/1 9:26 AM] Sam Wu
    

  
  
  
    
      
        HelloResource.java
      
      
        Java
      
    
    
    
      import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.InputStream;
@RequestScoped
@Path("/helloworld")
public class HelloWorldResource {​
    @GET
    @Produces(MediaType.TEXT_PLAIN) //@Produces("text/plain")
    public Response helloWorldText() {​
        String message = "Hello World from JAX-RS!";
        return Response.ok(message).build();
    }​
    @GET
    @Produces(MediaType.TEXT_HTML) // "text/html"
    public Response helloWorldHtml() {​
        String message = "<p>Hello World from <strong>JAX-RS</strong>";
        return Response.ok(message).build();
    }​
    @GET
    @Produces(MediaType.APPLICATION_JSON)   // "application/json"
    public Response helloWorldJson() {​
        String message = "{​\"message\":\"Hello World from JAX-RS\"}​";
        return Response.ok(message).build();
    }​
    @Path("/image")
    @GET
    @Produces("text/image")
    public Response helloImage(@Context HttpServletRequest request) {​
//        InputStream is = getClass().getResourceAsStream("/images/hello_world.png");
        File imageFile = new File("src/main/resources/META-INF/images/hello_world.png");
        return Response
                .ok(imageFile)
                .header("Content-Disposition","attachment; filename=hello_world.png")
                .build();
    }​
}​
    
    
  
  


Edited<https://teams.microsoft.com/l/message/19:d1803c5e062049d6b5d05f42ddc4888d@thread.tacv2/1643724223952?tenantId=5c98fb47-d3b9-4649-9d94-f88cbdd9729c&amp;groupId=aabb20c1-5fcc-4c57-ad72-3d0b118dbf8b&amp;parentMessageId=1643724223952&amp;teamName=DMIT2015-1212-OA01 Enterprise Application Development&amp;channelName=DMIT2015 Feb 01 - JAX-RS 1&amp;createdTime=1643724223952>