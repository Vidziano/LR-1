package Task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

public class ClassManager {
    private Class<?> cls;
    private Object classObject;
    public  static final BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public ClassManager(String cls) throws ClassNotFoundException {
        this.cls = Class.forName(cls);
    }

    public static void main(String[] args){
        ClassManager classManager;
        try {
            System.out.print("Введіть повне ім'я класу: ");
            String className = scanLine();
            classManager = new ClassManager(className);
            classManager.newInstance();

            while (true){
                classManager.showFields();
                classManager.chooseMethods();
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Клас не знайдено");
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchElementException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private Object getObjectByType(Class<?> classObject){
        if (classObject.isPrimitive()) {
            System.out.print("Enter " + classObject + " parameter: ");
            String stringParameter = scanLine();
            switch (classObject.getName()) {
                case "int":
                    return Integer.parseInt(stringParameter);
                case "double":
                    return Double.parseDouble(stringParameter);
                case "float":
                    return Float.parseFloat(stringParameter);
                case "boolean":
                    return Boolean.parseBoolean(stringParameter);
                case "java.lang.String":
                    return stringParameter;
                case "char":
                    return stringParameter.charAt(0);
                case "short":
                    return Short.parseShort(stringParameter);
                case "long":
                    return Long.parseLong(stringParameter);
            }
        }
        else
            try {
                System.out.println("\nСтворення об'єкта " + classObject + ": ");
                return createObject(classObject);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }

        return null;
    }


    private Object[] getParametersArray(Class<?>[] typeArray){
        Object [] parameterArray = new Object[typeArray.length];
        for (int i = 0; i < parameterArray.length; i++){
            parameterArray[i] = getObjectByType(typeArray[i]);
        }
        return parameterArray;
    }

    private Executable getConstructor(Executable[] constructorArray){
        for (int i = 0; i < constructorArray.length; i++)
            System.out.println("    " + (i + 1) + ") " + constructorArray[i]);

        System.out.print("Choose [1 - " + constructorArray.length + "]: ");
        int n = scan() - 1;
        while (n  >= constructorArray.length || n < 0){
            System.out.print("Choose [1 - " + constructorArray.length + "]: ");
            n = scan() - 1;
        }
        return constructorArray[n];
    }

    private Object createObject(Class<?> cls) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        System.out.println("Choose constructor: ");
        Constructor<?> constructor = (Constructor<?>) getConstructor(cls.getConstructors());

        Object [] parameters = getParametersArray(constructor.getParameterTypes());
        return constructor.newInstance(parameters);
    }

    private void chooseMethods() throws InvocationTargetException, IllegalAccessException {
        System.out.println("\nChoose method: ");
        Method method = (Method) getConstructor(classObject.getClass().getMethods());
        Object result;
        Object [] args = getParametersArray(method.getParameterTypes());
        result = method.invoke(classObject, args);
        System.out.println("Result = " + Optional.ofNullable(result).orElse("void"));
    }

    public void newInstance() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        classObject = createObject(cls);
    }

    public  void showFields(){
        Class<?> someClass = classObject.getClass();
        Field[] fields =  Optional.ofNullable(someClass.getDeclaredFields()).orElse(new Field[0]);
        System.out.println("Fields in class " + someClass.getSimpleName());
        Arrays.stream(fields).forEach(field -> {
            try {
                if(!field.isAccessible()){
                    field.setAccessible(true);
                }
                System.out.println(field.getType() + " " + field.getName() + " = " + field.get(classObject));
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }


    public Object getObject(){
        return classObject;
    }

    public static int scan(){
        int choice = 0;
        boolean flag = true;
        while (flag){
            flag = false;
            try {
                choice = Integer.parseInt(in.readLine());
            } catch (NumberFormatException | IOException e){
                flag = true;
                System.err.println("Incorrect number format");
            }
        }
        return choice;
    }

    public static String scanLine(){
        String inputChoice = "";
        try {
            inputChoice = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputChoice;
    }

}