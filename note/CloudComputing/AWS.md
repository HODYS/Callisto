# AWS全家桶介绍
Note only for cc course. 仅用作课程笔记。
## 
## EBS
Block device mapping:
Each instance that you launch has an associated root device volume, which is either an Amazon EBS volume or an instance store volume. You can use block device mapping to specify additional EBS volumes or instance store volumes to attach to an instance when it's launched.

see what is block device in:
https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/block-device-mapping-concepts.html

how to mount an EBS volume to an instance?
https://docs.aws.amazon.com/AWSEC2/latest/UserGuide/ebs-using-volumes.html

## Simple Storage Service (S3)
+ object storage service
+ Each object is stored in a bucket and retrieved via a unique, developer-assigned key
+ No traditional file system, instead using logical container(bucket) and objects can have names with / which can be used to preserve the hierarchy of existing files when uploading and downloading
  
Create bucket：
bucket name:
+ should be unique across all existing S3 bucket names so one way to name the bucket is to prefix it with your company's name.  The S3 bucket namespace is shared by all users of the S3 system. 
+ should be in lowercase.

Any object stored on S3 is also accessible via HTTP. You should be able to access your bucket from a browser using the URL: http://s3.amazonaws.com/<bucket-name>/<object-name>. Please note that you will need the appropriate permissions enabled for your bucket and object to allow for anonymous HTTP access.

## AWS Command Line Tool (aws-cli)
```
$ aws configure
AWS Access Key ID [None]: ********************
AWS Secret Access Key [None]: ********************
Default region name [None]: us-east-1
Default output format [None]: json
```
--dry-run can be added to check syntax instead of actually running the command

for ec2:
```
$ aws ec2 create-security-group --group-name devenv-sg --description "security group for development environment in EC2"

$ aws ec2 authorize-security-group-ingress --group-name devenv-sg --protocol tcp --port 22 --cidr 0.0.0.0/0

$ aws ec2 create-key-pair --key-name devenv-key --query 'KeyMaterial' --output text > devenv-key.pem

$ chmod 400 devenv-key.pem

$ aws ec2 run-instances \
    --image-id YOUR_AMI_ID \
    --security-group-ids SECURITY_GROUP_ID \
    --count 1 \
    --instance-type t2.micro \
    --key-name devenv-key \
    --query 'Instances[0].InstanceId'
```

for s3:
```
aws s3 help
aws s3 mb s3://thisisademo619
aws s3 ls s3://thisisademo619
aws s3 cp test.txt s3://thisisademo619
aws s3 cp s3://thisisademo619/test.txt .
aws s3 rm s3://thisisademo619/test.txt
aws s3 rb s3://thisisademo619 
aws s3 rb s3://thisisademo619 --force
```

for cloudwatch:
```
aws cloudwatch help
```