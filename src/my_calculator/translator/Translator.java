package my_calculator.translator;

import my_calculator.translator.expressions.*;

import java.util.ArrayList;
import java.util.Stack;

public class Translator {

    private String inputExpression = "";
    private Stack<Token> operators = new Stack<>();
    private ArrayList<Token> outputExpression = new ArrayList<>();

    public Translator(String expression) {
        inputExpression = expression;
    }

    public ArrayList<Token> translateToPostfixNotation() {
        outputExpression = new ArrayList<>();

        Tokenizer tokenizer = new Tokenizer(inputExpression);
        ArrayList<Token> tokens = tokenizer.getTokens();

        for (Token token : tokens) processToken(token);

        while (!operators.empty())
            outputExpression.add(operators.pop());

        return outputExpression;
    }

    private void processToken(Token token) {
        Token.Type tokenType = token.getType();

        if (tokenType == Token.Type.OPERAND) {
            outputExpression.add(token);
        } else if (tokenType == Token.Type.CONSTANT) {
            Double value = token.getValue();

            outputExpression.add(new OperandToken(value));
        } else if (tokenType == Token.Type.OPERATION) {
            if (token instanceof OperationToken) {
                OperationToken oToken = (OperationToken) token;
                Computational topOperation = (Computational) operators.peek();

                if (oToken.getPriority() < topOperation.getPriority())
                    outputExpression.add(operators.pop());
            }
            operators.push(token);
        } else if (tokenType == Token.Type.FUNCTION) {
            operators.push(token);
        } else if (tokenType == Token.Type.BRACKET) {
            BracketToken bracket = (BracketToken) token;
            if (bracket.getBracketType() == BracketToken.BracketType.OPEN)
                operators.push(token);
            else if (bracket.getBracketType() == BracketToken.BracketType.CLOSE)
                processCloseBracketToken();
        }
    }

    private void processCloseBracketToken() {
        Token top = operators.pop();
        while (!isOpenBracketToken(top)) {
            if (operators.empty()) {
                System.err.println("Входное выражение " + inputExpression + " некорректно");
                System.exit(1);
            }

            outputExpression.add(top);
            top = operators.pop();
        }
    }

    private boolean isOpenBracketToken(Token token) {
        if (token instanceof BracketToken) {
            BracketToken bracket = (BracketToken) token;

            return bracket.getBracketType() == BracketToken.BracketType.OPEN;
        }

        return false;
    }
}
