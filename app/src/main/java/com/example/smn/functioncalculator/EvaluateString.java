package com.example.smn.functioncalculator;

import java.util.Stack;

/**
 * Created by ashkan on 12/26/16.
 */

public class EvaluateString {

    public Double evaluate(String expression) {
        char[] tokens = expression.toCharArray();

        Stack<Double> values = new Stack<>();

        Stack<Character> opr = new Stack<>();

        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i] == ' ')
                continue;

            if (tokens[i] >= '0' && tokens[i] <= '9') {
                StringBuilder digit = new StringBuilder();

                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    digit.append(tokens[i++]);

                values.push(Double.parseDouble(digit.toString()));
            }

            else if (tokens[i] == '(')
                opr.push(tokens[i]);


            else if (tokens[i] == ')') {
                while (opr.peek() != '(')
                    values.push(applyOp(opr.pop(), values.pop(), values.pop()));
                opr.pop();
            }


            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/' || tokens[i] == '^') {


                while (!opr.empty() && hasPrecedence(tokens[i], opr.peek()))
                    values.push(applyOp(opr.pop(), values.pop(), values.pop()));

                opr.push(tokens[i]);
            }
            else if (tokens[i] == '#' || tokens[i] == '%' || tokens[i] == '$' || tokens[i] == '@')
            {
                while (!opr.empty())
                    values.push(applyOp(opr.pop(), values.pop()));

                opr.push(tokens[i]);
            }
        }

        while (!opr.empty())
            values.push(applyOp(opr.pop(), values.pop(), values.pop()));


        return values.pop();
    }



    // Returns true if 'op2' has higher or same precedence as 'op1',
    public boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/' || op1 == '^') && (op2 == '+' || op2 == '-'))
            return false;
        if (op1 == '^' && (op2 == '/' || op2 == '*'))
            return false;
        else
            return true;
    }


    public double applyOp(char op, double b, double a) {
        switch (op) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Cannot divide by zero");
                return a / b;
            case '^':
                return Math.pow(a, b);
        }
        return 0;
    }

    public double applyOp(char op, double a) {
        switch (op) {
            case '#':
                return Math.sin(a);
            case '$':
                return Math.cos(a);
            case '@':
                return Math.tan(a);
            case '%':
                return Math.atan(a);
            case '`':
                return Math.abs(a);
        }
        return 0;
    }
}
