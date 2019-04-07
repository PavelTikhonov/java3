package lesson3;
/*
1. Прочитать файл (около 50 байт) в байтовый массив и вывести этот массив в консоль;
2. Последовательно сшить 5 файлов в один (файлы примерно 100 байт). Может пригодиться следующая конструкция:
ArrayList<InputStream> al = new ArrayList<>(); ... Enumeration<InputStream> e = Collections.enumeration(al);
3. Написать консольное приложение, которое умеет постранично читать текстовые файлы (размером > 10 mb).
Вводим страницу (за страницу можно принять 1800 символов), программа выводит ее в консоль.
Контролируем время выполнения: программа не должна загружаться дольше 10 секунд, а чтение – занимать свыше 5 секунд.
*/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MainFile {
    public static void main(String[] args) {

        String dir = "src/main/java/lesson3/files";
        String name = "task1.txt";
        createFile(dir, name);

        System.out.println("task1:");
        String text = "To be, or not to be, that is the question:\n" +
                "Whether ’tis nobler in the mind to suffer\n" +
                "The slings and arrows of outrageous fortune,\n" +
                "Or to take arms against a sea of troubles\n";
        writeTextToFile(dir, name, text);
        readTextInFile(dir, name);
        System.out.println();

        System.out.println("task2:");
        String text1 = "And by opposing end them. To die-to sleep,\n" +
                "No more; and by a sleep to say we end\n" +
                "The heart-ache and the thousand natural shocks\n" +
                "That flesh is heir to: 'tis a consummation\n";
        String text2 = "Devoutly to be wish’d. To die, to sleep;\n" +
                "To sleep, perchance to dream-ay, there’s the rub:\n" +
                "For in that sleep of death what dreams may come,\n" +
                "When we have shuffled off this mortal coil,\n";
        String text3 = "Must give us pause-there’s the respect\n" +
                "That makes calamity of so long life.\n" +
                "For who would bear the whips and scorns of time,\n" +
                "Th’oppressor’s wrong, the proud man’s contumely,\n";
        String text4 = "The pangs of dispriz’d love, the law’s delay,\n" +
                "The insolence of office, and the spurns\n" +
                "That patient merit of th’unworthy takes,\n" +
                "When he himself might his quietus make\n";
        createFile(dir, "text1.txt");
        createFile(dir, "text2.txt");
        createFile(dir, "text3.txt");
        createFile(dir, "text4.txt");
        createFile(dir, "task2_common.txt");
        writeTextToFile(dir, "text1.txt", text1);
        writeTextToFile(dir, "text2.txt", text2);
        writeTextToFile(dir, "text3.txt", text3);
        writeTextToFile(dir, "text4.txt", text4);

        createCommonFile(dir, "task2_common.txt", "task1.txt", "text1.txt", "text2.txt", "text3.txt", "text4.txt");
        readTextInFile(dir, "task2_common.txt");
        System.out.println();

        System.out.println("task3:");
        while (true) {
            System.out.println("Введите номер страницы от 0 до 255");
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();

            createFile(dir, "task3.txt");
            writePagesToFile(dir, "task3.txt");
            readRandomPage(dir, "task3.txt", number);
        }


    }

    public static void createFile(String directory, String name){
        new File(directory).mkdirs();
        File file = new File(directory + "/" + name);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeTextToFile(String directory, String name, String text){
        try(FileOutputStream fos = new FileOutputStream(directory + "/" + name)) {
            fos.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readTextInFile(String directory, String name){
        byte [] buf = new byte [512];
        try(FileInputStream fis = new FileInputStream(directory + "/" + name)) {
            int count;
            while ((count = fis.read(buf)) != -1 ) {
                System.out.print(new String(buf, 0, count, "UTF-8"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createCommonFile(String dir, String commonName, String...names){
        ArrayList<InputStream> al = new ArrayList<>();
        try {
            for (String str: names) {
                al.add(new FileInputStream(dir + "/" + str));
            }

            SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(al));
            FileOutputStream fos = new FileOutputStream(dir + "/" + commonName);
            int x;
            while ((x = sis.read()) != -1){
                fos.write(x);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writePagesToFile(String directory, String name){
        byte [] buf = new byte [1800];
        try(FileOutputStream fos = new FileOutputStream((directory + "/" + name), true)) {
            for (int i = 0; i < 256; i++) {
                for (int j = 0; j < buf.length; j++) {
                    buf[j] = (byte)i;
                }
                fos.write(buf);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readRandomPage(String directory, String name, int page){
        int size = 1800;
        int line = 45;
        byte [] buf = new byte [size];
        try {
            RandomAccessFile raf = new RandomAccessFile(directory + "/" + name, "r");
            raf.seek(buf.length * page);
            raf.read(buf);
            for (int i = 0; i < line; i++) {
                for (int j = 0; j < buf.length / line; j++) {
                    System.out.print(buf[i]);
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


