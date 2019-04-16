package lesson6.NetChat.client;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lesson6.NetChat.Observer.Model;
import lesson6.NetChat.Observer.Observer;

import java.io.IOException;

public class MiniStage implements Observer {


    Controller controller;
    public static Model model;

    public MiniStage(Controller controller) {
        Parent root = null;
        try {
            this.controller = controller;
            Stage stage = new Stage();
            root = FXMLLoader.load(getClass().getResource("/secondWindow.fxml"));
            stage.setTitle(this.controller.getRecipientNick() + " send message");
            stage.setScene(new Scene(root, 200, 50));
            stage.show();

            model = new Model();
            model.registerObserver(this);

            stage.setOnCloseRequest(we -> {
                stage.close();
                model.removeObserver(this);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void notification(String message) {
        this.controller.sendMsgField("/w " + this.controller.getRecipientNick() + " " + message);
    }
}
