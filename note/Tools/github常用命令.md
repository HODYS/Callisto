# github常用命令
git init
git add .
git commit -m "first commit"
git remote add origin https://xxx/xxx.git
git push origin master

查看本地添加了哪些远程地址 
git remote -v

删除本地指定的远程地址  
git remote remove origin

从远程拉取所有内容
git fetch --all

reset 本地代码
git reset --hard origin/master

重启拉取对齐
git pull