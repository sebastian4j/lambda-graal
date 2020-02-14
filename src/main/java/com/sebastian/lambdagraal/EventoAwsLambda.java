package com.sebastian.lambdagraal;

/**
 * datos recibidos desde el evento de aws lambda.
 *
 * https://docs.aws.amazon.com/es_es/lambda/latest/dg/runtimes-api.html
 *
 * @author Sebastián Ávila A.
 */
public class EventoAwsLambda {

  /**
   * Lambda-Runtime-Aws-Request-Id: el ID de solicitud, el cual identifica la
   * solicitud que ha desencadenado la invocación de la función.
   *
   * Por ejemplo, 8476a536-e9f4-11e8-9739-2dfe598c3fcd.
   */
  private String requestid;
  /**
   * Lambda-Runtime-Deadline-Ms: la fecha en la que la función agota su tiempo
   * de espera en milisegundos de tiempo Unix.
   *
   * Por ejemplo, 1542409706888.
   */
  private long deadlinems;
  /**
   * Lambda-Runtime-Invoked-Function-Arn: el ARN de la función de Lambda, la
   * versión o el alias especificado en la invocación.
   *
   * Por ejemplo, arn:aws:lambda:us-east-2:123456789012:function:custom-runtime.
   */
  private String arn;
  /**
   * mbda-Runtime-Trace-Id: el AWS X-Ray encabezado de rastreo.
   *
   * Por ejemplo,
   * Root=1-5bef4de7-ad49b0e87f6ef6c87fc2e700;Parent=9a9197af755a6419;Sampled=1.
   */
  private String traceid;
  /**
   * Lambda-Runtime-Client-Context: en invocaciones desde AWS Mobile SDK, datos
   * sobre la aplicación cliente y el dispositivo.
   */
  private String cctx;
  /**
   * Lambda-Runtime-Cognito-Identity: para invocaciones desde AWS Mobile SDK,
   * datos sobre el proveedor de identidades de Amazon Cognito.
   */
  private String cgnid;
  /**
   * datos que representan al evento recibido desde
   * /2018-06-01/runtime/invocation/next.
   */
  private byte[] data;

  public byte[] getData() {
    return data;
  }

  public void setData(byte[] data) {
    this.data = data;
  }

  /**
   * metodo conveniente para obtener la representación de la data recibida.
   *
   * @return string con los datos recibidos, por defecto es vacio (no nulo).
   */
  public String data() {
    String retorno = "\"{}\"";
    if (data != null) {
      retorno = new String(data);
    }
    return retorno;
  }

  public String getRequestid() {
    return requestid;
  }

  public void setRequestid(String requestid) {
    this.requestid = requestid;
  }

  public long getDeadlinems() {
    return deadlinems;
  }

  public void setDeadlinems(long deadlinems) {
    this.deadlinems = deadlinems;
  }

  public String getArn() {
    return arn;
  }

  public void setArn(String arn) {
    this.arn = arn;
  }

  public String getTraceid() {
    return traceid;
  }

  public void setTraceid(String traceid) {
    this.traceid = traceid;
  }

  public String getCctx() {
    return cctx;
  }

  public void setCctx(String cctx) {
    this.cctx = cctx;
  }

  public String getCgnid() {
    return cgnid;
  }

  public void setCgnid(String cgnid) {
    this.cgnid = cgnid;
  }

  @Override
  public String toString() {
    return "Evento{" + "requestid=" + requestid + ", deadlinems=" + deadlinems + ", arn=" + arn + ", traceid=" + traceid + ", cctx=" + cctx + ", cgnid=" + cgnid + '}';
  }
}
