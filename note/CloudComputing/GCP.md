# Google Cloud Platform

见[The gcloud command-line tool cheat sheet](https://cloud.google.com/sdk/docs/cheatsheet)

+ ssh
```
gcloud compute ssh --zone "us-east1-b" clouduser@student-vm --project <enter-project-id>
```

## Google Kubernetes Engine (GKE) 
Authenticate to the Google Container Registry.
```
gcloud auth configure-docker
```

+ Create a Cluster 
```
# The name may contain only lowercase alphanumerics and '-', 
# and it must start with a letter and end with an alphanumeric, 
# and it must be no longer than 40 characters.
CLUSTER_NAME=gke-demo-cluster

gcloud container clusters create ${CLUSTER_NAME} --num-nodes=3
# Expected output:
# ...
# Creating cluster gke-demo-cluster in us-east1-d... Cluster is being health-checked (master is healthy)...done.
# Created [https://container.googleapis.com/v1/projects/<project_id>/zones/us-east1-d/clusters/gke-demo-cluster].
# To inspect the contents of your cluster, go to: https://console.cloud.google.com/kubernetes/workload_/gcloud/us-east1-d/gke-demo-cluster?project=<project_id>
# kubeconfig entry generated for gke-demo-cluster.
# NAME              LOCATION    MASTER_VERSION  MASTER_IP      MACHINE_TYPE   NODE_VERSION  NUM_NODES  STATUS
# gke-demo-cluster  us-east1-d  1.15.12-gke.17    .............  n1-standard-1  1.15.12-gke.17  3         RUNNING
```

+ Authenticate to a GKE Cluster
If you created the cluster from the web console or on a different machine from the one you are currently working on, you need to run the following command to grant the kubectl tool the access to your cluster.
```
$ gcloud container clusters get-credentials ${CLUSTER_NAME}
```
If you created the cluster with your current machine with the gcloud container clusters create command, the authentication to the cluster is already set up.
+ Delete cluster
```
gcloud container clusters delete wecloudchatcluster --zone=us-east1-d
```


You can watch the status by running 'kubectl --namespace default get services -o wide -w my-nginx-nginx-ingress-controller'

一些基础知识点：
+ stateful v.s. stateless application  
  
**Stateful applications** save data to persistent disk storage for use by the server, by clients, and by other applications. An example of a stateful application is a database or key-value store to which data is saved and retrieved by other applications.

**Stateless applications** are applications which do not store data or application state to the cluster or to persistent storage. Instead, data and application state stay with the client, which makes stateless applications more scalable. For example, a frontend application is stateless: you deploy multiple replicas to increase its availability and scale down when demand is low, and the replicas have no need for unique identities.