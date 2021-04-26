# Azure

+ login
```
az login
```

+ subscription
```
az account list --output table --refresh
az account set --subscription <name or id>
```
+ resource providers registration
```
az provider register --namespace Microsoft.ContainerService
```
+ resource group
```
RESOURCE_GROUP_NAME=aksDemoResourceGroup
az group create --name ${RESOURCE_GROUP_NAME} --location eastus
az group delete --name ExampleResourceGroup
```
+ create image/instance from template
```
az deployment group create --name "cloud-course-vm-image" --resource-group "$RESOURCE_GROUP_NAME" --template-file "template.json" --parameters "parameters.json"

az deployment group create --name "cloud-course-vm" --resource-group "$RESOURCE_GROUP_NAME" --template-file "template.json" --parameters "parameters.json
```
+ network 
```
az network public-ip list --resource-group $RESOURCE_GROUP_NAME
```

## Azure Kubernetes Service (AKS) 
Create an AKS cluster and set up the integration between the AKS cluster and the ACR instance.
```
CLUSTER_NAME=myAKSCluster
az aks create --name ${CLUSTER_NAME} --resource-group ${RESOURCE_GROUP_NAME} --attach-acr ${ACR_NAME} --generate-ssh-keys
```

Run the following commands to set up access to the AKS cluster. Note that you should always remember to run this az aks get-credentials command once before you can use kubectl to manage an AKS cluster.
```
az aks get-credentials --resource-group=${RESOURCE_GROUP_NAME} --name=${CLUSTER_NAME}
```

## Azure Container Registry (ACR)
Create a container registry. 
```
ACR_NAME=<unique_acr_name>
az acr create --name ${ACR_NAME} --resource-group ${RESOURCE_GROUP_NAME} --location eastus --sku Basic
```

Log in to the container registry
```
az acr login --name ${ACR_NAME}
```

You can now verify the fully qualified name of your ACR login server with the following command.
```
az acr list --resource-group ${RESOURCE_GROUP_NAME} --query "[].{acrLoginServer:loginServer}" --output table
```

## Storage
```
# name your storage account
# Note: the name must be globally unique and lowercase alphanumeric
ACCOUNT_NAME="the storage account name"

# create the storage account
az storage account create --location eastus --resource-group $RESOURCE_GROUP_NAME --name $ACCOUNT_NAME

# create a private container under the storage account
CONTAINER=system
az storage container create --account-name $ACCOUNT_NAME --name $CONTAINER

# fetch and note down the account key of the storage account
az storage account keys list --resource-group $RESOURCE_GROUP_NAME --account-name $ACCOUNT_NAME

# assign the account key to ACCOUNT_KEY
ACCOUNT_KEY="the value of access key of the storage account"
SRC_ACCOUNT="cloudcourse"
VHD_RELATIVE_PATH="Microsoft.Compute/Images/vhds/Project-Image-v-osDisk.7a770068-186d-4dfd-b539-d0d53db3e47e.vhd"

# start the asynchronous az storage blob copy command which may take 5-10 minutes
az storage blob copy start \
      --destination-blob $VHD_RELATIVE_PATH \
      --destination-container $CONTAINER \
      --account-key $ACCOUNT_KEY \
      --account-name $ACCOUNT_NAME \
      --source-uri https://$SRC_ACCOUNT.blob.core.windows.net/$CONTAINER/$VHD_RELATIVE_PATH

# you need to wait until the value of "properties.copy.status" that is returned by the command below
# changes from "pending" to "success"
az storage blob show \
              --name $VHD_RELATIVE_PATH \
              --container-name $CONTAINER \
              --account-name $ACCOUNT_NAME

# below is an example to monitor the progress using the Linux tools "watch" and "jq"
# "jq" is a JSON processor that you may want to install on your own
watch "az storage blob show \
      --name $VHD_RELATIVE_PATH \
      --container-name $CONTAINER \
      --account-name $ACCOUNT_NAME | jq '.properties.copy'"
```
