java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005 \
-jar \
target/shopping-list-manager.jar \
--spring.config.additional-location=file:values/native/shopping-list-manager-config.yaml,file:values/native/shopping-list-manager-secret.yaml