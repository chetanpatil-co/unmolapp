package com.chetan.unmolapp.Util;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CustomDateDeserializer implements  JsonDeserializer<Date>
{

	@Override
	public Date deserialize(JsonElement json, Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
		
		String s  =json.getAsString();
		
		
		long timeInMilliseconds = 0;
		try {
			timeInMilliseconds = json.getAsLong();
		} catch (Exception e) {
			e.printStackTrace();
			SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return	fmt.parse(s);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
				
			}
			
		}

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMilliseconds);

        return calendar.getTime();
	}

}


