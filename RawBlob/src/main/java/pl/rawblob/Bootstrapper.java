package pl.rawblob;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import pl.rawblob.interfaces.services.IClientService;
import pl.rawblob.interfaces.services.ICommandService;
import pl.rawblob.interfaces.services.INetworkService;
import pl.rawblob.interfaces.services.azure.IBlobService;
import pl.rawblob.interfaces.services.azure.IKeyVaultService;
import pl.rawblob.services.ClientService;
import pl.rawblob.services.CommandService;
import pl.rawblob.services.NetworkService;
import pl.rawblob.services.azure.BlobService;
import pl.rawblob.services.azure.KeyVaultService;

@Configuration
@ComponentScan(basePackages = {"pl.rawblob.services", "pl.rawblob.services.azure"})
@PropertySource("application.properties")
public class Bootstrapper {
    private final static Logger LOGGER = LoggerFactory.getLogger(Bootstrapper.class);
    private final String keyVault;
    private final String listenAddress;
    private final String listenPort;

    public Bootstrapper(Environment env) {
        LOGGER.info("Acquiring application.properties values...");
        keyVault = env.getProperty("rawblobapp.vaultUrl");
        listenAddress = env.getProperty("rawblobapp.listenAddress");
        listenPort = env.getProperty("rawblobapp.port");
    }

    @Bean("keyVaultUri")
    public String keyVaultUri() {
        return keyVault;
    }

    @Bean("listenAddress")
    public String listenAddress() {
        return listenAddress;
    }

    @Bean("listenPort")
    public String listenPort() {
        return listenPort;
    }

    @Bean("commandService")
    public ICommandService commandService() {
        LOGGER.info("Bean: ICommandService");
        return new CommandService();
    }

    @Bean("clientService")
    public IClientService clientService(){
        LOGGER.info("Bean: IClientService");
        return new ClientService();
    }

    @Bean("keyVaultService")
    public IKeyVaultService keyVaultService(){
        LOGGER.info("Bean: IKeyVaultService");
        return new KeyVaultService(keyVault);
    }

    @Bean("networkService")
    public INetworkService networkService() {
        LOGGER.info("Bean: INetworkService");
        return new NetworkService();
    }

}
