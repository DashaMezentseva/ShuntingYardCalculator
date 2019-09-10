package my_calculator.translator.expressions;

public interface Token {

    enum Type {CONSTANT, OPERAND, OPERATION, FUNCTION, BRACKET}

    Type getType();

    Double getValue();

    @Override
    String toString();
}
