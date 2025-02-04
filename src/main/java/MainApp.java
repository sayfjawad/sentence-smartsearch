import jakarta.ws.rs.core.Application;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@OpenAPIDefinition(
        servers = {
                @Server(url = "http://localhost:8080", description = "Local Development Server")
        },
        tags = {
                @Tag(name = "search", description = "Smart sentence search API")
        },
        info = @Info(
                title = "Smart Sentence Search API",
                version = "1.0.1",
                contact = @Contact(
                        name = "API Support",
                        url = "http://exampleurl.com/contact",
                        email = "sayf@multicode.nl"),
                license = @License(
                        name = "Apache 2.0",
                        url = "https://www.apache.org/licenses/LICENSE-2.0.html"))
)
public class MainApp extends Application {
}
