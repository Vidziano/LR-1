package Task6;

import java.lang.reflect.Constructor;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Test Object Creation");
        System.out.println("Object: CheckNext:");

        CheckNext checkNext = new CheckNext();
        System.out.println("origin: (" + checkNext.getOrigin().getX() + ", " + checkNext.getOrigin().getY() + ")");
        System.out.println("position: (" + checkNext.getPosition().getX() + ", " + checkNext.getPosition().getY() + ")");

        System.out.println("Let Create the same with reflection...");
        createObjectWithReflection();

    }

    private static void createObjectWithReflection() {
        try {
            Class<?> checkNextClass = Class.forName("CheckNext");

            Constructor<?>[] constructors = checkNextClass.getDeclaredConstructors();

            System.out.println("The beginning of creation of the CheckNext object");

            for (int i = 0; i < constructors.length; i++) {
                System.out.println((i + 1) + "). " + constructors[i]);
            }

            System.out.println("Input the Number of Constructor [1, " + constructors.length + "]:");
            Scanner scanner = new Scanner(System.in);
            int constructorIndex = scanner.nextInt() - 1;

            Constructor<?> selectedConstructor = constructors[constructorIndex];

            Class<?>[] parameterTypes = selectedConstructor.getParameterTypes();
            Object[] parameters = new Object[parameterTypes.length];

            for (int i = 0; i < parameterTypes.length; i++) {
                System.out.println("The beginning of creation of the " + parameterTypes[i].getSimpleName() + " object");
                parameters[i] = createObjectRecursively(parameterTypes[i]);
                System.out.println("The end of creation of the " + parameterTypes[i].getSimpleName() + " object");
            }

            Object checkNextObject = selectedConstructor.newInstance(parameters);

            System.out.println(checkNextObject);
            System.out.println("CheckNext:");
            System.out.println("origin: (" + ((CheckNext) checkNextObject).getOrigin().getX() + ", " +
                    ((CheckNext) checkNextObject).getOrigin().getY() + ")");
            System.out.println("position: (" + ((CheckNext) checkNextObject).getPosition().getX() + ", " +
                    ((CheckNext) checkNextObject).getPosition().getY() + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object createObjectRecursively(Class<?> clazz) {
        try {
            if (clazz.isPrimitive()) {
                Scanner scanner = new Scanner(System.in);
                System.out.println("Input " + clazz.getSimpleName() + " value:");
                if (clazz == int.class) {
                    return scanner.nextInt();
                } else if (clazz == double.class) {
                    return scanner.nextDouble();
                }
            } else {
                Constructor<?> defaultConstructor = clazz.getDeclaredConstructor();
                if (defaultConstructor != null) {
                    return defaultConstructor.newInstance();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
