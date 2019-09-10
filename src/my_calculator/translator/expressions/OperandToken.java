package my_calculator.translator.expressions;

public class OperandToken implements Token {

    private double value = 0;

    public OperandToken(String oValue) {
        value = Double.parseDouble(oValue);
    }

    public OperandToken(Double oValue) {
        value = oValue;
    }

    @Override
    public Type getType() {
        return Type.OPERAND;
    }

    @Override
    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getValue().toString();
    }
}
