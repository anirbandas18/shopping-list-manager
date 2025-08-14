FROM ghcr.io/teenthofabud/toab-jre-arm64:21-62
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib app/lib
COPY ${DEPENDENCY}/META-INF app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes app
ENV TZ=Europe/Berlin
EXPOSE 8081
ENTRYPOINT ["java","-Dotel.java.global-autoconfigure.enabled=true","-cp","app:app/lib/*","com.teenthofabud.codingchallenge.ecommerce.ShoppingListManagerApplication"]
