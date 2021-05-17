# Vim
参考[在vim中快速复制粘贴多行](https://blog.csdn.net/nmjhehe/article/details/83788780)

+ 显示行号
```
:set number 或者 :set nu 
```
+ 不显示行号
```
:set nonumber 或者 :set nonu 
```
+ 多行复制与移动
将第9行至第15行的数据，复制到第16行
```
：9，15 copy 16  或 ：9，15 co 16
由此可有：
：9，15 move 16  或 :9,15 m 16 将第9行到第15行的文本内容到第16行的后面  
```

撤销：u

恢复撤销：Ctrl + r

1.复制

1）单行复制

在命令模式下，将光标移动到将要复制的行处，按“yy”进行复制；

2）多行复制 在命令模式下，将光标移动到将要复制的首行处，按“nyy”复制n行；其中n为1、2、3……

【yy】 复制光标所在的那一行
【nyy】 复制光标所在的向下n行

2.粘贴

在命令模式下，将光标移动到将要粘贴的行处，按“p”进行粘贴

【p,P】 p为将已经复制的数据在光标下一行粘贴；P为将已经复制的数据在光标上一行粘贴

3、删除

删除一行：dd

删除一个单词/光标之后的单词剩余部分：dw

删除当前字符：x

光标之后的该行部分：d$

文本删除

dd 删除一行

d$ 删除以当前字符开始的一行字符

ndd 删除以当前行开始的n行

dw 删除以当前字符开始的一个字

ndw 删除以当前字符开始的n个字

全选（高亮显示）：按esc后，然后ggvG或者ggVG

全部复制：按esc后，然后ggyG

全部删除：按esc后，然后dG

[替换](https://blog.51cto.com/andyss/131652)
语法为 :[addr]s/源字符串/目的字符串/[option]
全局替换命令为：:%s/源字符串/目的字符串/g
[addr] 表示检索范围，省略时表示当前行。
如：“1，20” ：表示从第1行到20行；
“%” ：表示整个文件，同“1,$”；
“. ,$” ：从当前行到文件尾；
s : 表示替换操作
[option] : 表示操作类型
如：g 表示全局替换; 
c 表示进行确认
p 表示替代结果逐行显示（Ctrl + L恢复屏幕）；
省略option时仅对每行第一个匹配串进行替换；
如果在源字符串和目的字符串中出现特殊字符，需要用”\”转义