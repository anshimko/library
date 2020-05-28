package com.senlainc.library;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterObjectToJson {

	public static String convert(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
