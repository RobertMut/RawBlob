package pl.rawblobclient;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import pl.rawblobclient.interfaces.services.ICommandService;
import pl.rawblobclient.services.CommandService;
import pl.rawblobclient.view.MainView;
import pl.rawblobclient.viewmodel.MainViewModel;

import java.io.IOException;

@Configuration(proxyBeanMethods=false)
@ComponentScan(basePackages = {"pl.rawblobclient.services"})
@PropertySource("application.properties")
public class Bootstrapper {

    private final String address;
    private final String port;

    public Bootstrapper(Environment env) {
        address = env.getProperty("pl.rawblobclient.serverAddress");
        port = env.getProperty("pl.rawblobclient.serverPort");
    }

    @Bean("address")
    public String listenAddress() {
        return address;
    }

    @Bean("port")
    public String listenPort() {
        return port;
    }

    @Bean("commandService")
    public ICommandService commandService() throws IOException {
        return new CommandService(address, port);
    }
}
