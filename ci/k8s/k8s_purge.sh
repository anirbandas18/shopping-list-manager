# Clean up Kubernetes resources
helm uninstall shopping-list-manager --namespace aleph-alpha
kubectl delete namespace aleph-alpha
helm uninstall zipkin --namespace toab-infrastructure
helm uninstall prometheus --namespace toab-infrastructure
helm uninstall grafana --namespace toab-infrastructure
kubectl delete namespace toab-infrastructure