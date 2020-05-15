package com.senlainc.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

class ConverterObjectToJson {

	static String convert(final Object obj) {
		try {
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonContent = mapper.writeValueAsString(obj);
			System.out.println(jsonContent);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
