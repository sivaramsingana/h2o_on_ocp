#Download base image ubuntu 18.04
FROM ubuntu
 
# Update Software repository
RUN apt-get update
 
# Install java from ubuntu repository
RUN apt-get install -y openjdk-8-jdk 
 
#Copy supervisor configuration
RUN useradd -ms /bin/bash h2o
RUN mkdir -p /home/h2o/experiment/
COPY experiment /home/h2o/experiment/
WORKDIR /home/h2o/experiment/
USER h2o
CMD tail -f /dev/null
