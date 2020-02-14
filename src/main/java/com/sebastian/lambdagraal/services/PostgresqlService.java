package com.sebastian.lambdagraal.services;

import com.sebastian.lambdagraal.LambdaService;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/** @author Sebastián Ávila A. */
public class PostgresqlService implements LambdaService {
  private static final Logger LOGGER = Logger.getLogger(PostgresqlService.class.getCanonicalName());

  static {
    LOGGER.info("CARGANDO CONEXIÓN A LA BD...");
    try (final var con =
            DriverManager.getConnection(
                "jdbc:postgresql://url/postgres",
                "user",
                "pass");
        final var ps = con.prepareStatement("select now()");
        final var rs = ps.executeQuery()) {
      while (rs.next()) {
        LOGGER.info(rs.getString(1));
      }
    } catch (Exception ex) {
      LOGGER.log(Level.SEVERE, "no se puede iniciar la conexión con la base de datos", ex);
      throw new IllegalStateException(ex);
    }
    LOGGER.info("BD Cargada...");
  }
}
