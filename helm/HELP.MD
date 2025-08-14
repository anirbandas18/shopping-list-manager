* ```helm lint helm --set-file secretsFile=values/shopping-list-manager/local/secret.yaml --set-file configFile=values/shopping-list-manager/local/config.yaml --debug```
* ```helm template helm --name-template shopping-list-manager --namespace aleph-alpha --release-name --set-file secretsFile=values/shopping-list-manager/local/secret.yaml --set-file configFile=values/shopping-list-manager/local/config.yaml --output-dir out --debug```
* ```helm upgrade --install --create-namespace --namespace aleph-alpha --set-file secretsFile=values/shopping-list-manager/local/secret.yaml --set-file configFile=values/shopping-list-manager/local/config.yaml --debug shopping-list-manager helm```
* ```kubectl port-forward pod/shopping-list-manager-dd45bc494-wx6bk 8081:8081 -n aleph-alpha```
