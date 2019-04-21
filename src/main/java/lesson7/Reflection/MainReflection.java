package lesson7.Reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;

public class MainReflection {
    public static void main(String[] args) throws MalformedURLException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        File file = new File("D:/Project/java3/123/");
        String[] str = file.list();
        Class reflClass = null;
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:///D:/Project/java3/123/")});
        reflClass = urlClassLoader.loadClass("Main");


        for (String o: str) {
            String[] mass = o.split("\\.");
            if(!mass[1].equalsIgnoreCase("class")){
                throw new RuntimeException(o, new Exception());
            } else {
                reflClass = urlClassLoader.loadClass(mass[0]);
            }
        }

        System.out.println("Имя класса " + reflClass.getName());


        Field[] publicFields = reflClass.getDeclaredFields();
        for (Field o : publicFields) {
            System.out.println( "Тип_поля Имя_поля : " + o.getType().getName() + " " + o.getName());
        }

        Method[] methods = reflClass.getDeclaredMethods();
        for (Method o : methods) {
            System.out.println(o.getReturnType() + " ||| " + o.getName() + " ||| " + Arrays.toString(o.getParameterTypes()));
        }
        Method isNegative = reflClass.getDeclaredMethod("isNegative", int.class);
        isNegative.setAccessible(true);
        System.out.println("Вызов метода isNegative(5): " + isNegative.invoke(reflClass, 5 ));
        System.out.println("Вызов метода isNegative(-5): " + isNegative.invoke(reflClass, -5 ));

        Method calculate = reflClass.getDeclaredMethod( "calculate" , int.class, int.class, int.class, int.class);
        calculate.setAccessible(true);
        System.out.println("Вызов метода calculate(1,2,3,4): " + calculate.invoke(reflClass, 1,2,3,3 ));

        Method main = reflClass.getDeclaredMethod( "main" , String[].class);
        main.setAccessible(true);
        System.out.println("Вызов метода main: " + main.invoke(reflClass, (Object) null));
    }
}
