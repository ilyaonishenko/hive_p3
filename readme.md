# Hive part 3

### HBase useful commands^

To insert data into hbase table airports from file airports.csv:
```sh
hbase org.apache.hadoop.hbase.mapreduce.ImportTsv -Dimporttsv.separator=,  -Dimporttsv.columns=HBASE_ROW_KEY, info:airport, info:city, info:state,info:country,info:lat,info:long airports1 /tasks/task7/airports.csv
```

To create hive table from hbase table:
```sql
create external table hbase_airports(iata string, airport string, city string, state string, country string, lat float, long float)
stored by 'org.apache.hadoop.hive.hbase.HBaseStorageHandler'
with serdeproperties("hbase.columns.mapping"=":key,info:airport,info:city,info:state,info:country,info:lat,info:long")
tblproperties("hbase.table.name"="airports1");
```

WebHCat listening port 50111

web application created using akka http.  all information in Main.scala (please do not look at the code)!!