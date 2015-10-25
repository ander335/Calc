package calc.calc.calc.parser;

import calc.calc.calc.exceptions.CalcException;

/**
 * Created by Андрей on 09.10.2015.
 */
public abstract class StatementBase {
    public abstract double calc() throws CalcException;
}
