package calc.calc.calc.parser.operations;

import calc.calc.calc.exceptions.CalcException;
import calc.calc.calc.parser.StatementBase;

/**
 * Created by Андрей on 09.10.2015.
 */
public class StatementSubtraction extends StatementBinaryOperation {
    public StatementSubtraction(StatementBase l, StatementBase r) {
        super(l, r);
    }

    @Override
    public double calc() throws CalcException {
        return l.calc() - r.calc();
    }
}
