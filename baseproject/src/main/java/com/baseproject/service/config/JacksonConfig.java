package com.baseproject.service.config;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

@Provider
public class JacksonConfig implements ContextResolver<ObjectMapper> {
	private final ObjectMapper objectMapper;

	public JacksonConfig() throws Exception {
		objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, true);
		objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false);
		objectMapper.configure(SerializationConfig.Feature.AUTO_DETECT_IS_GETTERS, true);
	}

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		return objectMapper;
	}
}
