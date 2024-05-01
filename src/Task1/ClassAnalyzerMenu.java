package Task1;

import java.lang.reflect.*;
import java.util.Scanner;

public class ClassAnalyzerMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            displayMenu();
            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    analyzeClass(scanner);
                    break;
                case 2:
                    System.out.println("The program is over.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("1. Enter full class name");
        System.out.println("2. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice(Scanner scanner) {
        int choice = 0;
        while (true) {
            try {
                choice = Integer.parseInt(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
        return choice;
    }

    private static void analyzeClass(Scanner scanner) {
        System.out.print("Enter full class name: ");
        String className = scanner.nextLine();

        if (!className.isEmpty()) {
            try {
                Class<?> clazz = Class.forName(className);
                analyzeClass(clazz);
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found: " + e.getMessage());
            }
        } else {
            System.out.println("Please enter a class name.");
        }
    }

    private static void analyzeClass(Class<?> clazz) {
        System.out.println("Analyzing class: " + clazz.getName() + "\n");
        printClassInfo(clazz);
        printFields(clazz);
        printConstructors(clazz);
        printMethods(clazz);
    }

    private static void printClassInfo(Class<?> clazz) {
        Package pkg = clazz.getPackage();
        System.out.println("Package: " + pkg.getName());

        int modifiers = clazz.getModifiers();
        System.out.print(Modifier.toString(modifiers) + " class " + clazz.getSimpleName());

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            System.out.print(" extends " + superclass.getSimpleName());
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            System.out.print(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                System.out.print(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) {
                    System.out.print(", ");
                }
            }
        }
        System.out.println(" {\n");
    }

    private static void printFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        System.out.println("// Fields");
        for (Field field : fields) {
            System.out.println(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName() + ";");
        }
        System.out.println();
    }

    private static void printConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        System.out.println("// Constructors");
        for (Constructor<?> constructor : constructors) {
            System.out.print(Modifier.toString(constructor.getModifiers()) + " " + clazz.getSimpleName() + "(");
            Parameter[] parameters = constructor.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                System.out.print(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                if (i < parameters.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(");");
        }
        System.out.println();
    }

    private static void printMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        System.out.println("// Methods");
        for (Method method : methods) {
            System.out.print(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                System.out.print(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                if (i < parameters.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println(");");
        }
        System.out.println("}\n");
    }
}
