package com.portfolio.configractions;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfigiration {
   
	@Bean
	ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
