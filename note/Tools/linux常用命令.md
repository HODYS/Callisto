# Linux常用命令

### ssh
```
ssh -i key_file.pem username@instance-dns
```

动态端口转发
ssh -i key_file.pem -N -D 8123 username@instance-dns

ssh -keygen
见：https://www.ssh.com/ssh/keygen/
```
ssh-keygen -t rsa -C abc@abc.cn
```

### scp
上传本地文件到服务器
```
scp  -i  ~/.ssh/id_rsa /path/filename username@servername:/path
``` 
从服务器上下载文件 
```
scp username@servername:/path/filename /Users/mac/Desktop
```
拷贝整个文件夹
```
scp -r username@servername:/path /Users/mac/Desktop
```
可以用-i命令增加身份标识

sftp??
exit

## 文件操作
### cat
它用于创建包含内容的文件。用法较多，见https://www.geeksforgeeks.org/cat-command-in-linux-with-examples/

### touch
它用于创建没有任何内容的文件。使用touch命令创建的文件为空。当用户在创建文件时没有要存储的数据时，可以使用此命令。
```
touch File1_name File2_name File3_name 
```

### echo
快速生成一份文本文件  
echo "asdf" > test.txt

### rm
sudo rm -rf index.html

### mkdir  
创建新文件夹

### ls
ls -l xx.txt

### pwd
查看当前路径

### cp
是copy的缩写，此命令用于复制文件或一组文件或目录。
```
cp [OPTION] Source Destination
cp [OPTION] Source Directory
cp [OPTION] Source-1 Source-2 Source-3 Source-n Directory
```

### head
head 命令可用于查看文件的开头部分的内容，有一个常用的参数 -n 用于显示行数，默认为 10，即显示 10 行的内容。
```
head -n 5 runoob_notes.log
```

## 权限操作
### chmod
```
chmod 400 key_file.pem
```

```
chomod +x runner.sh
```

### usermod
usermod可用来修改用户帐号的各项设定。
-c = We can add comment field for the useraccount.
-d = To modify the directory for any existing user account.
-e = Using this option we can make the account expiry in specific period.
-g = Change the primary group for a User.
-G = To add a supplementary groups.
-a = To add anyone of the group to a secondary group.
-l = To change the login name from tecmint to tecmint_admin.
-L = To lock the user account. This will lock the password so we can’t use the account.
-m = moving the contents of the home directory from existing home dir to new dir.
-p = To Use un-encrypted password for the new password. (NOT Secured).
-s = Create a Specified shell for new accounts.
-u = Used to Assigned UID for the user account between 0 to 999.
-U = To unlock the user accounts. This will remove the password lock and allow us to use the user account.
**Examples**:
If you need to add a user to any one of the supplementary group, you can use the options ‘-a‘ and ‘-G‘. For example, here we going to add a user account tecmint_test0 with the wheel user.
```
usermod -a -G wheel tecmint_test0
```
```
# whoami: returns the current Linux user
sudo usermod -aG docker $(whoami)
```
详见[tecmint](https://www.tecmint.com/usermod-command-examples/)和[菜鸟教程](https://www.runoob.com/linux/linux-comm-usermod.html)


### tar  
用于从tar文件创建、提取或列出文件
以下命令解压archive.tar.gz的内容到当前路径
```
tar -xzvf archive.tar.gz
```
-x: e**x**tract，解压.  
-z: 使用gzip。Gzip是Unix和Linux系统的标准文件压缩形式。
-v: 也被叫做“verbose”模式，可以在终端中显示整个过程。
-f: 允许你指定文件（**f**ile）名称。

```
tar -xzvf archive.tar.gz -C /tmp
```
解压到/tmp目录下。
```
tar -czvf archive.tar.gz /home/ubuntu/Downloads /usr/local/stuff /home/ubuntu/Documents/notes.txt
```
将/home/ubuntu/Downloads目录, the /usr/local/stuff目录, 和/home/ubuntu/Documents/notes.txt文件打包为archive.tar.gz  
zvf命令见上。  
-c:压缩（**c**ompress）  
可以使用--exclude排除掉一些文件/文件夹  
```
tar -czvf archive.tar.gz /home/ubuntu --exclude=/home/ubuntu/Downloads --exclude=/home/ubuntu/.cache
```

### wget
### curl
与wget的区别：
wget is command line only. There's no lib or anything, but curl 's features are powered by libcurl. curl supports FTP , FTPS , HTTP , HTTPS , SCP , SFTP , TFTP , TELNET , DICT , LDAP , LDAPS , FILE , POP3 , IMAP , SMTP , RTMP and RTSP . ... wget only offers plain HTTP POST support.


以下摘自[this page](https://www.geeksforgeeks.org/curl-command-in-linux-with-examples/)。curl is a command line tool to transfer data to or from a server, using any of the supported protocols (HTTP, FTP, IMAP, POP3, SCP, SFTP, SMTP, TFTP, TELNET, LDAP or FILE).
```
curl -sL https://aka.ms/InstallAzureCLIDeb 
```
### zcat  
zcat命令用于不真正解压缩文件，就能显示压缩包中文件的内容的场合。

### less  
通常用于查看文件，或者并通过less分页显示（管道）
```
zcat < pageviews-20180310-000000.gz | less
```

unzip：用于解压缩由zip命令压缩的“.zip”压缩包。-d指定文件解压缩后所要存储的目录。
```
unzip test.zip -d /tmp
```

## 储存设备
### lsblk
lsblk命令 用于列出所有可用块设备的信息，而且还能显示他们之间的依赖关系，但是它不会列出RAM盘的信息。块设备有硬盘，闪存盘，cd-ROM等等
Use the lsblk -f command to get information about all of the devices attached to the instance.

Use the file -s command to get information about a specific device, such as its file system type
```
sudo file -s /dev/xvdf
```

 If you have an empty volume, use the mkfs -t command to create a file system on the volume.
 ```
 sudo mkfs -t xfs /dev/xvdf
 ```
 If you get an error that mkfs.xfs is not found, use the following command to install the XFS tools and then repeat the previous command:
 ```
 sudo yum install xfsprogs
 ```

 ### export  
 Linux export 命令用于设置或显示环境变量。
```shell
export AWS_ACCESS_KEY_ID="YOUR_ACCESS_KEY_HERE"
export AWS_SECRET_KEY="YOUR_SECRET_KEY_HERE"
```

You can then access them from within your code, for example, using System.getenv("AWS_ACCESS_KEY_ID") in Java, os.environ['AWS_ACCESS_KEY_ID'] in Python, or ${AWS_ACCESS_KEY_ID} in Bash.


## 网络工具
+ netstat  

netstat - atulnp会显示所有端口和所有对应的程序，用grep管道可以过滤出想要的字段

-a ：all，表示列出所有的连接，服务监听，Socket资料
-t ：tcp，列出tcp协议的服务
-u ：udp，列出udp协议的服务
-n ：port number， 用端口号来显示
-l ：listening，列出当前监听服务
-p ：program，列出服务程序的PID
见[这个链接](https://blog.csdn.net/weixin_41755830/article/details/80519390)

## Java
+ java

java -cp 和 -classpath 一样，是指定类运行所依赖其他类的路径，通常是类库和jar包，需要全路径到jar包，多个jar包之间连接符：window上分号“;”.Linux下使用“:”。详见[网页](https://www.cnblogs.com/klb561/p/10513575.html)。
windows环境：
```
java -cp .;d:\work\other.jar;d:\work\my.jar packname.mainclassname 
```

linux环境：
```
java -cp .:/hone/myuser/work/other.jar:/hone/myuser/work/my.jar packname.mainclassname 
```