## Deploy application to Kubernetes cluster using Helm

## Shopping List Manager application
helm upgrade --install --create-namespace --namespace aleph-alpha --debug --set secret.openai.apiKey=your-openai-apiKey shopping-list-manager helm && \

## Zipkin traces tool
helm upgrade --install --create-namespace --namespace toab-infrastructure --debug -f values/k8s/zipkin-config.yaml zipkin openzipkin/zipkin && \

## Prometheus metrics tool
helm upgrade --install --create-namespace --namespace toab-infrastructure --debug -f values/k8s/prometheus-config.yaml prometheus prometheus-community/prometheus && \

## Grafana observability tool
helm upgrade --install --create-namespace --namespace toab-infrastructure --debug -f values/k8s/grafana-config.yaml grafana grafana/grafana && \
kubectl get secret --namespace toab-infrastructure grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo " is your Grafana admin password" && \

## Wait for all application to be available
kubectl wait --for=condition=available --timeout=30s deployment/shopping-list-manager --namespace aleph-alpha && \

## Get the URL of the Shopping List Manager service on Kubernetes
minikube service shopping-list-manager --namespace aleph-alpha --url  --interval=30  --wait=30