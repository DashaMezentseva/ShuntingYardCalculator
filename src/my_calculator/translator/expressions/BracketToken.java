package my_calculator.translator.expressions;

public class BracketToken implements Token {

    public enum BracketType {NONE, OPEN, CLOSE}

    private BracketType bracketType = BracketType.NONE;

    public BracketToken(String c) {
        setTypesByLiteral(c);
    }

    @Override
    public Token.Type getType() {
        return Token.Type.BRACKET;
    }

    @Override
    public Double getValue() {
        return null;
    }

    @Override
    public String toString() {
        return bracketType.toString();
    }

    public BracketType getBracketType() {
        return bracketType;
    }

    private void setTypesByLiteral(String literal) {
        switch (literal) {
            case "(":
                bracketType = BracketType.OPEN;
                break;
            case ")":
                bracketType = BracketType.CLOSE;
                break;
            default:
                System.err.println("Неподдерживаемый тип скобок " + literal);
        }
    }
}
