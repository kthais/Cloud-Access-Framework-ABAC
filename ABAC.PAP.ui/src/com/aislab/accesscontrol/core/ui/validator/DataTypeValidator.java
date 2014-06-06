/******************************************************************************
 * Project:    Extensible Access Control Framework for Cloud based Applications.
 *                     http://ais.seecs.nust.edu.pk/project/ 
 * Developed by: KTH- Applied Information Security Lab (AIS), 
 *                       NUST-SEECS, H-12 Campus, 
 *                       Islamabad, Pakistan. 
 *                       www.ais.seecs.nust.edu.pk
 * Funded by: National ICT R&D Fund, Ministry of Information Technology & Telecom,
 *                  http://www.ictrdf.org.pk/
 * Copyright (c) 2013-2015 All Rights Reserved, AIS-SEECS NUST & National ICT R&D Fund

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy and/or modify the Software, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. 

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *****************************************************************************/

package com.aislab.accesscontrol.core.ui.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class DataTypeValidator implements Validator {

	/*
	 * private final static String EMAIL_PATTERN =
	 * "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
	 * ;
	 * 
	 * private final static Pattern EMAIL_COMPILED_PATTERN = Pattern
	 * .compile(EMAIL_PATTERN);
	 */

	private final static String INTEGER_PATTERN = "[-+]?[1-9][0-9]*|0";

	private final static Pattern INTEGER_COMPILED_PATTERN = Pattern
			.compile(INTEGER_PATTERN);

	private final static String STRING_PATTERN = "(([a-zA-Z])+([a-zA-Z0-9'_,\\-\" ])*)+";

	private final static Pattern STRING_COMPILED_PATTERN = Pattern
			.compile(STRING_PATTERN);

	private final static String ANYURI_PATTERN = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";

	private final static Pattern ANYURI_COMPILED_PATTERN = Pattern
			.compile(ANYURI_PATTERN);

	private final static String DOUBLE_PATTERN = "[-+]?[0-9][1-9]*[.][0-9]+";

	private final static Pattern DOUBLE_COMPILED_PATTERN = Pattern
			.compile(DOUBLE_PATTERN);

	private final static String TIME_PATTERN = "(([0][0-9]|1[0-9]|2[0-3])[:][0-5][0-9][:][0-5][0-9]){1}([-+]([0][0-9]|1[0-9]|2[0-3])[:][0-5][0-9])?";

	private final static Pattern TIME_COMPILED_PATTERN = Pattern
			.compile(TIME_PATTERN);

	private final static String DATE_PATTERN = "([0-9][0-9][0-9][0-9][-]([0][0-9]|1[1-2])-([0][0-9]|1[0-9]|2[0-9]|3[0-1])){1}([-+]([0][0-9]|1[0-9]|2[0-3])[:][0-5][0-9])?";

	private final static Pattern DATE_COMPILED_PATTERN = Pattern
			.compile(DATE_PATTERN);

	private final static String DATETIME_PATTERN = "([0-9][0-9][0-9][0-9][-]([0][0-9]|1[1-2])-([0][0-9]|1[0-9]|2[0-9]|3[0-1])[T]([0][0-9]|1[0-9]|2[0-3])[:][0-5][0-9][:][0-5][0-9]){1}([-+]([0][0-9]|1[0-9]|2[0-3])[:][0-5][0-9])?";

	private final static Pattern DATETIME_COMPILED_PATTERN = Pattern
			.compile(DATETIME_PATTERN);

	private final static String DAYTIMEDURATION_PATTERN = "(-)?P(\\d+D)?(T(\\d+H)?(\\d+M)?(\\d+(.\\d+)?S)?)?";

	private final static Pattern DAYTIMEDURATION_COMPILED_PATTERN = Pattern
			.compile(DAYTIMEDURATION_PATTERN);

	private final static String HEXBINARY_PATTERN = "([0-9a-fA-F]{2})*";

	private final static Pattern HEXBINARY_COMPILED_PATTERN = Pattern
			.compile(HEXBINARY_PATTERN);

	private final static String BASE64BINARY_PATTERN = "(?:[A-Za-z0-9+/]{4})*(?:[A-Za-z0-9+/]{2}==|[A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{4})";

	private final static Pattern BASE64BINARY_COMPILED_PATTERN = Pattern
			.compile(BASE64BINARY_PATTERN);

	private final static String RFC822NAME_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private final static Pattern RFC822NAME_COMPILED_PATTERN = Pattern
			.compile(RFC822NAME_PATTERN);

	/********************************
	 * String X.500 AttributeType ------------------------------ CN commonName L
	 * localityName ST stateOrProvinceName O organizationName OU
	 * organizationalUnitName C countryName STREET streetAddress DC
	 * domainComponent UID userid
	 *******************************/

	private final static String X500NAME_PATTERN = "([A-Za-z ]*)=([A-Za-z0-9: ]*)[,]?"; // "(((CN=) | (L=) | (ST=) | (O=) | (OU=) | (C=) | (STREET=) | (DC=) | (UID=))+([a-zA-Z0-9\\,\" ]+))+ ";

	private final static Pattern X500NAME_COMPILED_PATTERN = Pattern
			.compile(X500NAME_PATTERN);

	public void validate(FacesContext context, UIComponent component,
			Object value) throws ValidatorException {

		String dataType = (String) component.getAttributes().get("item");

		// String val = (String) value;

		// System.out.println("----------> 1" + dataType);

		if (dataType == null) {

			// System.out.println("----------> 2");

			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("DataType must be selected.");
			message.setDetail("DataType must be selected.");
			throw new ValidatorException(message);
		}

		else if (dataType.equalsIgnoreCase("String")) {

			Matcher matcher = STRING_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("String is not valid.");
				message.setDetail("String is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("Integer")) {

			Matcher matcher = INTEGER_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Integer is not valid.");
				message.setDetail("Integer is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("Boolean")) {

			if (!value.toString().equalsIgnoreCase("true")
					&& !value.toString().equalsIgnoreCase("false")) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Boolean Value required.");
				message.setDetail("Boolean Value required.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("anyURI")) {

			Matcher matcher = ANYURI_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("URI is not valid.");
				message.setDetail("URI is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("double")) {

			Matcher matcher = DOUBLE_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Double is not valid.");
				message.setDetail("Double is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("time")) {

			Matcher matcher = TIME_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Time is not valid.");
				message.setDetail("Time is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("date")) {

			Matcher matcher = DATE_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Date is not valid.");
				message.setDetail("Date is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("datetime")) {

			Matcher matcher = DATETIME_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Datetime is not valid.");
				message.setDetail("Datetime is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("dayTimeDuration")) {

			Matcher matcher = DAYTIMEDURATION_COMPILED_PATTERN
					.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("DayTimeDuration is not valid.");
				message.setDetail("DayTimeDuration is not valid.");
				throw new ValidatorException(message);
			}

			else if (value.toString().equals("P")
					| (value.toString().charAt(0) == 'P' && value.toString()
							.charAt(value.toString().length() - 1) == 'T')) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("DayTimeDuration is not valid.");
				message.setDetail("DayTimeDuration is not valid.");
				throw new ValidatorException(message);

			}
		}

		else if (dataType.equalsIgnoreCase("hexBinary")) {

			Matcher matcher = HEXBINARY_COMPILED_PATTERN
					.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("HexBinary is not valid.");
				message.setDetail("HexBinary is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("base64Binary")) {

			Matcher matcher = BASE64BINARY_COMPILED_PATTERN
					.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Base64Binary is not valid.");
				message.setDetail("Base64Binary is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("rfc822Name")) {

			Matcher matcher = RFC822NAME_COMPILED_PATTERN
					.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("RFC822Name is not valid.");
				message.setDetail("RFC822Name is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("x500Name")) {

			Matcher matcher = X500NAME_COMPILED_PATTERN.matcher((String) value);

			if (!matcher.matches()) {

				FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("X500NAME is not valid.");
				message.setDetail("X500NAME is not valid.");
				throw new ValidatorException(message);
			}
		}

		else if (dataType.equalsIgnoreCase("select datatype")) {

			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			message.setSummary("Select DataType first.");
			message.setDetail("Select DataType first.");
			throw new ValidatorException(message);
		}
	}

}
