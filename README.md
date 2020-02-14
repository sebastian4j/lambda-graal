### Entrada del Blog: https://javasofias.blogspot.com/2020/02/graalvm-java-11-aws-lambda-postgresql.html

### ?????
Este repositorio contiene el código para crear una lambda de AWS usando Java 11 y compilado con GraalVM en forma nativa

Lanzarlo con:
```
bash scripts.sh
```
luego subir el archivo *function.zip* a la consola de aws lambda

* Requiere Docker

### Enlaces útiles:

https://github.com/oracle/graal/issues/1891
https://docs.aws.amazon.com/lambda/latest/dg/runtimes-walkthrough.html
https://medium.com/@manuj.bhalla.007/aws-lambda-custom-runtime-graalvm-native-image-27c14590dc1b
https://stackoverflow.com/questions/22049212/copying-files-from-docker-container-to-host
https://docs.aws.amazon.com/es_es/lambda/latest/dg/runtimes-api.html


