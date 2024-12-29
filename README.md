# spring-boot3-batch-sample


sample application for spring-boot3-batch with kotlin

multiple data source, multiple job, multiple step

## requirements

amazon corretto 21
docker

## getting started

### create database

```shell
$ cd ./database
$ docker-compose up -d
```

### run application

insert csv data to database table

```shell
$ ./gradlew bootRun --args='--spring.batch.job.name=insertCsvDataJob'# 
```

select data from database table

```shell
$ ./gradlew bootRun --args='--spring.batch.job.name=selectDataJob';
```
