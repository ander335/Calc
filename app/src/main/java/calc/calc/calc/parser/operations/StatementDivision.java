package calc.calc.calc.parser.operations;

import calc.calc.calc.exceptions.CalcException;
import calc.calc.calc.parser.StatementBase;

/**
 * Created by Андрей on 09.10.2015.
 */
public class StatementDivision extends StatementBinaryOperation {
    public StatementDivision(StatementBase l, StatementBase r) {
        super(l, r);
    }

    @Override
    public double calc() throws CalcException {
        double lVal = l.calc();
        double rVal = r.calc();

        if (rVal == 0) {
            throw new CalcException("division by zero");
        }
        return lVal / rVal;
    }
}
