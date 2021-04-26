# Helm
## Introduction
You may be familiar with the apt-get or yum tools used to install packages in Linux based systems. Analogously, helm is like a package tool that allows you to install applications into a Kubernetes cluster. Refer to the Helm documentation, and you will find that Helm provides benefits similar to package managers in Linux:

+ Manage Complexity: Helm Charts allow developers to describe complex architectures and easily deploy, update and remove these applications to Kubernetes clusters.
+ Easy Updates: Applications and resources in Kubernetes can be upgraded seamlessly using the Helm client.
+ Simple Sharing: Application Charts are versioned and can be distributed for deployment by other users. There are many stable packages that you can choose from, such as MySQL, MongoDB, WordPress, etc. Once you become more familiar with Helm, you are encouraged to explore the Helm Hub.
+ Rollbacks - In case of a failed application deployment, you can use helm to revert to a known worked state.

## Command
Get version
```
$ helm version
```

Get the latest Chart information from chart repositories, similar to the apt-get update command in Linux.
```
$ helm repo add stable https://charts.helm.sh/stable

$ helm repo update
```

Use Helm to deploy the MySQL backend to the GKE cluster(create cluster and authenticate)
```
$ export mysqlRootPassword=...
$ export mysqlUser=...
$ export mysqlPassword=...

$ helm install mysql-profile --set mysqlRootPassword=${mysqlRootPassword},mysqlUser=${mysqlUser},mysqlPassword=${mysqlPassword},mysqlDatabase=test stable/mysql
```

Validate the helm installation by running the following command. 
```
$ helm list
```

install a Helm release using a prepared Helm chart, the syntax of the command is
```
helm install [RELEASE_NAME] [CHART] [flags]
```

Below is the concrete command you will use to install the stable/mysql chart as a Helm release named mysql-instance.
```
# Set environment variables for the MySQL properties
export MYSQL_ROOT_PWD="<REPLACE_THIS_VALUE>"
export MYSQL_PWD="<REPLACE_THIS_VALUE>"
export MYSQL_USERNAME="<REPLACE_THIS_VALUE>"

helm install \
    mysql-instance \
    --set mysqlRootPassword=$MYSQL_ROOT_PWD,mysqlUser=$MYSQL_USERNAME,mysqlPassword=$MYSQL_PWD,mysqlDatabase=test \
    stable/mysql
# Sample output:
# NAME:   mysql-instance
# LAST DEPLOYED: ...
# NAMESPACE: default
# STATUS: DEPLOYED
#  
# RESOURCES:
# ...
# 
# NOTES:
# MySQL can be accessed via port 3306 on the following DNS name from within your cluster:
# mysql-instance.default.svc.cluster.local
```

```
helm install profile profile-service/src/main/helm/profile/
```



You can retrieve the status of a release using helm status.
```
helm status mysql-instance
```

MySQL can be accessed via port 3306 on the following DNS name from within your cluster:
mysql-profile.default.svc.cluster.local

To get your root password run:

    MYSQL_ROOT_PASSWORD=$(kubectl get secret --namespace default mysql-profile -o jsonpath="{.data.mysql-root-password}" | base64 --decode; echo)

To connect to your database:

1. Run an Ubuntu pod that you can use as a client:

    kubectl run -i --tty ubuntu --image=ubuntu:16.04 --restart=Never -- bash -il

2. Install the mysql client:

    $ apt-get update && apt-get install mysql-client -y

3. Connect using the mysql cli, then provide your password:
    $ mysql -h mysql-profile -p

To connect to your database directly from outside the K8s cluster:
    MYSQL_HOST=127.0.0.1

```
helm install profile profile-service/src/main/helm/profile/
```

+ uninstall
```
helm uninstall helloweb
```

+ update
```
helm upgrade profile profile-service/src/main/helm/profile/
```


## ConfigMap

## Ingress
minimal-ingress.yaml:
```
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: minimal-ingress
  annotations:
    nginx.ingress.kubernetes.io/rewrite-target: /
spec:
  rules:
  - http:
      paths:
      - path: /testpath
        pathType: Prefix
        backend:
          service:
            name: test
            port:
              number: 80
```