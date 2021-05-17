# Kubenetes
## 概念
Pod 和 Service
**Pods**
Pods are the smallest deployable units of computing that you can create and manage in Kubernetes.

A Pod (as in a pod of whales or pea pod) is a group of one or more containers, with shared storage and network resources, and a specification for how to run the containers.

**Service**
An abstract way to expose an application running on a set of Pods as a network service.
With Kubernetes you don't need to modify your application to use an unfamiliar service discovery mechanism. Kubernetes gives Pods their own IP addresses and a single DNS name for a set of Pods, and can load-balance across them.

## kubectl
详见[kubectl Cheat Sheet](https://kubernetes.io/docs/reference/kubectl/cheatsheet/)
+ install
```
sudo snap install kubectl --classic
```

+ create
```
kubectl create deployment hello-web --image=${ACR_NAME}.azurecr.io/clouduser/hello-app:v1
```
+ apply
Apply the configuration in YAML files.
```
kubectl apply -f .
```

+ get
Get the information. 对象可以是pods, services、deployments或者hpa。
```
# List all services in the default namespace
$ kubectl get services

# List all pods in the default namespace
$ kubectl get pods

# Retrieve the logs for a specific pod
$ kubectl logs POD_NAME
```

+ run
```
kubectl run -i --tty ubuntu --image=ubuntu:16.04 --restart=Never -- bash -il
```

+ exec
```
# The exec command will give you the terminal access inside of the pod
$ kubectl exec -it POD_NAME -- /bin/sh
```

+ logs
```
$ kubectl logs $POD_NAME
```

+ describe
```
$ kubectl describe hpa
```

+ config
Get the contexts(clusters) and switch context.
```
$ kubectl config get-contexts

$ kubectl config use-context CONTEXT_NAME
```