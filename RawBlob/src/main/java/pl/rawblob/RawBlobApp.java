package pl.rawblob;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pl.rawblob.interfaces.services.INetworkService;

public class RawBlobApp
{
    public static void main( String[] args )
    {
        ApplicationContext context = new AnnotationConfigApplicationContext(Bootstrapper.class);

        var networkService = context.getBean(INetworkService.class);

        networkService.listen();
    }
}
