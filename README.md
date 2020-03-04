# Running H2O Container on OpenShift Container Platform

This repository contains yaml files that shall be consumed "as-is" on OCP/K8.
The docker image(s) involved are custom built for this particular usecase.

### Steps to build Docker Images for this usecase

We will now build the docker image for this usecase.

```
git clone https://github.com/krishvoor/h2o_recipe_ocp
cd $PWD/h2o_recipe_ocp/docker/
docker build -t h2o:ppc64le .
cd ../
```

### Steps to deploy service/deployment files for h2o

We will now look how to deploy the service/deployment files.

```
oc create -f  h2o-service.yaml
oc create -f h2o-deployment.yaml
```

Upon successfully creation of respective Service/Deployment, you will see the following Output as -

```
[root@p1216-kvm1 h2o_recipe_ocp]# oc create -f  h2o-service.yaml
service/h2o created
[root@p1216-kvm1 h2o_recipe_ocp]# oc create -f h2o-deployment.yaml 
deployment.extensions/h2o created
[root@p1216-kvm1 h2o_recipe_ocp]#
```

To check the status of pods in your desired namespace issue the following
```
[root@p1216-kvm1 h2o_recipe_ocp]# oc get po -n harsha
NAME                   READY     STATUS    RESTARTS   AGE
h2o-5fcc88f885-8jdck   1/1       Running   0          1m
[root@p1216-kvm1 h2o_recipe_ocp]# 
```

We will now login to the container and run sample example -

```
[root@p1216-kvm1 h2o_recipe_ocp]# oc get po -n harsha
NAME                   READY     STATUS    RESTARTS   AGE
h2o-5fcc88f885-8jdck   1/1       Running   0          1m
[root@p1216-kvm1 h2o_recipe_ocp]# oc rsh h2o-5fcc88f885-8jdck
$ ls   	
Customer_Retention_Test.csv  Main.class  Main.java  README.txt	example.csv  license.sig  mojo2-runtime-javadoc.jar  mojo2-runtime.jar	pipeline.mojo  run_example.sh  test_run.sh
$ ./run_example.sh
+ MOJO_FILE=pipeline.mojo
+ CSV_FILE=example.csv
+ LICENSE_FILE=
+ CMD_LINE='java -Xmx5g -Dai.h2o.mojos.runtime.license.file= -cp mojo2-runtime.jar ai.h2o.mojos.ExecuteMojo'
+ cat
======================
Running MOJO2 example
======================

MOJO file    : pipeline.mojo
Input file   : example.csv

Command line : java -Xmx5g -Dai.h2o.mojos.runtime.license.file= -cp mojo2-runtime.jar ai.h2o.mojos.ExecuteMojo pipeline.mojo example.csv

+ java -Xmx5g -Dai.h2o.mojos.runtime.license.file= -cp mojo2-runtime.jar ai.h2o.mojos.ExecuteMojo pipeline.mojo example.csv
Mojo load time:  3.374 sec
satisfaction_v2.neutral or dissatisfied,satisfaction_v2.satisfied
0.003346353769302368,0.9966536462306976
0.9988932227133773,0.0011067772866226733
0.3798948675394058,0.6201051324605942
0.008342504501342773,0.9916574954986572
0.08051924407482147,0.9194807559251785
0.00212077796459198,0.997879222035408
0.9947872833581641,0.005212716641835868
0.9980462707753759,0.0019537292246241122
0.027300626039505005,0.972699373960495
0.0013456344604492188,0.9986543655395508
Time per row:   4.700 msec (total time:  47.000 msec)
$ 

```

The results tells us that we are 99.86% Satisfied with result & 0.13% Dissatisfied with the result
