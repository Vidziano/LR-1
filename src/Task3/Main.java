package Task3;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Arrays;


class FunctionNotFoundException extends Exception {
    public FunctionNotFoundException(String message) {
        super(message);
    }
}

public class Main {
    public static void callMethod(Object obj, String methodName, List<Object> params) throws FunctionNotFoundException {
        Class<?>[] paramTypes = new Class<?>[params.size()];
        Object[] paramValues = new Object[params.size()];
        for (int i = 0; i < params.size(); i++) {
            paramTypes[i] = getPrimitiveType(params.get(i).getClass());
            paramValues[i] = params.get(i);
        }

        try {
            Method method = obj.getClass().getMethod(methodName, paramTypes);
            System.out.println(obj);
            System.out.println("Типи: " + Arrays.toString(paramTypes) + ", значення: " + params);
            Object result = method.invoke(obj, paramValues);
            System.out.println("Результат виклику: " + result);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new FunctionNotFoundException("Функція не знайдена: " + e.getMessage());
        }
    }


    private static Class<?> getPrimitiveType(Class<?> type) {
        if (type == Integer.class) {
            return int.class;
        } else if (type == Double.class) {
            return double.class;
        } else if (type == Float.class) {
            return float.class;
        } else if (type == Long.class) {
            return long.class;
        } else if (type == Short.class) {
            return short.class;
        } else if (type == Byte.class) {
            return byte.class;
        } else if (type == Character.class) {
            return char.class;
        }
        return type;
    }

    public static void main(String[] args) {
        TestClass test = new TestClass();
        try {
            callMethod(test, "method", List.of(1.0));
            callMethod(test, "method", List.of(1.0, 1));
        } catch (FunctionNotFoundException e) {
            e.printStackTrace();
        }
    }
}

class TestClass {
    public double method(double a) {
        return Math.exp(-Math.abs(a) * a) * Math.sin(a);
    }

    public double method(double a, int b) {
        return Math.exp(-Math.abs(a) * a) * Math.sin(a * b);
    }

    @Override
    public String toString() {
        return "TestClass [a=1.0, exp(-abs(a)*x)*sin(x)]";
    }
}
