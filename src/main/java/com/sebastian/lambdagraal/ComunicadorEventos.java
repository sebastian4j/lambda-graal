package com.sebastian.lambdagraal;

import com.sebastian.lambdagraal.utils.ApiGatewayResponseUtil;
import com.sebastian.lambdagraal.utils.EventoParserUtil;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * se encarga de comunicar las respustas de exito o error.
 *
 * @author Sebastián Ávila A.
 */
public final class ComunicadorEventos {
  static {
    System.setProperty("http.keepAlive", "false");
  }

  private ComunicadorEventos() {}

  private static final String ALEA = System.getenv("AWS_LAMBDA_RUNTIME_API");
  private static final String NEXT =
      new StringBuilder("http://")
          .append(ALEA)
          .append("/2018-06-01/runtime/invocation/next")
          .toString();

  public static EventoAwsLambda next() throws MalformedURLException, IOException {
    return EventoParserUtil.parse(new URL(NEXT).openConnection());
  }

  private static void post(final String url, final String mensaje) throws IOException {
    final var responsehttp = (HttpURLConnection) new URL(url).openConnection();
    responsehttp.setRequestMethod("POST");
    responsehttp.setDoOutput(true);
    escribir(responsehttp, mensaje);
    responsehttp.disconnect();
  }

  private static void escribir(final HttpURLConnection conn, final String mensaje)
      throws IOException {
    try (final var os = conn.getOutputStream()) {
      os.write(mensaje.getBytes());
      os.flush();
    }
    try (final var is = conn.getInputStream()) {
      while (is.read() != -1) {
        // requerido
      }
    }
  }

  public static void invocationError(final EventoAwsLambda evento, final Exception e)
      throws MalformedURLException, IOException {
    post(
        new StringBuilder("http://")
            .append(ALEA)
            .append("/2018-06-01/runtime/invocation/")
            .append(evento.getRequestid())
            .append("/error")
            .toString(),
        "{\"errorMessage\" : \""
            + e.getMessage()
            + "\", \"errorType\" : \""
            + e.getClass()
            + "\"}");
  }

  public static void initError(final Exception e) throws MalformedURLException, IOException {
    post(
        new StringBuilder("http://")
            .append(ALEA)
            .append("/2018-06-01/runtime/init/error")
            .toString(),
        "{\"errorMessage\" : \""
            + e.getMessage()
            + "\", \"errorType\" : \""
            + e.getClass()
            + "\"}");
  }

  public static void ok(final ApiGatewayResponse response, EventoAwsLambda evento)
      throws MalformedURLException, IOException {
    post(
        new StringBuilder("http://")
            .append(ALEA)
            .append("/2018-06-01/runtime/invocation/")
            .append(evento.getRequestid())
            .append("/response")
            .toString(),
        ApiGatewayResponseUtil.response(response));
  }
}
