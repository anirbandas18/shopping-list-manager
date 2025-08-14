# Shopping List Manager

## Features

* Secured login
* Add new items to the inventory with special permissions
* Multi shopping lists for all users
* Get detailed shopping list of a user
* Track user interactions with an item if it is added to or deleted from their shopping list
* Get recommended complementary and alternative items based on shopping list content
* First 3 characters of each item's name from the shopping list is used to recommend items
* Receive recipe ideas based on items already in the shopping list along while also considering recommended complementary and alternative items

## User roles

* **USER**: Can view and manage their own shopping lists, get recommended items and recipe ideas based on their shopping list
* **ADMIN**: Can add new items to the inventory plus all user permissions

## Predefined users

| **User** | **Password** | **Role** |
|----------|--------------|----------|
| adas     | adas         | ADMIN    |
| user     | user         | USER     |

## System requirements

* Unix compatible operating system (Linux, macOS)
* Internet connection
* JDK 21
* Maven 3.9.10
* Docker 27.4.1
* Helm v3.14.3
* Minikube v1.36.0
* kubectl v1.33.3
* JAVA_HOME environment variable set to your JDK 21 installation path
* OpenAI API key

## Deploying the application to local Kubernetes cluster on minikube

### If you do not have a local minikube setup, follow these steps in order:

1. Execute the script `ci/k8s/k8s_admin.sh` to start the minikube Kubernetes cluster (can take some time) after you have installed it
2. Execute the script `ci/k8s/k8s_build.sh` to build the maven artifacts, docker image and the helm chart for the application. It also adds the external observability helm dependencies for this application
3. Edit the `ci/k8s/k8s_run.sh` script and replace the string `your-openai-apiKey` with your own OpenAI API key so that the application functions properly
4. Make sure that you do not commit the modified `ci/k8s/k8s_run.sh` script containing your OpenAI API key for security purposes
5. Execute the script `ci/k8s/k8s_run.sh` to deploy the application and its dependent observability helm charts to the minikube cluster
6. At the end of the `ci/k8s/k8s_run.sh` script execution (takes 30s time to be safe) you will see the URL to access the application in the shell where you executed this script in the format http://127.0.0.1:TUNNEL_PORT
7. If your browser does not automatically open the URL http://127.0.0.1:TUNNEL_PORT, copy and paste it into your browser to access the application

### If you already have a local minikube setup running, follow this step:

1. **DO NOT EXECUTE THE SCRIPT** `ci/k8s/k8s_admin.sh` as it will delete your already running minikube cluster and start a new one
2. Execute the script `ci/k8s/k8s_build.sh` to build the maven artifacts, docker image and the helm chart for the application. It also adds the external observability helm dependencies for this application
3. Edit the `ci/k8s/k8s_run.sh` script and replace the string `your-openai-apiKey` with your own OpenAI API key so that the application functions properly
4. Make sure that you do not commit the modified `ci/k8s/k8s_run.sh` script containing your OpenAI API key for security purposes
5. Execute the script `ci/k8s/k8s_run.sh` to deploy the application and its dependent observability helm charts to the minikube cluster
6. At the end of the `ci/k8s/k8s_run.sh` script execution (takes 30s time to be safe) you will see the URL to access the application in the shell where you executed this script in the format http://127.0.0.1:TUNNEL_PORT
7. If your browser does not automatically open the URL http://127.0.0.1:TUNNEL_PORT, copy and paste it into your browser to access the application

### If you want to remove this application and its installed dependencies from your minikube Kubernetes cluster, follow this step:

1. Execute the script `ci/k8s/k8s_purge.sh` to delete the application and its dependent observability helm charts and namespaces from the minikube cluster

### If you want to run this application and its installed dependencies from your minikube Kubernetes cluster provided you did not change any application code, follow this step:

1. Execute the script `ci/k8s/k8s_run.sh` to deploy the application and its dependent observability helm charts to the minikube cluster

### If you want to run this application and its installed dependencies from your minikube Kubernetes cluster after changing application code, follow this step:

1. Execute the script `ci/k8s/k8s_build.sh` to build the maven artifacts, docker image and the helm chart for the application. It also adds the external observability helm dependencies for this application
2. Execute the script `ci/k8s/k8s_run.sh` to deploy the application and its dependent observability helm charts to the minikube cluster

## WARNING

The setup does not utilize any persistent storage for the application and grafana, so all data will be lost when the minikube cluster is deleted or restarted. 
This is intended for local development and testing purposes only.

## Usage

### Operational

#### Application

* After the application starts running on executing `ci/k8s/k8s_run.sh` script keep the console open so that the tunneling between your localhost vs minikube is kept alive to access the application
* To exit the application and stop the tunneling, press `Ctrl+C` in the terminal where you executed the `ci/k8s/k8s_run.sh` script
* To access the application again after having deployed it you can execute the command ``minikube service shopping-list-manager --namespace aleph-alpha --url`` in a new or the same terminal. You will see the new URL to access the application on the console in the format http://127.0.0.1:TUNNEL_PORT
* The Swagger UI of the application available at http://127.0.0.1:TUNNEL_PORT
* The default root path **/** of the application represents the Swagger UI
* The grafana admin password is also available in the same terminal where you run the `ci/k8s/k8s_run.sh` script. You will find the grafana admin password with the phrase `**** is your Grafana admin password`

#### Observability

* To access zipkin after having deployed it you can execute the command ``minikube service zipkin --namespace toab-infrastructure --url`` in a new terminal
* You will see the new URL to access the application on the console in the format http://127.0.0.1:TUNNEL_PORT where the web UI of zipkin is available
* To exit zipkin and stop the tunneling, press `Ctrl+C` in the same terminal
* To access prometheus after having deployed it you can execute the command ``minikube service prometheus-server --namespace toab-infrastructure --url`` in a new terminal
* You will see the new URL to access the application on the console in the format http://127.0.0.1:TUNNEL_PORT where the web UI of prometheus is available
* To exit prometheus and stop the tunneling, press `Ctrl+C` in the same terminal
* The default username for grafana is admin
* To get the password for grafana admin user after having deployed it you can execute the command ``kubectl get secret --namespace toab-infrastructure grafana -o jsonpath="{.data.admin-password}" | base64 --decode ; echo`` in a new terminal
* To access grafana after having deployed it you can execute the command ``minikube service grafana --namespace toab-infrastructure --url`` in the same terminal
* You will see the new URL to access the application on the console in the format http://127.0.0.1:TUNNEL_PORT where the web UI of grafana is available
* To exit grafana and stop the tunneling, press `Ctrl+C` in the same terminal

### Functional

* You can use the predefined users to test the application functionality
* You need to have ADMIN role privilege as a user to add new items to the inventory
* You can add new users by making a new SQL insert statement to the file `src/main/resources/import.sql`. Examples are already available, you can review them. You can use the provided BCryptProducer to generate an encoded password for a new user
* You can view all items available in the inventory or add a new item to the inventory by using the ItemAPI
* You can view all item categories by using the CategoryAPI
* You can manage your shopping lists by using the ShoppingListAPI
* You can add or remove items to your shopping list using the ShoppingListAPI
* Items are recommended that either contain, start with or end with the first 3 characters of an item name from the shopping list