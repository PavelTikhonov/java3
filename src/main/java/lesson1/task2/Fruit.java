package lesson1.task2;

public abstract class Fruit<T extends Number> {
    private T weight;

    public Fruit(T weight){
        this.weight = weight;
    }

    public abstract T getWeight();
}
