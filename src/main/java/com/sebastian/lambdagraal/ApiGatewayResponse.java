package com.sebastian.lambdagraal;

import java.util.Map;

/**
 * respuesta para ser enviada al cliente que invoca el método.
 *
 * @author Sebastián Ávila A.
 */
public class ApiGatewayResponse {

  /** indica si ocupa base64. */
  private boolean isBase64Encoded;
  /** código http para el cliente. */
  private int statusCode;
  /** headers adicionales que pueden ser enviados. */
  private Map<String, String> headers;
  /** json stringificado con la respuesta al cliente. */
  private String body;

  public ApiGatewayResponse() {
  }

  public ApiGatewayResponse(
          boolean isBase64Encoded, int statusCode, Map<String, String> headers, String body) {
    this.isBase64Encoded = isBase64Encoded;
    this.statusCode = statusCode;
    this.headers = headers;
    this.body = body;
  }

  public boolean isIsBase64Encoded() {
    return isBase64Encoded;
  }

  public void setIsBase64Encoded(boolean isBase64Encoded) {
    this.isBase64Encoded = isBase64Encoded;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Map<String, String> getHeaders() {
    return headers == null ? Map.of() : headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  /**
   * retorna un json vacio stringificado si en nulo el body.
   *
   * @return body para el api gateway
   */
  public String getBody() {
    return body == null ? body = "\"{}\"" : body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}
