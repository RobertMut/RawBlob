package pl.rawblobclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import pl.rawblobclient.interfaces.services.ICommandService;
import pl.rawblobclient.view.MainView;

import java.io.IOException;

public class RawBlobClientApplication extends Application {

    private static ApplicationContext context;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RawBlobClientApplication.class.getResource("main-view.fxml"));
        var service = (ICommandService) context.getBean("commandService");
        fxmlLoader.setController(new MainView(service));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        context = new AnnotationConfigApplicationContext(Bootstrapper.class);
        launch();
    }
}