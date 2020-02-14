package com.sebastian.lambdagraal;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * demo lambda graalvm.
 *
 * <p>la intención es no ocupar dependencias para que sea permite envolver la lógica y no limitar la
 * ejecución.
 *
 * @author Sebastián Ávila A.
 */
public class App {
  private static final Logger LOGGER = Logger.getLogger(App.class.getCanonicalName());

  private static final List<LambdaHandler> HANDLERS = new ArrayList<>();
  private static final List<LambdaService> SERVICES = new ArrayList<>();

  static {
    System.setProperty("javax.net.ssl.trustStore", "cacerts");
    System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    try {
      ServiceLoader.load(LambdaHandler.class).forEach(HANDLERS::add);
      ServiceLoader.load(LambdaService.class).forEach(SERVICES::add);
    } catch (Exception e) {
      try {
        ComunicadorEventos.initError(e);
        LOGGER.log(Level.SEVERE, "no se pudo iniciar", e);
      } catch (Exception ex) {
        LOGGER.log(Level.SEVERE, "error al notificar", ex);
        throw new IllegalStateException(ex);
      }
    }
  }

  public static void main(String[] args) throws MalformedURLException, IOException {
    while (true) {
      final var evento = ComunicadorEventos.next();
      try {
        final var handler = System.getenv("_HANDLER");
        for (final var h : HANDLERS) {
          if (handler.equals(h.getClass().getCanonicalName())) {
            ComunicadorEventos.ok(h.handle(evento), evento);
            break;
          }
        }
      } catch (Exception e) {
        LOGGER.log(Level.SEVERE, "error al invocar post evento", e);
        ComunicadorEventos.invocationError(evento, e);
      }
    }
  }
}
