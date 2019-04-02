package lesson1.task2;

import java.util.ArrayList;
import java.util.Arrays;

public class Box<T extends Fruit> {
    private ArrayList<T> fruit = new ArrayList<>();
    private int number;

    public Box(T... fruit) {
        this.fruit.addAll(Arrays.asList(fruit));
    }

    public void put(T fruit){
        this.fruit.add(fruit);
    }

    public T take(){
        T buf = this.fruit.get(0);
        fruit.remove(0);
        return buf;
    }

    public int getSize(){
        return fruit.size();
    }

    public double getWeight(){
        double sum = 0;
        for (T t : fruit) {
            sum += t.getWeight().doubleValue();
        }
        return sum;
    }

    public boolean compare(Box box){
        return this.getWeight() == box.getWeight();
    }

    public void takeAll(Box<T> box) throws FruitExeption {
        if (this.equals(box)){
            throw new FruitExeption("Нельзя перекладывать фрукты из текущей коробки в текущую!");
        }
        int size = box.getSize();
        for (int i = 0; i < size; i++) {
            this.fruit.add(box.take());
        }
    }
}
