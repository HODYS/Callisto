# Kubenetes
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