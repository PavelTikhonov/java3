package lesson1.task2;

public class Apple extends Fruit {
    private Number weight;

    public Apple(Number weight) {
        super(weight);
        this.weight = weight;
    }

    @Override
    public Number getWeight() {
        return this.weight;
    }
}
