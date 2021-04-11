package lab3.gradebook.nc.controllers.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomFormatResponseBody {
    private ObjectMapper objectMapper;

    @Autowired
    public CustomFormatResponseBody(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String buildResponse(boolean response, Object object) throws JsonProcessingException {
        return "{" +
                "\"response\":" + response + ", " +
                "\"message\":" + objectMapper.writeValueAsString(object) +
                '}';
    }
}
