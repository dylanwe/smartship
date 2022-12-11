package com.smartship.backend.app.views;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;

public class CustomJson {
    public static class Shallow {}

    public static class Summary extends Shallow {}

    public static class ShallowSerializer extends JsonSerializer<Object>{
        @Override
        public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,JsonProcessingException {

            JsonMapper mapper = JsonMapper.builder()
                    .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                    .build();

            mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

            mapper.registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

            mapper.setConfig(mapper.getSerializationConfig().withView(Shallow.class));

            jsonGenerator.setCodec(mapper);
            jsonGenerator.writeObject(object);
        }
    }


    public static class SummarySerializer extends JsonSerializer<Object>{
        @Override
        public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException,JsonProcessingException {

            JsonMapper mapper = JsonMapper.builder()
                    .configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
                    .build();

            mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);

            mapper.registerModule(new JavaTimeModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

            mapper.setConfig(mapper.getSerializationConfig().withView(Summary.class));

            jsonGenerator.setCodec(mapper);
            jsonGenerator.writeObject(object);
        }
    }
}
