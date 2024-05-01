package Task2;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Random;
import java.util.Scanner;

class Check {
    private double x;
    private double y;
    private String name;

    public Check(double x, double y, String name) {
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public double Dist() {
        return Math.sqrt(x * x + y * y);
    }

    public void setRandomData() {
        Random random = new Random();
        x = random.nextDouble() * 100;
        y = random.nextDouble() * 100;
    }

    public void setData(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Check{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                '}';
    }
}

class ReflectionExample {
    public static void printObjectInfo(Object obj) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Тип об'єкта: " + obj.getClass().getName());
        System.out.println("Стан об'єкта:");
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                System.out.println(field.getType().getName() + " " + field.getName() + " = " + field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Список відкритих методів:");
        Method[] methods = obj.getClass().getDeclaredMethods();
        int count = 1;
        for (Method method : methods) {
            System.out.println(count + "). " + method.toString());
            count++;

        }
    }

    public static void main(String[] args) {
        Check obj = new Check(3.0, 4.0, "Object1");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printObjectInfo(obj);
            System.out.println("Введіть порядковий номер методу для виклику, або 0 для виходу:");
            int methodNumber = scanner.nextInt();
            if (methodNumber == 0) {
                System.out.println("Програма завершена.");
                break;
            }
            try {
                Method[] methods = obj.getClass().getDeclaredMethods();
                Method selectedMethod = methods[methodNumber - 1];
                System.out.println("---------------------------------------------------------------------------");
                if (selectedMethod.getName().equals("setRandomData")) {
                    selectedMethod.invoke(obj);
                    System.out.println("Метод встановлення випадкових даних був успішно викликаний.");
                } else if (selectedMethod.getName().equals("setData")) {
                    System.out.println("Введіть значення для x:");
                    double x = scanner.nextDouble();
                    System.out.println("Введіть значення для y:");
                    double y = scanner.nextDouble();
                    selectedMethod.invoke(obj, x, y);
                    System.out.println("Метод setData був успішно викликаний.");
                } else if (selectedMethod.getName().equals("setName")) {
                    System.out.println("Введіть нове значення для імені:");
                    String newName = scanner.next();
                    selectedMethod.invoke(obj, newName);
                    System.out.println("Метод setName був успішно викликаний.");
                } else if (selectedMethod.getName().equals("getName")) {
                    Object result = selectedMethod.invoke(obj);
                    System.out.println("Результат виклику методу getName: " + result);
                } else {
                    Object result = selectedMethod.invoke(obj);
                    System.out.println("Результат виклику методу: " + result);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        scanner.close();
    }
}
