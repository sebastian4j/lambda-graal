package com.sebastian.lambdagraal.utils;

import com.sebastian.lambdagraal.ApiGatewayResponse;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * genera la respuesta en el formato que espera el api gateway y las comunicaciones.
 *
 * @author Sebastián Ávila A.
 */
public final class ApiGatewayResponseUtil {

  private ApiGatewayResponseUtil() {}

  /**
   * utiliza los datos para genera el string para ser enviado al api gateway.
   *
   * @param agr datos para ser enviados
   * @return representación en formato para el api gateway
   */
  public static String response(final ApiGatewayResponse agr) {
    final var sb =
        new StringBuilder("{\"isBase64Encoded\":")
            .append(agr.isIsBase64Encoded())
            .append(",\"statusCode\":")
            .append(agr.getStatusCode())
            .append(",\"headers\":{");
    final var headers = new ArrayList<String>();
    agr.getHeaders()
        .forEach(
            (a, b) ->
                headers.add(
                    new StringBuilder()
                        .append("\"")
                        .append(a)
                        .append("\":\"")
                        .append(b)
                        .append("\"")
                        .toString()));
    sb.append(headers.stream().collect(Collectors.joining(",")))
        .append("},\"body\":")
        .append(agr.getBody())
        .append("}");
    return sb.toString();
  }
}
