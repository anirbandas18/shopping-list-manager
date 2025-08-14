mvn clean package -e && \
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar) && \
docker build --progress=plain -t teenthofabud/shopping-list-manager:0.0.1-SNAPSHOT .