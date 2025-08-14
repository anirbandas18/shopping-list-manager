# Maven, docker and helm build stack
mvn clean package -e && \
mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar) && \
eval $(minikube docker-env) && \
env | grep DOCKER && \
docker build --progress=plain -t teenthofabud/shopping-list-manager:0.0.1-SNAPSHOT . && \
helm lint helm --set secret.openai.apiKey=your-openai-apiKey --debug && \
helm template helm --name-template shopping-list-manager --namespace aleph-alpha --release-name --set secret.openai.apiKey=your-openai-apiKey --output-dir out --debug && \
helm repo add openzipkin https://openzipkin.github.io/zipkin && \
helm template openzipkin/zipkin --name-template zipkin --namespace toab-infrastructure --release-name -f values/k8s/zipkin-config.yaml --output-dir out --debug && \
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts && \
helm template prometheus-community/prometheus --name-template prometheus --namespace toab-infrastructure --release-name -f values/k8s/prometheus-config.yaml  --output-dir out --debug && \
helm repo add grafana https://grafana.github.io/helm-charts && \
helm template grafana/grafana --name-template grafana --namespace toab-infrastructure --release-name -f values/k8s/grafana-config.yaml  --output-dir out --debug && \
helm repo update