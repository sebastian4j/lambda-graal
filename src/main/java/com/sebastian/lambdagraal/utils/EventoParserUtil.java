package com.sebastian.lambdagraal.utils;

import com.sebastian.lambdagraal.EventoAwsLambda;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 * permite obtener la representación del evento recibido.
 *
 * @author Sebastián Ávila A.
 */
public final class EventoParserUtil {

  private EventoParserUtil() {
  }

  /**
   * obtiene el evento que representa al request recibido al custom runtime de
   * lambda.
   *
   * @param conn desde donde se leer los datos para generar la representación
   * @return datos asociados al request
   * @throws IOException error al obtener los datos
   */
  public static EventoAwsLambda parse(final URLConnection conn) throws IOException {
    final var evento = new EventoAwsLambda();
    evento.setRequestid(conn.getHeaderField("Lambda-Runtime-Aws-Request-Id"));
    evento.setDeadlinems(conn.getHeaderFieldLong("Lambda-Runtime-Deadline-Ms", 0));
    evento.setArn(conn.getHeaderField("Lambda-Runtime-Invoked-Function-Arn"));
    evento.setTraceid(conn.getHeaderField("Lambda-Runtime-Trace-Id"));
    evento.setCctx(conn.getHeaderField("Lambda-Runtime-Client-Context"));
    evento.setCgnid(conn.getHeaderField("Lambda-Runtime-Cognito-Identity"));

    try (final var is = conn.getInputStream()) {
      evento.setData(leer(is));
    }
    return evento;
  }

  /**
   * obtengo el evento en bytes.
   *
   * @param is inputstream con los datos recibidos
   * @return representación en bytes del evento
   * @throws IOException error al leer el evento
   */
  private static byte[] leer(final InputStream is) throws IOException {
    final var baos = new ByteArrayOutputStream();
    try (is) {
      int lee;
      while ((lee = is.read()) != -1) {
        baos.write(lee);
      }
    }
    return baos.toByteArray();
  }
}
