package Task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Random;

public class ArrayReflect {
    public static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    public static final Random random = new Random();

    public static void main(String... args) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        int[] dimension = initDimension(0);
        String classString = "";

        while (true) {
            System.out.println("Введіть повну назву класу або примітивний тип:");
            classString = scanLine();
            Object array;
            try {
                array = getArray(classString, dimension);
            } catch (ClassNotFoundException e) {
                System.out.println("Клас не знайдено.");
                return;
            }

            int choice = -1;
            while (choice != 0) {
                menu();
                choice = scan();
                switch (choice) {
                    case 1:
                        dimension = initDimension(dimension.length);
                        array = setSize(array, dimension, classString);
                        break;
                    case 2:
                        System.out.println(showArray(array));
                        break;
                    case 3:
                        fillArray(array);
                        break;
                    case 4:
                        System.out.println(convertToString(array));
                        break;
                }
            }

            dimension = initDimension(0);
        }
    }

    public static void menu() {
        System.out.println("--------------------------");
        System.out.println("1 - Змінити розмір");
        System.out.println("2 - Показати");
        System.out.println("3 - Заповнити");
        System.out.println("4 - Перетворити в рядок");
        System.out.println("0 - Вихід");
        System.out.println("--------------------------");
    }

    public static int[] initDimension(int modifier) {
        int[] dimension;
        if (modifier == 0) {
            System.out.print("Введіть кількість вимірів масиву (1/2), або введіть 'exit' для виходу: ");
            String input = scanLine().trim();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Вихід з програми...");
                System.exit(0);
            }

            dimension = new int[Integer.parseInt(input)];
        } else if (modifier == 1) {
            System.out.print("Введіть новий розмір масиву: ");
            dimension = new int[1];
        } else {
            System.out.print("Введіть нові розміри масиву: ");
            dimension = new int[2];
        }

        if (dimension.length > 2 || dimension.length < 1) {
            System.out.println("Невірні дані.");
            System.exit(0);
        }

        for (int i = 0; i < dimension.length; i++) {
            System.out.print("\n=> ");
            System.out.print("Введіть дані " + (i + 1) + ": ");
            dimension[i] = scan();
            System.out.print("\n");
        }

        return dimension;
    }

    public static int getDimension(Object array) {
        String classString = array.getClass().toString();
        int dimension = 0;

        for (int i = 0; i < classString.length(); i++) {
            if (classString.charAt(i) == '[') {
                dimension++;
            }
        }
        return dimension;
    }

    public static Class<?> getClass(Object array) throws ClassNotFoundException {
        String s = array.getClass().toString();
        if (s.indexOf(';') != -1) {
            s = s.substring(s.indexOf('L') + 1, s.indexOf(';'));
            return Class.forName(s);
        } else {
            return getSimpleClass(s.charAt(s.length() - 1));
        }

    }

    public static void init1DArray(Object array) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        String classString = getClass(array).getName();
        for (int i = 0; i < Array.getLength(array); i++) {
            ClassManager classManager = new ClassManager(classString);
            classManager.newInstance();
            Array.set(array, i, classManager.getObject());
        }
    }

    public static void init2DArray(Object array) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        Object[][] arrayCopy = (Object[][]) array;
        String classString = getClass(array).getName();

        for (int i = 0; i < arrayCopy.length; i++) {
            for (int j = 0; j < arrayCopy[0].length; j++) {
                ClassManager classManager = new ClassManager(classString);
                classManager.newInstance();
                ((Object[][]) array)[i][j] = classManager.getObject();
            }
        }
    }

    public static void handInit(Object array) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (getDimension(array) == 1)
            init1DArray(array);
        else
            init2DArray(array);
    }

    public static Object getArray(Class<?> cls, int[] dimension) {
        return Array.newInstance(cls, dimension);
    }

    public static Object getArray(String cls, int[] dimension) throws ClassNotFoundException {
        Class<?> simpleClass;
        return getArray((simpleClass = getSimpleClass(cls)) == null ? Class.forName(cls) : simpleClass, dimension);
    }

    public static Class<?> getSimpleClass(String string) {
        switch (string) {
            case "int":
                return int.class;
            case "double":
                return double.class;
            case "long":
                return long.class;
            case "float":
                return float.class;
            case "short":
                return short.class;
            case "char":
                return char.class;
            case "boolean":
                return boolean.class;
        }

        return null;
    }


    public static Class<?> getSimpleClass(Character letter) {
        switch (letter) {
            case 'I':
                return Integer.class;
            case 'D':
                return Double.class;
            case 'J':
                return Long.class;
            case 'F':
                return Float.class;
            case 'S':
                return Short.class;
            case 'C':
                return Character.class;
            case 'Z':
                return Boolean.class;
        }

        return null;
    }


    public static Object setSize(Object array, int[] newDimension, String classString) throws ClassNotFoundException {
        Object newArray = getArray(classString, newDimension);

        int height = Math.min(newDimension[0], Array.getLength(array));

        if (getDimension(array) == 2) {
            int width;

            for (int i = 0; i < height; i++) {
                if (newDimension[1] > Array.getLength(Array.get(array, i)))
                    width = Array.getLength(Array.get(array, i));
                else
                    width = newDimension[1];

                System.arraycopy(Array.get(array, i), 0, Array.get(newArray, i), 0, width);
            }
        } else {
            System.arraycopy(array, 0, newArray, 0, height);
        }

        return newArray;
    }

    public static String showArray(Object array) {
        StringBuilder stringBuilder = new StringBuilder();
        if (getDimension(array) == 1) {
            for (int i = 0; i < Array.getLength(array); i++) {
                stringBuilder.append(Array.get(array, i).toString()).append(" ");
            }
            stringBuilder.append("\n");
        } else {
            for (int i = 0; i < Array.getLength(array); i++) {
                for (int j = 0; j < Array.getLength(Array.get(array, i)); j++) {
                    stringBuilder.append(Array.get(Array.get(array, i), j)).append(" ");
                }
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public static String convertToString(Object array) {
        StringBuilder result = new StringBuilder();
        if (getDimension(array) == 1) {
            if (array instanceof int[]) {
                result.append(Arrays.toString((int[]) array));
            } else if (array instanceof double[]) {
                result.append(Arrays.toString((double[]) array));
            } else if (array instanceof long[]) {
                result.append(Arrays.toString((long[]) array));
            } else if (array instanceof float[]) {
                result.append(Arrays.toString((float[]) array));
            } else if (array instanceof short[]) {
                result.append(Arrays.toString((short[]) array));
            } else if (array instanceof char[]) {
                result.append(Arrays.toString((char[]) array));
            } else if (array instanceof boolean[]) {
                result.append(Arrays.toString((boolean[]) array));
            } else {
                result.append(Arrays.toString((Object[]) array));
            }
        } else {
            for (int i = 0; i < Array.getLength(array); i++) {
                result.append(convertToString(Array.get(array, i)));
                if (i != Array.getLength(array) - 1) {
                    result.append(", ");
                }
            }
        }
        return result.toString();
    }


    public static void fillArray(Object array) {
        if (getDimension(array) == 1) {
            for (int i = 0; i < Array.getLength(array); i++) {
                Array.set(array, i, random.nextInt(100));
            }
        } else {
            for (int i = 0; i < Array.getLength(array); i++) {
                for (int j = 0; j < Array.getLength(Array.get(array, i)); j++) {
                    Array.set(Array.get(array, i), j, random.nextInt(100));
                }
            }
        }
    }

    public static int scan() {
        int choice = 0;
        boolean flag = true;
        while (flag) {
            flag = false;
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (NumberFormatException | IOException e) {
                flag = true;
                System.err.println("Incorrect number format");
            }
        }
        return choice;
    }

    public static String scanLine() {
        String inputChoice = "";
        try {
            inputChoice = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputChoice;
    }
}

