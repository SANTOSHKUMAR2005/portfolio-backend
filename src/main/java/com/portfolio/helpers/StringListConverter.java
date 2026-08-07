package com.portfolio.helpers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
	
		return attribute==null?"":String.join(",", attribute);
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
         if(dbData==null || dbData.trim().isEmpty()) {
        	 return Collections.emptyList();
         }
		return Arrays.asList(dbData.split(","));
	}

}
