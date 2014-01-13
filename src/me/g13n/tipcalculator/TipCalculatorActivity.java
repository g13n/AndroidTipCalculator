package me.g13n.tipcalculator;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class TipCalculatorActivity extends Activity implements OnSeekBarChangeListener {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);

    	SeekBar sbDiffTip = (SeekBar)findViewById(R.id.sbDifferentTip);
    	sbDiffTip.setOnSeekBarChangeListener(this);
    	
    	this.progressPercent = getString(R.string.percent);
    }
    
    public void onQuickTipClick(View v) {
    	try {
    		this.setTipAndTotal(TipCalculator.getTipValue(this.getTotalValue(), (double)Double.parseDouble((String)v.getTag())));
    	} catch (NumberFormatException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	} catch (NegativeValueException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	}
	}
    
    protected double getTotalValue() {
    	EditText etTotal = (EditText) findViewById(R.id.etAmount);
    	this.total = Double.parseDouble(etTotal.getText().toString());
    	return this.total;
    }
    
    protected void setTipAndTotal(double tip) {
    	TextView lblTipValue = (TextView) findViewById(R.id.lblTipValue);
    	lblTipValue.setText(TipCalculator.formatCurrency(tip));
    	TextView lblTotalValue = (TextView) findViewById(R.id.lblTotalValue);
    	lblTotalValue.setText(TipCalculator.formatCurrency(this.total + tip));
    }

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
    	TextView lblTipPercent = (TextView) findViewById(R.id.lblTipPercent);
    	lblTipPercent.setText(String.format(this.progressPercent, String.valueOf(progress)));
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
    	try {
    		this.setTipAndTotal(TipCalculator.getTipValue(this.getTotalValue(), seekBar.getProgress()));
    	} catch (NegativeValueException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	} catch (NumberFormatException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	}
	}
    
	private double total;
	private String progressPercent;
}

final class TipCalculator {
	public static double getTipValue(double total, double tip) throws NegativeValueException {
		if (total < 0.0 || tip < 0.0) {
			throw new NegativeValueException();
		}
		
		return total * tip / 100;
	}
	
	public static String formatCurrency(double value) {
		Currency c = Currency.getInstance(Locale.getDefault());
		NumberFormat cf = NumberFormat.getCurrencyInstance();
		cf.setCurrency(c);
		return cf.format(value);
	}
}

final class NegativeValueException extends Exception {
	private static final long serialVersionUID = 1L;
}
