package pl.rawblob.services.azure;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.SecretServiceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.interfaces.services.azure.IKeyVaultService;

/**
 * KeyVaultService class
 */
@Component
public class KeyVaultService implements IKeyVaultService {
    private SecretClient secretClient;
    private String azureStorage;

    /**
     * Constructs KeyVaultService
     * @param keyVaultUri uri to keyvault
     */
    @Autowired
    public KeyVaultService(String keyVaultUri){

        secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .serviceVersion(SecretServiceVersion.getLatest())
                .buildClient();
        azureStorage = secretClient.getSecret("azureStorage").getValue();
    }

    /**
     * Gets azure storage connection string
     * @return storage connection string
     */
    @Override
    public String getAzureStorage() {
        return azureStorage;
    }

}
