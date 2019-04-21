package lesson6.NetChat.server;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Vector;

public class MainServer {
    private Vector<ClientHandler> clients;
    private Logger loggerAdmin = Logger.getLogger("admin");

    public MainServer() throws SQLException {
        ServerSocket server = null;
        Socket socket = null;
        clients = new Vector<>();

        try {
            AuthService.connect();
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен!");
            loggerAdmin.info("Сервер запущен!");

            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                loggerAdmin.info("Клиент подключился");
                new ClientHandler(this,socket);
            }

        } catch (IOException e) {
             e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            AuthService.disconnect();
        }
    }

    public void sendPersonalMsg(ClientHandler from, String nickTo, String msg) {
        for (ClientHandler o: clients) {
            if ((o.getNick().equals(nickTo)) && (!o.checkBlackList(from.getNick()))) {
                o.sendMsg("from " + from.getNick() + ": " + msg);
                from.sendMsg("to " + nickTo + ": " + msg);
                loggerAdmin.info(from.getNick() + " отправил сообщение " + nickTo + ": " + msg);
                return;
            } else {
                from.sendMsg("Клиент с ником " + nickTo + " ограничил Вам доступ к личной переписке");
                return;
            }
        }
        from.sendMsg("Клиент с ником " + nickTo + " не найден в чате!");
    }

    public boolean isNickBusy(String nick) {
        for (ClientHandler o: clients) {
            if (o.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public void subscribe(ClientHandler client) {
        clients.add(client);
        broadcastClientList();
    }

    public void unsubscribe(ClientHandler client) {
        clients.remove(client);
        broadcastClientList();
    }

    public void broadcastMsg(ClientHandler from, String msg) {
        for (ClientHandler o : clients) {
            if(!o.checkBlackList(from.getNick())) {
                o.sendMsg(msg);
                loggerAdmin.info(from.getNick() + " отправил сообщение :"  + msg);
            }
        }
    }

    public void broadcastClientList() {
        StringBuilder sb = new StringBuilder();
        sb.append("/clientlist ");
        for (ClientHandler o: clients) {
            sb.append(o.getNick() + " ");
        }
        String out = sb.toString();

        for (ClientHandler o: clients) {
            o.sendMsg(out);
        }
    }
}
