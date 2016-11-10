package jp.eiya.aya.web.util.rest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractGenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;

public class APIResponse2HttpMessageConverter extends AbstractGenericHttpMessageConverter<APIResponse<?>> {
    MappingJackson2HttpMessageConverter jsonConverter;
    public APIResponse2HttpMessageConverter() {
        this.jsonConverter = new MappingJackson2HttpMessageConverter(Jackson2ObjectMapperBuilder.json().build());
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(APIResponse.class);
    }

    @Override
    protected APIResponse<?> readInternal(Class<? extends APIResponse<?>> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new HttpMessageNotReadableException("APIResponse is not readable.");
    }

    @Override
    protected void writeInternal(APIResponse<?> apiResponse, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getHeaders().add("x-message",apiResponse.message());
        jsonConverter.write(apiResponse.getResult(),MediaType.APPLICATION_JSON,outputMessage);
    }

    @Override
    public APIResponse<?> read(Type type, Class<?> contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        throw new HttpMessageNotReadableException("APIResponse is not readable.");
    }
}
