package calc.calc.calc.parser.operations;

import calc.calc.calc.parser.StatementBase;

/**
 * Created by Андрей on 09.10.2015.
 */
public abstract class StatementBinaryOperation extends StatementBase {
    public StatementBinaryOperation(StatementBase l, StatementBase r) {
        this.l = l;
        this.r = r;
    }

    protected StatementBase l;
    protected StatementBase r;
}
