FROM oracle/graalvm-ce:19.3.1-java11 as graalvm
COPY . /home/app/basic-app
WORKDIR /home/app/basic-app
RUN gu install native-image
RUN native-image --no-server --static -H:EnableURLProtocols=http,https -jar target/lambda-graal-1.0-SNAPSHOT.jar

FROM frolvlad/alpine-glibc
COPY --from=graalvm /home/app/basic-app/lambda-graal-1.0-SNAPSHOT /app/lambda-graal-1.0-SNAPSHOT
# ENTRYPOINT ["/app/lambda-graal-1.0-SNAPSHOT"]