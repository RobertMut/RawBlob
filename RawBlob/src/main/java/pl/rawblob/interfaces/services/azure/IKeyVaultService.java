package pl.rawblob.interfaces.services.azure;

/**
 * KeyVaultService interface
 */
public interface IKeyVaultService {

    /**
     * Returns storage to be used from keyvault
     * @return Storage connection string
     */
    String getAzureStorage();
}
