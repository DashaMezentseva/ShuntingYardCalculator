package my_calculator.translator.expressions;

import java.util.HashMap;

public class TokenFactory {

    public enum ExpressionType {NONE, CONSTANT, OPERATION, FUNCTION, BRACKET}

    private static final HashMap<String, ConstantToken.ConstantType> constants = new HashMap<String, ConstantToken.ConstantType>() {{
        put("e", ConstantToken.ConstantType.E);
        put("pi", ConstantToken.ConstantType.PI);
    }};

    private static final HashMap<String, OperationToken.OperationType> operations = new HashMap<String, OperationToken.OperationType>() {{
        put("+", OperationToken.OperationType.PLUS);
        put("-", OperationToken.OperationType.MINUS);
        put("*", OperationToken.OperationType.MULTIPLICATION);
        put("/", OperationToken.OperationType.DIVISION);
        put("^", OperationToken.OperationType.POWER);
    }};

    private static final HashMap<String, FunctionToken.FunctionType> functions = new HashMap<String, FunctionToken.FunctionType>() {{
        put("sin", FunctionToken.FunctionType.SIN);
        put("cos", FunctionToken.FunctionType.COS);
        put("tan", FunctionToken.FunctionType.TAN);
        put("tg", FunctionToken.FunctionType.TAN);
        put("cotan", FunctionToken.FunctionType.COTAN);
        put("cot", FunctionToken.FunctionType.COTAN);
        put("ctg", FunctionToken.FunctionType.COTAN);
        put("pow", FunctionToken.FunctionType.POW);
    }};

    private static final HashMap<Character, BracketToken.BracketType> brackets = new HashMap<Character, BracketToken.BracketType>() {{
        put('(', BracketToken.BracketType.OPEN);
        put(')', BracketToken.BracketType.CLOSE);
    }};

    static public OperationToken.OperationType getOperationTypeByLiteral(String literal) {
        if (operations.containsKey(literal))
            return operations.get(literal);
        else
            System.err.println("Переданная операция не подерживается " + literal);

        return OperationToken.OperationType.NONE;
    }

    static public FunctionToken.FunctionType getFunctionTypeByLiteral(String literal) {
        if (functions.containsKey(literal))
            return functions.get(literal);
        else
            System.err.println("Переданная функция не подерживается " + literal);

        return FunctionToken.FunctionType.NONE;
    }

    static public ConstantToken.ConstantType getConstantTypeByLiteral(String literal) {
        if (constants.containsKey(literal))
            return constants.get(literal);
        else
            System.err.println("Переданная константа не подерживается " + literal);

        return ConstantToken.ConstantType.NONE;
    }

    static public BracketToken.BracketType getBracketTypeByLiteral(Character literal) {
        if (brackets.containsKey(literal))
            return brackets.get(literal);
        else
            System.err.println("Переданная скобка не подерживается " + literal);

        return BracketToken.BracketType.NONE;
    }

    static public ExpressionType getExpressionType(String literal) {
        if (operations.containsKey(literal))
            return ExpressionType.OPERATION;
        else if (functions.containsKey(literal))
            return ExpressionType.FUNCTION;
        else if (constants.containsKey(literal))
            return ExpressionType.CONSTANT;
        else if (brackets.containsKey(literal))
            return ExpressionType.BRACKET;

        return ExpressionType.NONE;
    }
}
