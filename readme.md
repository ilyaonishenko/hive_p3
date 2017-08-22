# Hadoop part 2
Project consists of 2 modules: web application and yarn job. Every module has it's own build.sbt and own configuration.

### Web Application
To run web application go to scalatra-auth folder and run ```sbt``` and then
```sh
> container:start
```
After this you can go to ```http://localhost:8080```
Pay extra attention at the path variablies in your system, and commands in json you send to the server

### Yarn Task
Project compiles in to uber jar using command:
```sh
$ sbt clean compile assembly
```
This jar is started by REST API from web application (due to the next command):
```sh
$ /usr/local/hadoop/hadoop-2.8.1/bin/hadoop jar /home/ilia/IdeaProjects/hadoop_p2/yarn-task/target/scala-2.12/yarn_task-assembly-1.0.jar com.example.ApplicationMaster /user/ilia/jars/hadoop_p2-assembly-1.0.jar 1
```
If you want to run jar without web application, you can use the same command, just change the main class to ```com.example.Client```

Usefull links:
http://localhost:8088/cluster
https://hadoop.apache.org/docs/stable/hadoop-project-dist/hadoop-common/SingleCluster.html

Maybe I should post here hadoop configuration files