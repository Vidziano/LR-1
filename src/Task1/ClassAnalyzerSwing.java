package Task1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.*;

public class ClassAnalyzerSwing {

    private JTextField classNameField;
    private JTextArea resultArea;

    public ClassAnalyzerSwing() {
        JFrame frame = new JFrame("Class Analyzer");
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Components
        JLabel promptLabel = new JLabel("Enter full class name:");
        classNameField = new JTextField(30);
        JButton analyzeButton = new JButton("Analyze");
        JButton clearButton = new JButton("Clear");
        JButton exitButton = new JButton("Exit");
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Add action listeners
        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeClass();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultArea.setText("");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(analyzeButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(promptLabel);
        inputPanel.add(classNameField);

        // Layout
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void analyzeClass() {
        String className = classNameField.getText();
        if (!className.isEmpty()) {
            resultArea.setText("");
            resultArea.append("Analyzing class: " + className + "\n\n");
            try {
                Class<?> clazz = Class.forName(className);
                printClassInfo(clazz);
                printFields(clazz);
                printConstructors(clazz);
                printMethods(clazz);
            } catch (ClassNotFoundException e) {
                resultArea.append("Class not found: " + e.getMessage() + "\n");
            }
        } else {
            resultArea.append("Please enter a class name.\n");
        }
    }

    private void printClassInfo(Class<?> clazz) {
        Package pkg = clazz.getPackage();
        resultArea.append("Package: " + pkg.getName() + "\n");

        int modifiers = clazz.getModifiers();
        resultArea.append(Modifier.toString(modifiers) + " class " + clazz.getSimpleName());

        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            resultArea.append(" extends " + superclass.getSimpleName());
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0) {
            resultArea.append(" implements ");
            for (int i = 0; i < interfaces.length; i++) {
                resultArea.append(interfaces[i].getSimpleName());
                if (i < interfaces.length - 1) {
                    resultArea.append(", ");
                }
            }
        }
        resultArea.append(" {\n\n");
    }

    private void printFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        resultArea.append("// Fields\n");
        for (Field field : fields) {
            resultArea.append(Modifier.toString(field.getModifiers()) + " " + field.getType().getSimpleName() + " " + field.getName() + ";\n");
        }
        resultArea.append("\n");
    }

    private void printConstructors(Class<?> clazz) {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        resultArea.append("// Constructors\n");
        for (Constructor<?> constructor : constructors) {
            resultArea.append(Modifier.toString(constructor.getModifiers()) + " " + clazz.getSimpleName() + "(");
            Parameter[] parameters = constructor.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                resultArea.append(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                if (i < parameters.length - 1) {
                    resultArea.append(", ");
                }
            }
            resultArea.append(");\n");
        }
        resultArea.append("\n");
    }

    private void printMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        resultArea.append("// Methods\n");
        for (Method method : methods) {
            resultArea.append(Modifier.toString(method.getModifiers()) + " " + method.getReturnType().getSimpleName() + " " + method.getName() + "(");
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                resultArea.append(parameters[i].getType().getSimpleName() + " " + parameters[i].getName());
                if (i < parameters.length - 1) {
                    resultArea.append(", ");
                }
            }
            resultArea.append(");\n");
        }
        resultArea.append("}\n\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ClassAnalyzerSwing();
            }
        });
    }
}
//java.lang.String