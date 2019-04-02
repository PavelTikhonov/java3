package lesson1.task2;

public class Main {
    public static void main(String[] args) {
        Box<Orange> orangeBox = new Box<Orange>(new Orange(4.5), new Orange(4), new Orange(2));
        orangeBox.put(new Orange(2.5));
        System.out.println("orangeBox size = " + orangeBox.getSize());
        System.out.println("orangeBox weight = " + orangeBox.getWeight());

        Box<Apple> appleBox = new Box<Apple>(new Apple(4.5), new Apple(4), new Apple(2));
        appleBox.put(new Apple(2.5));
        System.out.println("orangeBox size = " + appleBox.getSize());
        System.out.println("appleBox weight = " + appleBox.getWeight());

        System.out.println("orangeBox.Compare(appleBox): " + orangeBox.compare(appleBox));

        Box<Orange> orangeBox1 = new Box<Orange>(new Orange(4.5), new Orange(4), new Orange(2));
        System.out.println("orangeBox1 size = " + orangeBox1.getSize());
        System.out.println("orangeBox1 weight = " + orangeBox1.getWeight());

        try {
            orangeBox.takeAll(orangeBox1);
        } catch (FruitExeption fruitExeption) {
            fruitExeption.printStackTrace();
        }
        System.out.println("orangeBox.takeAll(orangeBox1)");
        System.out.println("orangeBox size = " + orangeBox.getSize());
        System.out.println("orangeBox1 size = " + orangeBox1.getSize());

        System.out.println("orangeBox weight = " + orangeBox.getWeight());
        System.out.println("orangeBox.Compare(appleBox): " + orangeBox.compare(appleBox));
    }
}
