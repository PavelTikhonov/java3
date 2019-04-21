package lesson6.NetChat.Observer;

import java.util.LinkedList;
import java.util.List;

public class Model implements Observable {
    private static List<Observer> observers = new LinkedList<>();

    // регистрация слушателя
    @Override public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    // уведомление слушателей
    @Override public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.notification(message);
        }
    }


}
