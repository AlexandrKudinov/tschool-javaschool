package com.tsystems.javaschool.tasks.calculator;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class Calculator {
    private static char[] operators = "()+-*/".toCharArray();
    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement) {
        if (statement == null || statement.isEmpty()) return null;
        char[] statementToChars = statement.toCharArray();
        if (!verify(statementToChars)) return null;
        Number result = calculating(toReversePolishNotation(statementToChars));
        return (result == null) ? null : String.valueOf(result);
    }

    private boolean verify(char[] statementToChars) {
        int open = 0;
        int close = 0;
        for (int i = 0; i < statementToChars.length; i++) {
            if (!Character.isDigit(statementToChars[i]) && !isOperator(statementToChars[i]) && statementToChars[i] != '.') {
                return false;
            }
            if (statementToChars[i] == '(') {
                open++;
            }
            if (statementToChars[i] == ')') {
                close++;
            }
            if (statementToChars[i] != ')' && isOperator(statementToChars[i]) && i < statementToChars.length - 1 &&
                    (statementToChars[i + 1] != '(' && !Character.isDigit(statementToChars[i + 1]))) {
                return false;
            }
            if (statementToChars[i] == '.' && i < statementToChars.length - 1 &&
                    (!isOperator(statementToChars[i + 1]) && !Character.isDigit(statementToChars[i + 1]))) {
                return false;
            }
        }
        return open == close;
    }

    private boolean isOperator(char symbol) {
        for (char operator : operators) {
            if (operator == symbol) {
                return true;
            }
        }
        return false;
    }

    private byte getPriority(char symbol) {
        switch (symbol) {
            case '(':
                return 0;
            case ')':
                return 1;
            case '+':
                return 2;
            case '-':
                return 3;
            case '*':
                return 4;
            case '/':
                return 5;
            default:
                return 6;
        }
    }

    private List<Character> toReversePolishNotation(char[] statementToChars) {
        Stack<Character> operators = new Stack<>();
        List<Character> result = new LinkedList<>();

        for (int i = 0; i < statementToChars.length; i++) {
            if (Character.isDigit(statementToChars[i]) || statementToChars[i] == '.') {
                while (!isOperator(statementToChars[i])) {
                    result.add(statementToChars[i++]);
                    if (i == statementToChars.length) {
                        result.add(' ');
                        i--;
                        break;
                    }
                }
                result.add(' ');
            }
            if (isOperator(statementToChars[i])) {
                if (statementToChars[i] == '(') {
                    operators.push(statementToChars[i]);
                } else if (statementToChars[i] == ')') {
                    char symbol = operators.pop();
                    while (symbol != '(') {
                        result.add(symbol);
                        result.add(' ');
                        symbol = operators.pop();
                    }
                } else {
                    if (operators.size() > 0) {
                        if (getPriority(statementToChars[i]) < getPriority(operators.peek())) {
                            result.add(operators.pop());
                            result.add(' ');
                        }
                    }
                    operators.push(statementToChars[i]);
                }
            }
        }

        while (operators.size() > 0) {
            result.add(operators.pop());
            result.add(' ');
        }
        return result;
    }

    private Number calculating(List<Character> list) {
        double result = 0;
        Stack<Double> tmp = new Stack<>();

        for (int i = 0; i < list.size(); i++) {
            if (Character.isDigit(list.get(i)) || list.get(i) == '.') {
                StringBuilder stringBuilder = new StringBuilder();
                while (list.get(i) != ' ') {
                    stringBuilder.append(list.get(i++));
                }
                tmp.push(Double.parseDouble(stringBuilder.toString()));
                i--;
            } else if (isOperator(list.get(i))) {
                double a = tmp.pop();
                double b = tmp.pop();

                switch (list.get(i)) {
                    case '+':
                        result = b + a;
                        break;
                    case '-':
                        result = b - a;
                        break;
                    case '*':
                        result = b * a;
                        break;
                    case '/':
                        if (a == 0) {
                            return null;
                        }
                        result = b / a;
                        break;
                }
                tmp.push(result);
            }
        }

        if (tmp.peek() == null) {
            return tmp.peek();
        } else if (tmp.peek() % 1 == 0) {
            return tmp.peek().intValue();
        } else return (double) Math.round(tmp.peek() * 10000d) / 10000d;
    }

}
