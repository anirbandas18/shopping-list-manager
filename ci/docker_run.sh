docker stop shopping-list-manager
docker rm shopping-list-manager
docker run --name shopping-list-manager -p 8081:8081 -t teenthofabud/shopping-list-manager:0.0.1-SNAPSHOT