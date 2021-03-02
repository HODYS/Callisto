# Terraform 使用指南
terraform init      Initialize a Terraform working directory
terraform validate  Validates the Terraform files
terraform fmt       Rewrites config files to canonical format
terraform plan      Generate and show an execution plan
terraform apply     Builds or changes infrastructure

terraform destroy   Destroy Terraform-managed infrastructure
一般会销毁所有用terraform部署的资源，如果需要指定特定的资源，使用：
```
terraform destroy --target=RESOURCE_TYPE.NAME.
```