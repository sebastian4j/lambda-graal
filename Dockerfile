FROM oracle/graalvm-ce:19.3.1-java11 as graalvm
COPY . /app
WORKDIR /app
RUN gu install native-image
RUN native-image --no-server --initialize-at-build-time=org.postgresql.Driver --initialize-at-build-time=org.postgresql.util.SharedTimer  -H:FallbackThreshold=0 -H:+ReportExceptionStackTraces -H:+AddAllCharsets -H:EnableURLProtocols=http,https --enable-all-security-services -H:+JNI -H:+TraceServiceLoaderFeature -H:+StackTrace  -jar target/lambda-graal-1.0-SNAPSHOT.jar

FROM frolvlad/alpine-glibc
COPY --from=graalvm /app/lambda-graal-1.0-SNAPSHOT /app/bootstrap
