package infocarbon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

public class DateLabelFormatter extends AbstractFormatter {
	
	private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	@Override
	public Object stringToValue(String text) throws ParseException {
		return formatter.parseObject(text);
	}

	@Override
	public String valueToString(Object value) throws ParseException {
		if (value != null) {
            Calendar cal = (Calendar) value;
            return formatter.format(cal.getTime());
        }

        return "";
    }

}
