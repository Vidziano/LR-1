package Task5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class MethodProfiler implements InvocationHandler {
    private final Object target;

    public MethodProfiler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.nanoTime();
        Object result = method.invoke(target, args);
        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        String functionName = getFunctionName(target);
        String message = functionName + ": ";
        if (args != null && args.length > 0) {
            message += method.getName() + "(" + args[0] + ") = " + result;
        } else {
            message += result;
        }
        System.out.println(message);

        System.out.println("[" + functionName + "]." + method.getName() + " took " + duration + " ns");

        return result;
    }

    private String getFunctionName(Object function) {
        if (function instanceof Function1) {
            return "[Exp(-|2.5| * x) * sin(x)]";
        } else if (function instanceof Function2) {
            return "[x * x]";
        }
        return "";
    }
}

