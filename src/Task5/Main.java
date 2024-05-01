package Task5;


import java.lang.reflect.Proxy;
public class Main {
    public static void main(String[] args) {
        Evaluatable f1 = new Function1();
        Evaluatable f2 = new Function2();

        Evaluatable proxyF1 = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new MethodProfiler(f1)
        );

        Evaluatable proxyF2 = (Evaluatable) Proxy.newProxyInstance(
                Evaluatable.class.getClassLoader(),
                new Class<?>[]{Evaluatable.class},
                new MethodProfiler(f2)
        );

        double x = 1.0;
        System.out.println("F1: " + proxyF1.eval(x));
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("F2: " + proxyF2.eval(x));
    }
}