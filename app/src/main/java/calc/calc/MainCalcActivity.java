package calc.calc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import calc.calc.calc.Calc;
import calc.calc.calc.exceptions.CalcException;
import calc.calc.util.SystemUiHider;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class MainCalcActivity extends Activity implements android.view.View.OnClickListener {
    @Override
    public void onClick(View v) {
        if (!v.equals(bCalc)) {
            return;
        }

        try {
            double res = calc.calcExp(tExpression.getText().toString());
            tResult.setText(Double.toString(res));
        } catch (CalcException e) {
            tResult.setText(e.toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main_calc);

        calc = new Calc();

        bCalc = (Button)findViewById(R.id.bCalc);
        tExpression = (TextView)findViewById(R.id.tExpression);
        tResult = (TextView)findViewById(R.id.tResult);

        bCalc.setOnClickListener(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    private Calc calc;

    private Button bCalc;
    private TextView tExpression;
    private TextView tResult;
}
