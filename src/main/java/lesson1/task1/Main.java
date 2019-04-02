package lesson1.task1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Generic<String> gen = new Generic<String>("java", "c#", "puthon", "kotlin");
        gen.print();
        gen.swap(1,2);
        gen.print();

        ArrayList<String> al;
        al = gen.arrayToArrayList();
        for (int i = 0; i < al.size(); i++) {
            System.out.print("[" + al.get(i) + "] ");
        }
    }
}
