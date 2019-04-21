package lesson6.NetChat.server;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientHandler {

    private MainServer server;
    private Socket socket;
    private DataOutputStream out;
    private DataInputStream in;
    private List<String> blacklist;
    private String nick;
    private Logger loggerAdmin = Logger.getLogger("admin");

    public ClientHandler(MainServer server, Socket socket) {
        try {
            this.blacklist = new ArrayList<>();
            this.server = server;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            String str = in.readUTF();

                            if(str.startsWith("/auth")) {
                                String[] tokens = str.split(" ");
                                String newNick = AuthService.getNickByLoginAndPass(tokens[1], tokens[2]);

                                if(newNick != null) {
                                    if(!server.isNickBusy(newNick)) {
                                        sendMsg("/authok " + newNick);
                                        loggerAdmin.info(newNick + " прошел аутентификацию");

                                        nick = newNick;
                                        server.subscribe(ClientHandler.this);
                                        getBlackListFromDB(nick);
                                        break;
                                    } else {
                                        sendMsg("Учетная запись уже используется!");
                                        loggerAdmin.info("Учетная запись" + newNick + "уже используется!");
                                    }
                                } else {
                                    sendMsg("Неверный логин/пароль");
                                    loggerAdmin.info("Неверный логин/пароль");
                                }
                            }
                        }

                        while (true) {
                            String str = in.readUTF();
                            if (str.startsWith("/")) {
                                if (str.equals("/end")) {
                                    loggerAdmin.info(nick + " отправил команду /end");
                                    out.writeUTF("/serverclosed");
                                    break;
                                }
                                if (str.startsWith("/w ")) {
                                    String[] tokens = str.split(" ",3);
                                    server.sendPersonalMsg(ClientHandler.this, tokens[1], tokens[2]);
                                    loggerAdmin.info(nick + " отправил команду /w " + tokens[2]);
                                }
                                if (str.startsWith("/blacklist ")) {
                                    String[] tokens = str.split(" ");
                                    blacklist.add(tokens[1]);
                                    loadBlackListToDB(nick);
                                    sendMsg("Вы добавили пользователя " + tokens[1] + " в черный список");
                                    loggerAdmin.info(nick + " отправил команду /blacklist" + tokens[1]);
                                }
                                if (str.startsWith("/blacklistRemove ")) {
                                    String[] tokens = str.split(" ");
                                    blacklist.remove(tokens[1]);
                                    loadBlackListToDB(nick);
                                    sendMsg("Вы убрали пользователя " + tokens[1] + " из черного списка");
                                    loggerAdmin.info(nick + " отправил команду /blacklistRemove" + tokens[1]);
                                }
                            } else {
                                server.broadcastMsg(ClientHandler.this, nick + " : " + str);
                            }
                        }

                    } catch (IOException | SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            in.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        server.unsubscribe(ClientHandler.this);
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkBlackList(String nick) {
        return blacklist.contains(nick);
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNick() {
        return nick;
    }

    public void getBlackListFromDB(String nick) throws SQLException {
        String list = AuthService.getBlacklistByNick(nick);
        if(list != null){
            String[] tokens = list.split(" ");
            blacklist.addAll(Arrays.asList(tokens));
        }
    }

    public void loadBlackListToDB(String nick) throws SQLException {
        StringBuilder sb = new StringBuilder();
        if(blacklist.isEmpty()){
            AuthService.setBlacklistByNick(nick, "none");
        } else {
            for (String s: blacklist) {
                sb.append(s + " ");
            }
            AuthService.setBlacklistByNick(nick, sb.toString());
        }
    }
}
