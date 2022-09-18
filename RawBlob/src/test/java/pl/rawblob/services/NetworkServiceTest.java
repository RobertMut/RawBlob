package pl.rawblob.services;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import pl.rawblob.interfaces.services.IClientService;

import java.net.Socket;

@RunWith(MockitoJUnitRunner.class)
public class NetworkServiceTest extends TestCase {

    @InjectMocks
    private NetworkService networkService;

    @Mock
    private IClientService clientService;

    public NetworkServiceTest(){
        this.clientService = Mockito.spy(ClientService.class);
        this.networkService = new NetworkService("16553", "127.0.0.1", this.clientService);
    }

    @Test
    public void testListen() throws InterruptedException {
        Thread thread = new Thread(() -> this.networkService.listen());

        thread.start();
        Thread.sleep(2000);
        thread.interrupt();

        Mockito.verify(clientService, Mockito.never()).manage(Mockito.any(Socket.class));
    }
}