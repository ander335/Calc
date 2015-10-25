package calc.calc.calc.lexer;

import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import calc.calc.calc.exceptions.CalcException;

/**
 * Created by Андрей on 09.10.2015.
 */
public class Tokenizer {
    public enum TokenType {
        Tok_Num,
        Tok_L_Par,
        Tok_R_Par,
        Tok_Plus,
        Tok_Minus,
        Tok_Star,
        Tok_Slash,
        Tok_Percent,
        Tok_Dummy
    }

    public class Token {
        public Token(TokenType type) {
            this.type = type;
        }

        public Token(TokenType type, double value) {
            this.type = type;
            this.value = value;
        }

        public TokenType type;
        public double value;
    }

    public Vector<Token> Tokenize(String exp) throws CalcException {
        Init(exp);

        Vector<Token> toks = new Vector<Token>();

        while (notEmpty()) {
            switch (currChar) {
                case '(':
                    onOpenParenthesis();
                    currTok = new Token(TokenType.Tok_L_Par);
                    nextChar();
                    break;
                case ')':
                    onCloseParenthesis();
                    currTok = new Token(TokenType.Tok_R_Par);
                    nextChar();
                    break;
                case '+':
                    currTok = new Token(TokenType.Tok_Plus);
                    nextChar();
                    break;
                case '-':
                    currTok = new Token(TokenType.Tok_Minus);
                    nextChar();
                    break;
                case '*':
                    currTok = new Token(TokenType.Tok_Star);
                    nextChar();
                    break;
                case '/':
                    currTok = new Token(TokenType.Tok_Slash);
                    nextChar();
                    break;
                case '%':
                    currTok = new Token(TokenType.Tok_Percent);
                    nextChar();
                    break;
                default:
                    if (!Character.isDigit(currChar)) {
                        throw new CalcException(String.format("unexpected token in position - %d", pos));
                    }
                    currTok = new Token(TokenType.Tok_Num);
                    currTok.value = readNum();
            }
            applyCurrTok(toks);
        }

        currTok = new Token(TokenType.Tok_Dummy);
        applyCurrTok(toks);

        onEndTokenize();

        return toks;
    }

    public static int getPriority(TokenType type) {
        return priority.get(type).intValue();
    }

    public static Map<TokenType, Integer> priority;

    private void Init(String exp) {
        countOpenParenthesis = 0;
        this.exp = exp;
        pos = 0;
        currChar = exp.charAt(0);

        prevTok = new Token(TokenType.Tok_Dummy);
    }

    private void nextChar() {
        pos++;

        if (notEmpty()) {
            currChar = exp.charAt(pos);
        } else {
            currChar = 0;
        }
    }

    private boolean notEmpty() {
        return pos < exp.length();
    }

    private void checkTok() throws CalcException {
        if (!neighborsTok.get(prevTok.type).contains(currTok.type)) {
            throw new CalcException(String.format("unexpected token in position - %d", pos));
        }
    }

    private void applyCurrTok(Vector<Token> toks) throws CalcException {
        checkTok();
        toks.add(currTok);
        prevTok = currTok;
    }

    private double readNum() {
        double v = 0;

        while (Character.isDigit(currChar)) {
            v *= 10.0;
            v += Character.getNumericValue(currChar) - Character.getNumericValue('0');
            nextChar();
        }

        return v;
    }

    private void onEndTokenize() throws CalcException {
        if (countOpenParenthesis != 0) {
            throw new CalcException("expected token ')'");
        }
    }

    private void onOpenParenthesis() {
        countOpenParenthesis++;
    }

    private void onCloseParenthesis() throws CalcException {
        countOpenParenthesis--;

        if (countOpenParenthesis < 0) {
            throw new CalcException(String.format("unexpected token ')' in position - %d", pos));
        }
    }

    private int countOpenParenthesis;
    private int pos;
    private char currChar;
    private String exp;
    private Token prevTok;
    private Token currTok;

    private static Map<TokenType, Vector<TokenType>> neighborsTok;

    static {
        neighborsTok = new Hashtable<>();
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_R_Par);
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_Plus);
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_Minus);
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_Star);
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_Slash);
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_Percent);
        AddValidPair(TokenType.Tok_Num, TokenType.Tok_Dummy);

        AddValidPair(TokenType.Tok_L_Par, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_L_Par, TokenType.Tok_L_Par);

        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_R_Par);
        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_Plus);
        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_Minus);
        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_Star);
        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_Slash);
        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_Percent);
        AddValidPair(TokenType.Tok_R_Par, TokenType.Tok_Dummy);

        AddValidPair(TokenType.Tok_Plus, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_Plus, TokenType.Tok_L_Par);

        AddValidPair(TokenType.Tok_Minus, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_Minus, TokenType.Tok_L_Par);

        AddValidPair(TokenType.Tok_Star, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_Star, TokenType.Tok_L_Par);

        AddValidPair(TokenType.Tok_Slash, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_Slash, TokenType.Tok_L_Par);

        AddValidPair(TokenType.Tok_Percent, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_Percent, TokenType.Tok_L_Par);

        AddValidPair(TokenType.Tok_Dummy, TokenType.Tok_Num);
        AddValidPair(TokenType.Tok_Dummy, TokenType.Tok_L_Par);

        priority = new Hashtable<>();
        priority.put(TokenType.Tok_Plus, new Integer(1));
        priority.put(TokenType.Tok_Minus, new Integer(1));
        priority.put(TokenType.Tok_Star, new Integer(2));
        priority.put(TokenType.Tok_Slash, new Integer(2));
        priority.put(TokenType.Tok_Percent, new Integer(2));
        priority.put(TokenType.Tok_Dummy, new Integer(-1));
        priority.put(TokenType.Tok_R_Par, new Integer(-1));
    }

    private static void AddValidPair(TokenType l, TokenType r) {
        if (neighborsTok.containsKey(l)) {
            neighborsTok.get(l).add(r);
        } else {
            Vector<TokenType> vecR = new Vector<TokenType>();
            vecR.add(r);
            neighborsTok.put(l, vecR);
        }
    }
}
