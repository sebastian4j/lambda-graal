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

	/**
	 * obtiene el siguiente evento de invocación del lambda.
	 * 
	 * @return evento recibido
	 * @throws MalformedURLException error de url
	 * @throws IOException error al obtener los datos del evento
	 */
  public static EventoAwsLambda next() throws MalformedURLException, IOException {
    return EventoParserUtil.parse(new URL(NEXT).openConnection());
  }

	/**
	 * envia el mensaje a la url indicada usando post.
	 * 
	 * @param url donde enviar el mensaje
	 * @param mensaje contenido del mensaje
	 * @throws IOException error al enviar el mensaje
	 */
  private static void post(final String url, final String mensaje) throws IOException {
    final var responsehttp = (HttpURLConnection) new URL(url).openConnection();
    responsehttp.setRequestMethod("POST");
    responsehttp.setDoOutput(true);
    escribir(responsehttp, mensaje);
    responsehttp.disconnect();
  }

	/**
	 * escribe el mensaje el la conexión recibida.
	 * 
	 * @param conn donde se escribirá el mensaje
	 * @param mensaje contenido del mensaje a ser escrito
	 * @throws IOException error al escribir el mensaje
	 */
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

	/**
	 * notifica un error de invocación del lambda al procesar un evento
	 * 
	 * @param evento evento que se intentó procesar
	 * @param e error que se generó
	 * @throws MalformedURLException error en la url de destino
	 * @throws IOException error al escribir el error
	 */
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

	/**
	 * notifica un error al iniciar el lambda.
	 * 
	 * @param e error que generó el problema
	 * @throws MalformedURLException error en la url de destino
	 * @throws IOException error al enviar el error
	 */
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

	/**
	 * envia una respuesta ok.
	 * 
	 * @param response respuesta para el cliente
	 * @param evento evento que tiene que ser enviado al cliente
	 * @throws MalformedURLException error en la url de destino
	 * @throws IOException error al escribir la respuesta
	 */
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
