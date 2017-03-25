# spring-jersey-jpa-multi-demo

[![Build Status](https://travis-ci.org/sprint4us/spring-jersey-jpa-multi-demo.svg?branch=master)](https://github.com/sprint4us/spring-jersey-jpa-multi-demo)

![Intended Java technology stack](http://ibin.co/3EZ7vQAYb8cT.png)

---

Requirements:
>JDK 8, Maven 3

>Tests themselves ran succssfully in local or hosted Linux box

Install the artifacts to local repos
>$ mvn clean install

Run the server
>$ cd spring-classic-jersey-jpa-demo && mvn jetty:run 

Run the client
```
$ curl --data France localhost:8080/demo/create/country
{"id":1,"name":"France","languages":[]}

$ curl --data "id=1&l=English&p=39" localhost:8080/demo/update/country
{"id":1,"name":"France","languages":[{"id":1,"name":"English","percentage":39}]}

$ curl "localhost:8080/demo/search/percentage?c=France&l=English"
France has 39% English as foreign language.
```

Alternatively, stop the running server, then run the secure server
>$ cd spring-boot-jersey-jpa-demo && mvn spring-boot:run

Run the secure client, and the responses same as above.
```
$ curl -k -u user:password --data France https://localhost:8443/demo/create/country
$ curl -k -u user:password --data "id=1&l=English&p=39" https://localhost:8443/demo/update/country
$ curl -k -u user:password "https://localhost:8443/demo/search/percentage?c=France&l=English"
```

Now, time to kick off Travis CI ☺
