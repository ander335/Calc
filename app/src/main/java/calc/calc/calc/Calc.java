package calc.calc.calc;

import java.util.Vector;

import calc.calc.calc.exceptions.CalcException;
import calc.calc.calc.lexer.Tokenizer;
import calc.calc.calc.parser.Parser;
import calc.calc.calc.parser.StatementBase;

/**
 * Created by Андрей on 09.10.2015.
 */
public class Calc {
    public Calc() {
        tokenizer = new Tokenizer();
        parser = new Parser();
    }

    public double calcExp(String exp) throws CalcException {
        if (exp == null || exp.isEmpty()) {
            throw new CalcException("input the expression");
        }

        Vector<Tokenizer.Token> streamToks = tokenizer.Tokenize(exp);
        StatementBase sExpression = parser.parse(streamToks);

        return sExpression.calc();
    }

    private Tokenizer tokenizer;
    private Parser parser;
}
