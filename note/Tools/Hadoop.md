# Hadoop



+ TextInputFormat  
An InputFormat for plain text files. Files are broken into lines. Either linefeed or carriage-return are used to signal end of line. Keys are the position in the file, and values are the line of text..

hadoop fs -mkdir /input

hadoop fs -ls -R /

hadoop fs -put ./input/ /input

hadoop fs -rm -r /output

hadoop fs -get <src> <localdest>

hadoop fs -copyToLocal /output .

 you should change the owner of the HFiles recursively to hbase,
hadoop fs -chown -R hbase:hbase /hfiles