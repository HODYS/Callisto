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

创建分支命令
git branch (branchname)

切换分支命令
git checkout (branchname)

创建的同时切换分支，-b：branch name
git checkout -b (branchname)

合并分支命令
git merge 

删除分支命令，-d:delete
git branch -d (branchname)

关于branch使用的详细说明
见[3.2 Git Branching - Basic Branching and Merging](https://git-scm.com/book/en/v2/Git-Branching-Basic-Branching-and-Merging)

git diff 命令比较文件的不同
尚未缓存的改动：git diff
查看已缓存的改动： git diff --cached
查看已缓存的与未缓存的所有改动：git diff HEAD
显示摘要而非整个 diff：git diff --stat

