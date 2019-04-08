//1. Создать три потока, каждый из которых выводит определенную букву (A, B и C)
//5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.

package lesson4;

public class MainThreads {
    private static final int maxABC = 5;

    public static void main(String[] args) {

        SequentialPrinting sp = new SequentialPrinting();

        Runnable r1 = ()->{
            while (sp.getCountA() != maxABC){
                sp.printA();
            }
        };
        Runnable r2 = ()->{
            while (sp.getCountB() != maxABC){
                sp.printB();
            }
        };
        Runnable r3 = ()->{
            while (sp.getCountC() != maxABC){
                sp.printC();
            }
        };

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);
        Thread t3 = new Thread(r3);

        t1.start();
        t2.start();
        t3.start();

    }

}

class SequentialPrinting{

    private int countA;
    private int countB;
    private int countC;

    public SequentialPrinting(){
        this.countA = 0;
        this.countB = 0;
        this.countC = 0;
    }

    public int getCountA() {
        return countA;
    }

    public int getCountB() {
        return countB;
    }

    public int getCountC() {
        return countC;
    }

    public synchronized void printA(){
        if((countA == countB) && (countA == countC)){
            countA++;
            System.out.print("A");
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void printB(){
        if((countA > countB) && (countB == countC)){
            countB++;
            System.out.print("B");
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void printC(){
        if((countA == countB) && (countB > countC)){
            countC++;
            System.out.println("C");
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
