package calc.calc.calc.exceptions;

/**
 * Created by Андрей on 09.10.2015.
 */
public class CalcException extends Exception {
    @Override
    public String toString() {
        return desc;
    }

    public CalcException(String desc) {
        super(desc);
        this.desc = desc;
    }

    private String desc;
}