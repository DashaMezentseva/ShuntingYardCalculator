package my_calculator.translator.expressions;

public class ConstantToken implements Token {

    enum ConstantType {NONE, E, PI}

    ConstantType type = ConstantType.NONE;

    public ConstantToken(String literal) {
        type = TokenFactory.getConstantTypeByLiteral(literal);
    }

    @Override
    public Type getType() {
        return Type.CONSTANT;
    }

    @Override
    public Double getValue() {
        switch (type) {
            case E:
                return StrictMath.E;
            case PI:
                return StrictMath.PI;
        }

        return 0.0;
    }

    @Override
    public String toString() {
        return getClass().getName() + " " + type;
    }
}
