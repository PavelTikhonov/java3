package lesson1.task1;

import java.util.ArrayList;

public class Generic<T> {
    private T[] arr;
    private int ind;

    public Generic(T... arr){
        this.arr = arr;
    }

    public void put(T obj){
        arr[ind++] = obj;
    }

    public void swap(int numb1, int numb2){
        T buf;

        if((numb1 >= arr.length) || (numb2 >= arr.length) || (numb1 == numb2)){
            System.out.println("error swap");
            return;
        }
        if(arr.length > 1){
            buf = arr[numb1];
            arr[numb1] = arr[numb2];
            arr[numb2] = buf;
        }
    }

    public T getObj(int ind){
        return arr[ind];
    }

    public void print(){
        for (T t: arr) {
            System.out.print("[" + t.toString() + "] ");
        }
        System.out.println();
    }

    public ArrayList<T> arrayToArrayList(){
        ArrayList<T> al = new ArrayList<>();
        for (T t: this.arr) {
            al.add(t);
        }
        return al;
    }
}
