package pl.rawblob;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.rawblob.interfaces.services.INetworkService;

/**
 * RawBlobApp class
 */
public class RawBlobApp
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(Bootstrapper.class);

        var networkService = context.getBean(INetworkService.class);

        networkService.listen();
    }
}
