package me.g13n.tipcalculator;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TipEntry extends Activity {
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_entry);
    }
    
    public void onTenPercentClick(View v) {
    	try {
    		this.setTipValue(TipCalculator.getTipValue(this.getTotalValue(), 10));
    	} catch (NumberFormatException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	} catch (NegativeValueException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	}
    	this.clearTipPercent();
	}
    
    public void onFifteenPercentClick(View v) {
    	try {
    		this.setTipValue(TipCalculator.getTipValue(this.getTotalValue(), 15));
    	} catch (NumberFormatException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	} catch (NegativeValueException ex) {
    		Toast.makeText(getBaseContext(), R.string.invalid_input, Toast.LENGTH_SHORT).show();
    	}
    	this.clearTipPercent();
	}
    
    public void onTwentyPercentClick(View v) {
    	try {
    		this.setTipValue(TipCalculator.getTipValue(this.getTotalValue(), 20));
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
    	String tipValue = String.format(getString(R.string.currency), tip);
    	lblTipValue.setText(tipValue);
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
}

final class NegativeValueException extends Exception {
	private static final long serialVersionUID = 1L;
}
