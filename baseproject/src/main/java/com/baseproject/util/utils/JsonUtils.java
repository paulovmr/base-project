package com.baseproject.util.utils;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonUtils {
	
	private static final JsonSerializer<Date> ser = new JsonSerializer<Date>() {
		@Override
		public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
			return src == null ? null : new JsonPrimitive(src.getTime());
		}
	};

	private static final JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
		@Override
		public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			return json == null ? null : new Date(json.getAsLong());
		}
	};

	private static Gson getGsonInstance() { 
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, ser).registerTypeAdapter(Date.class, deser).create();
		return gson;
	}
	
	public static String toJson(Object obj) {
		return getGsonInstance().toJson(obj);		
	}
	
	public static <T> T fromJson(String json, Class<T> clazz) {
		return getGsonInstance().fromJson(json, clazz);
	}
}
