package com.sebastian.lambdagraal.services;

import com.sebastian.lambdagraal.LambdaService;
import java.util.logging.Level;
import java.util.logging.Logger;
import software.amazon.awssdk.http.urlconnection.UrlConnectionHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

/** @author Sebastián Ávila A. */
public class DynamoService implements LambdaService {

  private static final Logger LOGGER = Logger.getLogger(DynamoService.class.getCanonicalName());

  static {
    LOGGER.info("CARGANDO CONEXIÓN A DYNAMO...");
    final var ddb =
        DynamoDbClient.builder()
            .httpClientBuilder(UrlConnectionHttpClient.builder())
            .region(Region.US_EAST_1)
            .build();
    // listar las tablas: https://docs.aws.amazon.com/sdk-for-j1ava/v2/developer-guide/examples-dynamodb-tables.html
    LOGGER.log(Level.INFO, "DYNAMO CARGADO...{0}", ddb);
  }
}
