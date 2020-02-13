package com.sebastian.lambdagraal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.Arrays;

/**
 * demo lambda graalvm.
 *
 * @author Sebastián Ávila A.
 */
public class App {

    public static void main(String[] args) throws MalformedURLException, IOException {
        System.out.println("args: " + Arrays.toString(args));

        while (true) {
            System.out.println("crear url");
            final var url = new java.net.URL(
                    new StringBuilder("http://")
                            .append(System.getenv("AWS_LAMBDA_RUNTIME_API"))
                            .append("/2018-06-01/runtime/invocation/next")
                            .toString());
            System.out.println("abrir conexión");
            final var conn = url.openConnection();
            final var requestid = conn.getHeaderField("Lambda-Runtime-Aws-Request-Id");
            System.out.println("requestid: " + requestid);
            try (final var is = conn.getInputStream()) {
                int lee;
                System.out.println("*****************");
                while ((lee = is.read()) != -1) {
                    System.out.print((char) lee);
                }
                System.out.println("*****************");
            }
            ((HttpURLConnection) conn).disconnect();            
            final var ok = new java.net.URL(
                    new StringBuilder("http://")
                            .append(System.getenv("AWS_LAMBDA_RUNTIME_API"))
                            .append("/2018-06-01/runtime/invocation/")
                            .append(requestid).append("/response")
                            .toString());
            final var responsehttp = (HttpURLConnection) ok.openConnection();
            responsehttp.setRequestMethod("POST");
            responsehttp.setDoOutput(true);
            responsehttp.connect();
            try (final var os = responsehttp.getOutputStream()) {
                os.write("ok".getBytes());
                os.flush();
            }
            responsehttp.disconnect();
        }
    }
}
