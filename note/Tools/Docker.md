# Docker
全文参考[官方文档](https://docs.docker.com/engine/reference/commandline/docker/)
## Docker CLI
+ docker build
The docker build command builds Docker images from a Dockerfile and a “context”. A build’s context is the set of files located in the specified PATH or URL. 

以下命令最后的“.”，代表Dockerfile或者context就在当前目录下
```
# --rm: Remove intermediate containers after a successful build
# --tag: Name and optionally tag the image in the 'name:tag' format
# 
# Please note the `.` at the end of the docker build command, and you cannot omit it.
# `.` (which means the current working directory) is passed as the `PATH`, 
# and all the files in the current working directory will get packaged and sent to the Docker daemon
# not just the ones listed to ADD in the Dockerfile

docker build --rm --tag clouduser/primer:latest .
```
```
docker build --no-cache --rm --tag clouduser/task1:latest -f ./src/main/docker/Dockerfile .
```

docker build 的其他相关option
```
--no-cache		：Do not use cache when building the image
-a=[]           : Attach to `STDIN`, `STDOUT` and/or `STDERR`
-t              : Allocate a pseudo-tty
--sig-proxy=true: Proxy all received signals to the process (non-TTY mode only)
-i              : Keep STDIN open even if not attached
```

+ docker images
查看当前images
```
docker images
```

+ docker run  
The docker run command first creates a writeable container layer over the specified image, and then starts it using the specified command. That is, docker run is equivalent to the API /containers/create then /containers/(id)/start. 
```
# -d: When starting a Docker container, you must decide if you want to run 
# the container in the background in a "detached" mode or in the default foreground mode.
# `-d` starts the container in detached mode
#
# -p: map a single port or range of container ports to the host port
# the mapping has the following format: "hostPort:containerPort"

docker run -d -p 80:80 clouduser/primer:latest
# A docker will run in the background
# Note the usage of command docker -p <hostPort>:<containerPort>
# Sample output:
# e32b87a5b9482a3899de8afa65a05efdfff093951cc3ec0576006b8b10a1d963
```
The container runs a web server that listens to the container at port 80, and the container port 80 is mapped to the host VM port 80 as per -p 80:80. As the host VM post 80 is open to the public, you can now access the containerized server from the Internet.


+ docker rmi
Remove one or more images. -f: forced to remove
```
$ docker images

REPOSITORY                TAG                 IMAGE ID            CREATED             SIZE
test1                     latest              fd484f19954f        23 seconds ago      7 B (virtual 4.964 MB)
test                      latest              fd484f19954f        23 seconds ago      7 B (virtual 4.964 MB)
test2                     latest              fd484f19954f        23 seconds ago      7 B (virtual 4.964 MB)

$ docker rmi fd484f19954f

Error: Conflict, cannot delete image fd484f19954f because it is tagged in multiple repositories, use -f to force
2013/12/11 05:47:16 Error: failed to remove one or more images

$ docker rmi test1:latest

Untagged: test1:latest

$ docker rmi test2:latest

Untagged: test2:latest


$ docker images

REPOSITORY                TAG                 IMAGE ID            CREATED             SIZE
test                      latest              fd484f19954f        23 seconds ago      7 B (virtual 4.964 MB)

$ docker rmi test:latest

Untagged: test:latest
Deleted: fd484f19954f4920da7ff372b5067f5b7ddb2fd3830cecd17b96ea9e286ba5b8
```

+ docker tag
Docker uses the image tag to determine what repository the image belongs to. To push any local image to the Google Container Registry, you need to first tag the image with the Google Container Registry name and then push the image.

The command to tag your images is as follows:
```
docker tag [SOURCE_IMAGE] [HOSTNAME]/[PROJECT-ID]/[IMAGE]:[TAG]
```
```
GCP_PROJECT_ID=your_gcp_id
docker tag clouduser/primer:latest us.gcr.io/${GCP_PROJECT_ID}/clouduser/gcr-demo
```

+ docker push
Push an image or a repository to a registry
```
docker push [OPTIONS] pushNAME[:TAG]
```
```
docker push ${ACR_NAME}.azurecr.io/clouduser/hello-app:v1
```

+ docker ps
Stop the local running container. 

## Dockerfile
关于build context，摘抄一段官方文档的[Understand build context](https://docs.docker.com/develop/develop-images/dockerfile_best-practices/#understand-build-context)

```
Build context example

Create a directory for the build context and cd into it. Write “hello” into a text file named hello and create a Dockerfile that runs cat on it. Build the image from within the build context (.):

mkdir myproject && cd myproject
echo "hello" > hello
echo -e "FROM busybox\nCOPY /hello /\nRUN cat /hello" > Dockerfile
docker build -t helloapp:v1 .

Move Dockerfile and hello into separate directories and build a second version of the image (without relying on cache from the last build). Use -f to point to the Dockerfile and specify the directory of the build context:

mkdir -p dockerfiles context
mv Dockerfile dockerfiles && mv hello context
docker build --no-cache -t helloapp:v2 -f dockerfiles/Dockerfile context
```
list of instructions：
ADD
COPY
ENV
EXPOSE
FROM
LABEL
STOPSIGNAL
USER
VOLUME
WORKDIR
ONBUILD

+ ADD
COPY takes in a src and destination. It only lets you copy in a local file or directory from your host (the machine building the Docker image) into the Docker image itself.

ADD lets you do that too, but it also supports 2 other sources. First, you can use a URL instead of a local file / directory. Secondly, you can extract a tar file from the source directly into the destination.

+ WORKDIR
The WORKDIR instruction sets the working directory for any RUN, CMD, ENTRYPOINT, COPY and ADD instructions that follow it in the Dockerfile. If the WORKDIR doesn’t exist, it will be created even if it’s not used in any subsequent Dockerfile instruction.
```
WORKDIR /path/to/workdir
```