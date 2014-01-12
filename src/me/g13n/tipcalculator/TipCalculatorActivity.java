package me.g13n.tipcalculator;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TipCalculatorActivity extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calculator);
    }
    
    public void onQuickTipClick(View v) {
    	try {
    		this.setTipValue(TipCalculator.getTipValue(this.getTotalValue(), (double)Double.parseDouble((String)v.getTag())));
    	} catch (NumberFormatException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	} catch (NegativeValueException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	}
    	this.clearTipPercent();
	}
    
    public void onCalculateClick(View v) {
    	try {
    		this.setTipValue(TipCalculator.getTipValue(this.getTotalValue(), this.getTipPercent()));
    	} catch (NegativeValueException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	} catch (NumberFormatException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	}
	}
    
    protected double getTotalValue() {
    	EditText etTotal = (EditText) findViewById(R.id.etAmount);
    	return Double.parseDouble(etTotal.getText().toString());
    }
    
    protected double getTipPercent() {
    	EditText etTip = (EditText) findViewById(R.id.etDifferentTip);
    	return Double.parseDouble(etTip.getText().toString());
    }
    
    protected void setTipValue(double tip) {
    	TextView lblTipValue = (TextView) findViewById(R.id.lblTipValue);
    	lblTipValue.setText(TipCalculator.formatCurrency(tip));
    }
    
    protected void clearTipPercent() {
    	EditText etTip = (EditText) findViewById(R.id.etDifferentTip);
    	etTip.setText("");
    }
    
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
