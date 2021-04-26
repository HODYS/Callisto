# Pandas常用命令

## DataFrame
### 列标签
全部重命名
```
new_col = ['new1', 'new2',... , 'newn']
dataframe.columns = new_col
```
部分重命名
```
```

### 数据选取
并列选取
```python
df.loc[(df['A']=='a') & df['A']=='b') ]  # 此处只能用&，用and会报错，此外也要用圆括号括起来 
```

索引
https://www.shanelynn.ie/select-pandas-dataframe-rows-and-columns-using-iloc-loc-and-ix/



启动虚拟环境
source ./virtualenv/bin/activate