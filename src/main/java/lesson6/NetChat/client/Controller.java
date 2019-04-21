package lesson6.NetChat.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Controller {
    @FXML
    public ScrollPane Scroll;
    @FXML
    public VBox VBboxChat;
    @FXML
    TextArea textArea;

    @FXML
    TextField textField;

    @FXML
    Button btn1;

    @FXML
    HBox bottomPanel;

    @FXML
    HBox upperPanel;

    @FXML
    TextField loginField;

    @FXML
    PasswordField passwordField;

    @FXML
    ListView<String> clientList;

    private boolean isAuthorized;

    Socket socket;
    DataInputStream in;
    DataOutputStream out;

    final String IP_ADRESS = "localhost";
    final int PORT = 8189;
    private static String nick;
    private int index;
    private String thisNick;

    public void setAuthorized(boolean isAuthorized) {
        this.isAuthorized = isAuthorized;
        if(!isAuthorized) {
            upperPanel.setVisible(true);
            upperPanel.setManaged(true);
            bottomPanel.setVisible(false);
            bottomPanel.setManaged(false);
            clientList.setVisible(false);
            clientList.setManaged(false);
        } else {
            upperPanel.setVisible(false);
            upperPanel.setManaged(false);
            bottomPanel.setVisible(true);
            bottomPanel.setManaged(true);
            clientList.setVisible(true);
            clientList.setManaged(true);
        }
    }

    public void tryToAuth(ActionEvent actionEvent) {

        if(socket == null || socket.isClosed()) {
            connect();
        }

        try {
            out.writeUTF("/auth " + loginField.getText() + " " + passwordField.getText());
            loginField.clear();
            passwordField.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connect() {
        try {
            socket = new Socket(IP_ADRESS, PORT);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/authok")) {
                                String[] tokens = str.split(" ");
                                thisNick = tokens[1];
                                setAuthorized(true);
                                break;
                            }
//                            else {
//                                Platform.runLater(() -> putText(str + "\n"));
//                                //textArea.appendText(str + "\n");
//                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if(str.startsWith("/")) {
                                if (str.equals("/serverclosed"))  {
                                    // closeApp();
                                    break;
                                }
                                if (str.startsWith("/clientlist ")) {
                                    String[] tokens = str.split(" ");
                                    Platform.runLater(() -> {
                                        clientList.getItems().clear();
                                        for (int i = 1; i < tokens.length; i++) {
                                            clientList.getItems().add(tokens[i]);
                                        }
                                    });
                                }
                            } else {
                                Platform.runLater(() -> putText(str + "\n"));
                                //textArea.appendText(str + "\n");
                            }
                            clientList.setOnMouseClicked(event -> {
                                    nick = clientList.getSelectionModel().getSelectedItem();
                                    if(nick != null) {
                                        MiniStage miniStage = new MiniStage(getParent());
                                    }

                            });
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        setAuthorized(false);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMsg() {
        try {
            out.writeUTF(textField.getText());
            textField.clear();
            textField.requestFocus();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void sendMsgField(String s) {
        textField.appendText(s);
        sendMsg();
    }

    public void putText(String text){
        String fromNick;
        String[] tokens = text.split(" ");
        fromNick = tokens[0];

        Label label = new Label(text + "\n");
        label.setWrapText(true);
        VBox vBoxi = new VBox();

        if(!thisNick.equals(fromNick)) {
            vBoxi.setAlignment(Pos.TOP_LEFT);
        } else {
            vBoxi.setAlignment(Pos.TOP_RIGHT);
        }

        vBoxi.getChildren().add(label);
        VBboxChat.getChildren().add(vBoxi);
        index++;

//        textField.clear();
//        textField.requestFocus();
    }



    public Controller getParent(){
        return this;
    }

    public String getRecipientNick(){
        return nick;
    }
//    public void closeApp() {
//        try {
//            socket.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.exit(0);
//    }
}
