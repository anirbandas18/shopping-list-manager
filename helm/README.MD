# Clone The Repo

`git clone git@github.com:anirbandas18/shopping-list-manager.git`

## Helm Deploy

`cd shopping-list-manager/helm`

### This step makes your shell use Minikube’s internal Docker daemon.

> This command sets your terminal to use Minikube’s built-in Docker environment.
> Build image available only inside it. 

`eval $(minikube docker-env)`

### Use this command to switch back to your normal local Docker.

`eval $(minikube docker-env -u)`

### Verify that you are using Minikube’s Docker daemon

`env | grep DOCKER`

### Build image inside Minikube’s internal Docker daemon

`docker build --progress=plain -t teenthofabud/shopping-list-manager:0.0.1-SNAPSHOT .`

### Run image inside Minikube’s internal Docker daemon

`docker run --name shopping-list-manager -p 8081:8081 -e OTEL_HOST=localhost -e OTEL_PORT=4318 -t teenthofabud/shopping-list-manager:0.0.1`


### Create K8S namespace

`kubectl create namespace aleph-alpha`

## Zipkin

`helm repo add openzipkin https://openzipkin.github.io/zipkin`

`helm repo update`

`helm install zipkin openzipkin/zipkin --namespace toab-infrastructure`

`helm upgrade --install --create-namespace --namespace toab-infrastructure --debug zipkin openzipkin/zipkin`


## OpenTelemetry

`helm repo add open-telemetry https://open-telemetry.github.io/opentelemetry-helm-chartss`

`helm repo update`

## OTEL

### Add otel-values.yaml from CLI (If not present )


```bash

cat <<EOF > value/opentelemetry-collector/otel-values.yaml
image:
  repository: otel/opentelemetry-collector-contrib

mode: deployment
config:
  receivers:
    otlp:
      protocols:
        grpc:
          endpoint: "0.0.0.0:4317"
        http:
          endpoint: "0.0.0.0:4318"
  exporters:
    debug:
      verbosity: detailed
    zipkin:
      endpoint: "http://zipkin.your-namespace.svc:9411/api/v2/spans"
      format: proto
  processors:
    batch: {}
  extensions:
    health_check: {}
  service:
    extensions: [health_check]
    pipelines:
      metrics:
        receivers: [otlp]
        exporters: [debug]
      traces:
        receivers: [otlp]
        exporters: [zipkin]
      logs:
        receivers: [otlp]
        exporters: [debug]

EOF

```

### Install OTEL

`helm install otel-collector open-telemetry/opentelemetry-collector -n <your-namespace> -f otel-values.yaml`

### Install the application Helm Chart

`helm install your-installation-name ./shopping-list-manager-helm -n <your-namespace>`

### Port Forwarding 

`kubectl port-forward pod/<pod-name> <local-port>:<pod-port> -n <namespace>`
