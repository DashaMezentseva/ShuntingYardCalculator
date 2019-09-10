package my_calculator.translator;

import my_calculator.translator.expressions.*;

import java.util.ArrayList;

class Tokenizer {

    private enum State {NONE, UNDEFINED, NUMBER, WORD, BRACKET}

    private ArrayList<Token> tokens = new ArrayList<>();
    private StringBuilder tokenName = new StringBuilder();

    private boolean wasThereRadixPoint = false; // Был ли уже десятичный разделитель
    private boolean isParametr = false; // Является ли данное выражение параметром функции
    private State state = State.NONE;

    private String inputExpression;

    Tokenizer(String expr) {
        inputExpression = expr;
    }

    ArrayList<Token> getTokens() {
        for (int i = 0; i < inputExpression.length(); ++i) {
            Character c = inputExpression.charAt(i);

            if (switchState(c))
                tokenName.append(c);
        }

        addToken();

        return tokens;
    }

    private boolean switchState(Character c) {
        if (Character.isDigit(c)) {
            processDigit();
            return true;
        } else if (c.equals('.')) {
            if (state == State.NUMBER && !wasThereRadixPoint) {
                wasThereRadixPoint = true;
                return true;
            } else {
                System.err.println("Лишняя точка");
                return false;
            }
        } else if (Character.isLetter(c)) {
            if (state != State.WORD) {
                addToken();
                state = State.WORD;
            }
            return true;
        } else if (c.equals(',')) {
            if (isParametr) {
                addToken();
            } else System.err.println("Лишняя запятая");

            return false;
        } else if (c.equals('(') || c.equals(')')) {
            processBracket(c);
            return true;
        } else if (c.equals(' ')) {
            addToken();
            state = State.NONE;
            return false;
        } else {
            if (state != State.UNDEFINED) {
                addToken();
                state = State.UNDEFINED;
            }
            return true;

        }
    }

    private void processDigit() {
        if (state != State.NUMBER) {
            addToken();
            state = State.NUMBER;
        }
    }

    private void processBracket(Character c) {
        BracketToken.BracketType bracketType = TokenFactory.getBracketTypeByLiteral(c);

        if (bracketType == BracketToken.BracketType.OPEN && state == State.WORD) {
            String token = tokenName.toString();
            if (TokenFactory.getExpressionType(token) == TokenFactory.ExpressionType.FUNCTION) {
                addToken();
                isParametr = true;
            } else {
                System.err.println("Скобка после константы");
            }
        } else {
            addToken();

            if (isParametr && bracketType == BracketToken.BracketType.CLOSE)
                isParametr = false;
        }

        state = State.BRACKET;
    }

    private void addToken() {
        if (tokenName.toString().isEmpty())
            return;

        String value = tokenName.toString();
        tokenName = new StringBuilder();
        TokenFactory.ExpressionType type = TokenFactory.getExpressionType(value);

        switch (state) {
            case NUMBER:
                tokens.add(new OperandToken(value));
                break;
            case WORD:
                if (type == TokenFactory.ExpressionType.CONSTANT)
                    tokens.add(new ConstantToken(value));
                else if (type == TokenFactory.ExpressionType.FUNCTION)
                    tokens.add(new FunctionToken(value));
                else System.err.println("Неизвестное слово");
                break;
            case BRACKET:
                tokens.add(new BracketToken(value));
                break;
            case UNDEFINED:
                if (type == TokenFactory.ExpressionType.OPERATION)
                    tokens.add(new OperationToken(value));
                else
                    System.err.println("Неизвестное выражение");
                break;
        }
    }
}
