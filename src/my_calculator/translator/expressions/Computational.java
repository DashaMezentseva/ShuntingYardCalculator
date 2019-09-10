package my_calculator.translator.expressions;

public interface Computational extends Token {

    void setParametres(Double[] parametres);

    int getParametresCount();

    int getPriority();
}
