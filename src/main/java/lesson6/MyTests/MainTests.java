//2. Написать метод, которому в качестве аргумента передается не пустой одномерный целочисленный массив.
//Метод должен вернуть новый массив, который получен путем вытаскивания из исходного массива элементов,
//идущих после последней четверки. Входной массив должен содержать хотя бы одну четверку,
//иначе в методе необходимо выбросить RuntimeException. Написать набор тестов для этого метода
//(по 3-4 варианта входных данных). Вх: [ 1 2 4 4 2 3 4 1 7 ] -> вых: [ 1 7 ].
//3. Написать метод, который проверяет состав массива из чисел 1 и 4. Если в нем нет
//хоть одной четверки или единицы, то метод вернет false; Написать набор тестов для этого метода
//(по 3-4 варианта входных данных).

package lesson6.MyTests;

public class MainTests {

    public int[] arrayConversion(int[] arr){
        int numb = arr.length;
        int newLength;

        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 4){
                numb = i + 1;
            }
        }
        if(numb == arr.length){
            throw new  RuntimeException();
        } else {
            int[] newArr = new int[arr.length - numb];
            for (int i = 0; i < newArr.length; i++) {
                newArr[i] = arr[numb + i];
            }
            return newArr;
        }
    }

    public boolean arrayCheck(int[] arr){
        int count1 = 0;
        int count4 = 0;

        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 1){
                count1++;
            }
            if(arr[i] == 4){
                count4++;
            }
        }
        if ((count1 != 0) && (count4 != 0)){
            return true;
        } else {
            return false;
        }
    }
}
