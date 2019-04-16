package lesson6.NetChat.client;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import lesson6.NetChat.Observer.Model;
import lesson6.NetChat.Observer.Observer;

public class ControllerSeconds implements Observer {

    @FXML
    TextField textField1;

    @FXML
    Button btn2;

    static Model model;

    public void sendMsg1() {
        model = new Model();
        model.registerObserver(this);
        model.notifyObservers(textField1.getText());
        model.removeObserver(this);
        textField1.clear();
        textField1.requestFocus();
    }

    @Override
    public void notification(String message) {

    }
}
