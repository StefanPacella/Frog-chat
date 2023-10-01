package it.project.chat.framework;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.Date;

import it.project.chat.framework.data.BusinessException;

public class UtilityDateandTime {

	public String convertDateForDbms(String data) throws ParseException {
		SimpleDateFormat server = new java.text.SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date d = sdf.parse(data);
		return server.format(d);
	}

	public String convertDateForUser(String data) throws ParseException {
		SimpleDateFormat user = new java.text.SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date d = sdf.parse(data);
		return user.format(d);
	}

	public long xDateTimeDiffyDateTime(String xDate, String xTime, String yDate, String yTime) throws ParseException {
		xDate = convertDateForDbms(xDate);
		yDate = convertDateForDbms(yDate);
		Date xD = convertDateToDate(xDate);
		Date yD = convertDateToDate(yDate);

		Time xT = convertStringToTime(xTime);
		Time yT = convertStringToTime(yTime);

		return (xD.getTime() + xT.getTime()) - (yD.getTime() + yT.getTime());
	}

	public boolean dataABeforeDateB(String dateA, String dateB) throws ParseException {
		dateA = convertDateForDbms(dateA);
		dateB = convertDateForDbms(dateB);
		if (convertDateToDate(dateA).before(convertDateToDate(dateB)))
			return true;
		return false;
	}


	public Date convertDateToDate(String data) throws ParseException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.parse(data);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.parse(data);
		}
	}


	public Time convertStringToTime (String ora) {
		try {
			return Time.valueOf(ora + ":00");
		} catch (NumberFormatException e) {
			// TODO: handle exception
			return Time.valueOf(ora);
		}
	}

	public void checkTheTimeFormat(String ora) throws BusinessException {
		try {
			Time.valueOf(ora);
		} catch (DateTimeParseException e) {
			// TODO: handle exception
			// TODO: handle exception
			throw new BusinessException();
		}
	}

}