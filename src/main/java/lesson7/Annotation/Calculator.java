package lesson7.Annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Calculator {
    private int a;
    private int b;

    public Calculator(int a, int b){
        this.a = a;
        this.b = b;
    }

    @BeforeSuite
    public void startInfo() throws NoSuchFieldException {
        System.out.println("Calculator test start");
    }

    @Test(priority = 9)
    public int sum(){

        return  this.a + this.b;
    }

    @Test(priority = 3)
    public float div(){
        return (float) this.a / this.b;
    }

    @Test(priority = 4)
    public int mul(){
        return this.a * this.b;
    }

    @Test(priority = 2)
    public double log(){
        return Math.log(this.a + this.b);
    }

    @Test(priority = 8)
    public double sqrt(){
        return Math.sqrt(this.a + this.b);
    }

    @AfterSuite
    public void finalInfo(){
        System.out.println("Calculator test end");
    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        int countBefore = 0;
        int countAfter = 0;
        Method method;

        Calculator calc = new Calculator(16, 4);
        Method[] methods = Calculator.class.getDeclaredMethods();
        for (Method o : methods) {
            if (o.getAnnotation(BeforeSuite.class) != null ) {
                countBefore++;
            }
            if (o.getAnnotation(AfterSuite.class) != null ) {
                countAfter++;
            }
        }
        if(countBefore != 1){
            throw new RuntimeException("Methods annotated with @BeforeSuite declared not once");
        }
        if(countAfter != 1){
            throw new RuntimeException("Methods annotated with @AfterSuite declared not once");
        }

        for (Method o : methods) {
            if (o.isAnnotationPresent(BeforeSuite.class)) {
                o.invoke(calc);
            }
        }
        for (int i = 1; i < 11; i++) {
            for (Method o : methods) {
                if ((o.isAnnotationPresent(Test.class)) && (o.getAnnotation(Test.class).priority() == i)) {
                    System.out.println(o + "; result = " + o.invoke(calc));
                }
            }
        }
        for (Method o : methods) {
            if (o.isAnnotationPresent(AfterSuite.class)) {
                o.invoke(calc);
            }
        }

    }
}
