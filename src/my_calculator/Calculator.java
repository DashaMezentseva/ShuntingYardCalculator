package my_calculator;

import my_calculator.translator.Translator;
import my_calculator.translator.expressions.Token;
import my_calculator.translator.expressions.FunctionToken;
import my_calculator.translator.expressions.OperandToken;
import my_calculator.translator.expressions.OperationToken;

import java.util.ArrayList;
import java.util.Stack;

public class Calculator {

    private String infixExpression;
    private ArrayList<Token> postfixExpression = new ArrayList<>();
    private Stack<Token> operands = new Stack<>();
    private double result = 0.0;

    Calculator() {
        infixExpression = "";
    }

    Calculator(String expression) {
        infixExpression = expression.toLowerCase();
    }

    public String getInfixExpression() {
        return infixExpression;
    }

    public void setInfixExpression(String infixExpression) {
        this.infixExpression = infixExpression.toLowerCase();
    }

    public String getPostfixExpression() {
        return postfixExpression.toString();
    }

    public double getResult() {
        return result;
    }

    public void calculate() {
        Translator translator = new Translator(infixExpression);
        postfixExpression = translator.translateToPostfixNotation();

        for (Token token : postfixExpression)
            processToken(token);

        result = operands.peek().getValue(); //Результат вычислений лежит на вершине стека
    }

    private void processToken(Token token) {
        Token.Type tokenType = token.getType();
        if (tokenType == Token.Type.OPERAND) {
            operands.push(token);
        } else if (tokenType == Token.Type.OPERATION) {
            executeOperation(token);
        } else if (tokenType == Token.Type.FUNCTION) {
            executeFunction(token);
        }
    }

    private void executeOperation(Token expr) {
        if (expr.getType() == Token.Type.OPERATION) {
            OperationToken o = (OperationToken) expr;
            int parametrCount = o.getParametresCount();

            o.setParametres(getParametres(parametrCount));

            operands.push(new OperandToken(o.getValue()));

        } else {
            System.err.println("Входное выражение " + infixExpression + " некорректно");
            System.exit(1);
        }
    }

    private void executeFunction(Token expr) {
        if (expr.getType() == Token.Type.FUNCTION) {
            FunctionToken o = (FunctionToken) expr;
            int parametrCount = o.getParametresCount();

            o.setParametres(getParametres(parametrCount));

            operands.push(new OperandToken(o.getValue()));
        }
    }

    private Double[] getParametres(int count) {
        ArrayList<Double> parametres = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            if (!operands.empty()) {
                Double value = operands.pop().getValue();
                parametres.add(value);
            }
        }

        Double[] array = new Double[count];
        parametres.toArray(array);

        return array;
    }

    private boolean isContainsAsManyOperands(int count) {
        return count > 0 && operands.size() >= count;
    }
}
