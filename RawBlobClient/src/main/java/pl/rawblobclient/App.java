package pl.rawblobclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.rawblobclient.controllers.MainController;
import pl.rawblobclient.interfaces.services.ICommandService;
import pl.rawblobclient.services.CommandService;

import java.util.Properties;

/**
 * App class
 */
public class App extends Application {

    private ICommandService commandService;
    private Properties properties;
    private String address;
    private String port;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void init() throws Exception {
        properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("application.properties"));

        this.address = properties.getProperty("pl.rawblobclient.serverAddress");
        this.port = properties.getProperty("pl.rawblobclient.serverPort");
        commandService = new CommandService(address, port);

        super.init();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MainController controller = new MainController(commandService);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));

        loader.setController(controller);
        primaryStage.setScene(new Scene(loader.load(), 600, 400));
        primaryStage.show();
    }

}