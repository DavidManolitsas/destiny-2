package com.manolitsas.david.client.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RestClientInterceptor implements ClientHttpRequestInterceptor {

  @Override
  @NonNull
  public ClientHttpResponse intercept(
      @NonNull HttpRequest request,
      @NonNull byte[] body,
      @NonNull ClientHttpRequestExecution execution)
      throws IOException {
    String requestBodyMessage = getRequestBodyMessage(body);
    log.info(
        "Sending HTTP {} request to {} {}",
        request.getMethod(),
        request.getURI(),
        requestBodyMessage);
    return execution.execute(request, body);
  }

  private String getRequestBodyMessage(byte[] body) {
    if (body == null) {
      return "";
    }

    try {
      String requestBody = new String(body, StandardCharsets.UTF_8);
      if (requestBody.trim().isEmpty()) {
        return "";
      }
      return String.format("with request body: %s", requestBody);
    } catch (Exception e) {
      return "unable to deserialize request body";
    }
  }
}
