package com.codingexam.customer.utility;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class TestObjectBuilder {

    public static <T> T buildRequest(String filePath, Class<T> responseType) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.registerModule(new JavaTimeModule().addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_DATE)));
        InputStream in = TestObjectBuilder.class.getClassLoader().getResourceAsStream(filePath);
        if (in == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }
        try {
            T responseDto = mapper.readValue(in, responseType);
            log.info("Test: " + responseDto.toString());
            return responseDto;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
