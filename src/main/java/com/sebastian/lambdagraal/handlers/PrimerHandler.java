package com.sebastian.lambdagraal.handlers;

import com.sebastian.lambdagraal.ApiGatewayResponse;
import com.sebastian.lambdagraal.EventoAwsLambda;
import com.sebastian.lambdagraal.LambdaHandler;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/** @author Sebastián Ávila A. */
public class PrimerHandler implements LambdaHandler {

  @Override
  public ApiGatewayResponse handle(EventoAwsLambda evento) {
    return new ApiGatewayResponse(
        false,
        ThreadLocalRandom.current().nextInt(200, 500),
        Map.of("a", getClass().getCanonicalName(), "uuid", UUID.randomUUID().toString()),
        "\"{\\\"x\\\":\\\"y\\\", \\\"z\\\":23}\"");
  }
}
