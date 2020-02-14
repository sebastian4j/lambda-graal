package com.sebastian.lambdagraal;

/**
 *
 * @author Sebastián Ávila A.
 */
public interface LambdaHandler {

  /**
   * procesa un evento recibido desde aws.
   *
   * @param evento datos asociados al evento
   * @return respuesta del proceso
   */
  public ApiGatewayResponse handle(final EventoAwsLambda evento);
}
