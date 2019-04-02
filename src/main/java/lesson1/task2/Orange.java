package lesson1.task2;

public class Orange extends Fruit {
    private Number weight;

    public Orange(Number weight) {
        super(weight);
        this.weight = weight;
    }

    @Override
    public Number getWeight() {
        return this.weight;
    }
}
