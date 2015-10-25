package calc.calc.calc.parser;

/**
 * Created by Андрей on 09.10.2015.
 */
public class StatementNum extends StatementBase {
    public StatementNum(double v) {
        this.v = v;
    }
    @Override
    public double calc() {
        return v;
    }

    private double v;
}
