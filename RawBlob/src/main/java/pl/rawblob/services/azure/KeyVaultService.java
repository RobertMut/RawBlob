package pl.rawblob.services.azure;

import com.azure.core.credential.TokenCredential;
import com.azure.core.util.Configuration;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.SecretServiceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.rawblob.interfaces.services.azure.IKeyVaultService;

@Component
public class KeyVaultService implements IKeyVaultService {


    private SecretClient secretClient;
    private String azureStorage;
    private String sasToken;


    @Autowired
    public KeyVaultService(String keyVaultUri){

        secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUri)
                .credential(new DefaultAzureCredentialBuilder().build())
                .serviceVersion(SecretServiceVersion.getLatest())
                .buildClient();
        azureStorage = secretClient.getSecret("azureStorage").getValue();
        sasToken = secretClient.getSecret("sasToken").getValue();
    }

    @Override
    public String getAzureStorage() {
        return azureStorage;
    }

    @Override
    public String getSasToken() {
        return sasToken;
    }
}
