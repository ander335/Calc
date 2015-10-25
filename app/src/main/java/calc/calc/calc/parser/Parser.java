package calc.calc.calc.parser;

import java.util.Vector;

import calc.calc.calc.exceptions.CalcException;
import calc.calc.calc.lexer.Tokenizer;
import calc.calc.calc.parser.operations.StatementDivision;
import calc.calc.calc.parser.operations.StatementMultiplication;
import calc.calc.calc.parser.operations.StatementRemainder;
import calc.calc.calc.parser.operations.StatementSubtraction;
import calc.calc.calc.parser.operations.StatementSum;

/**
 * Created by Андрей on 09.10.2015.
 */
public class Parser {
    public StatementBase parse(Vector<Tokenizer.Token> toks) throws CalcException {
        init(toks);

        return parseExp(0, parseValue());
    }

    private StatementBase parseValue() throws CalcException {
        switch (tok.type) {
            case Tok_Num:
                StatementBase sNum = new StatementNum(tok.value);
                nextTok();
                return sNum;
            case Tok_L_Par:
                nextTok();
                StatementBase sExp = parseExp(0, parseValue());
                if (tok.type != Tokenizer.TokenType.Tok_R_Par) {
                    throw new CalcException("tokenizer missed error with expected token right parenthesis");
                }
                nextTok();
                return sExp;
            default:
                throw new CalcException("unexpected token in position - %d, expected number of left parenthesis");
        }
    }

    private StatementBase parseExp(int initPriority, StatementBase sL) throws CalcException {
        while (true) {
            int priority = Tokenizer.getPriority(tok.type);
            if (priority < initPriority) {
                return sL;
            }

            Tokenizer.TokenType oper = tok.type;
            nextTok();

            StatementBase sR = parseValue();

            if (priority < Tokenizer.getPriority(tok.type)) {
                sR = parseExp(priority + 1, sR);
            }
            sL = createBinOper(oper, sL, sR);
        }
    }

    private StatementBase createBinOper(Tokenizer.TokenType type, StatementBase l, StatementBase r) throws CalcException {
        switch (type) {
            case Tok_Plus:
                return new StatementSum(l, r);
            case Tok_Minus:
                return new StatementSubtraction(l, r);
            case Tok_Star:
                return new StatementMultiplication(l, r);
            case Tok_Slash:
                return new StatementDivision(l, r);
            case Tok_Percent:
                return new StatementRemainder(l, r);
            default:
                throw new CalcException("tokenizer missed error with expected token operation");
        }
    }

    private void init(Vector<Tokenizer.Token> toks) {
        this.toks = toks;
        pos = 0;
        tok = toks.get(0);
    }

    private void nextTok() {
        pos++;

        if (notEmpty()) {
            tok = toks.get(pos);
        }
    }

    private boolean notEmpty() {
        return pos < toks.size();
    }

    private int pos;
    private Tokenizer.Token tok;
    private Vector<Tokenizer.Token> toks;
}
