FROM hseeberger/scala-sbt:8u222_1.3.5_2.13.1
WORKDIR /seprojectws
ADD . /seprojectse
CMD sbt run