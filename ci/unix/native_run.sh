export OTEL_HOST=localhost
export OTEL_PORT=4318
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 -jar target/shopping-list-manager.jar