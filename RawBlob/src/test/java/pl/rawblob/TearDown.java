package pl.rawblob;

import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobServiceClientBuilder;
import junit.framework.TestCase;
import org.junit.AfterClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.RunListener;
import org.junit.runners.Suite;
import pl.rawblob.helpers.JsonHelperTest;
import pl.rawblob.helpers.NetworkHelperTest;
import pl.rawblob.services.CommandServiceTest;
import pl.rawblob.services.NetworkServiceTest;
import pl.rawblob.services.azure.BlobServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({BlobServiceTest.class, CommandServiceTest.class, NetworkServiceTest.class, JsonHelperTest.class, NetworkHelperTest.class})
public class TearDown extends RunListener {
    public static final String ConnectionString = "AccountName=devstoreaccount1;AccountKey=Eby8vdM02xNOcqFlqUwJPLlmEtlCDXJ1OUzFT50uSRZ6IFsuFq2UVErCz4I6tq/K1SZFPTOtr/KBHBeksoGMGw==;DefaultEndpointsProtocol=http;BlobEndpoint=http://127.0.0.1:10000/devstoreaccount1;QueueEndpoint=http://127.0.0.1:10001/devstoreaccount1;TableEndpoint=http://127.0.0.1:10002/devstoreaccount1;";

    @Override
    public void testRunFinished(Result result) throws Exception {
        var blobContainerClient = new BlobServiceClientBuilder()
                .credential(new DefaultAzureCredentialBuilder().build())
                .connectionString(TearDown.ConnectionString)
                .buildClient().getBlobContainerClient("items");

        blobContainerClient.delete();
        super.testRunFinished(result);
    }
}
