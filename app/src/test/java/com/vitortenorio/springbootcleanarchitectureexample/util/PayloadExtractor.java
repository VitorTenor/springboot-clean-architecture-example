package com.vitortenorio.springbootcleanarchitectureexample.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.jayway.jsonpath.JsonPath;
import lombok.RequiredArgsConstructor;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RequiredArgsConstructor
public class PayloadExtractor implements ResultHandler {

    private MvcResult result;
    private final ObjectMapper jsonMapper;

    @Override
    public void handle(MvcResult result) {
        this.result = result;
    }

    public <T> T as(Class<T> payloadClass) throws Exception {
        return jsonMapper.readValue(getContentAsString(), payloadClass);
    }

    public <T> List<T> asListOf(Class<T> payloadClass) throws UnsupportedEncodingException {
        return asListOf(payloadClass, false);
    }

    public <T> List<T> asListOf(Class<T> payloadClass, boolean isPaged) throws UnsupportedEncodingException {
        Object data = extractDataPayloadFromHttpBody(isPaged);

        CollectionType listType = jsonMapper.getTypeFactory().constructCollectionType(List.class, payloadClass);
        return jsonMapper.convertValue(data, listType);
    }

    private Object extractDataPayloadFromHttpBody(boolean isPaged) throws UnsupportedEncodingException {
        String body = getContentAsString();

        if (isPaged) {
            JsonPath jsonPath = JsonPath.compile("@.content");
            return jsonPath.read(body);
        }

        return JsonPath.parse(body).json();
    }

    public String getContentAsString() throws UnsupportedEncodingException {
        return result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    }

    public String getHeaderAsString(String header) {
        return this.result.getResponse().getHeader(header);
    }

}
