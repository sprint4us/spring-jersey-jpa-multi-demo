# spring-jersey-jpa-multi-demo

[![Build Status](https://travis-ci.org/sprint4us/spring-jersey-jpa-multi-demo.svg?branch=master)](https://github.com/sprint4us/spring-jersey-jpa-multi-demo)

![Intended Java technology stack](http://ibin.co/3EZ7vQAYb8cT.png)

---

Install the artifacts to local repos
>$ mvn clean install

Run the server
>$ cd spring-classic-jersey-jpa-demo
>$ mvn jetty:run 

Or alternatively
>$ cd spring-boot-jersey-jpa-demo
>$ mvn spring-boot:run # run the server

Run the client
>$ curl --data France http://localhost:8080/demo/create/country
>{"id":1,"name":"France","languages":[]}

>$ curl --data "id=1&l=English&p=39" http://localhost:8080/demo/update/country
>{"id":1,"name":"France","languages":[{"id":1,"name":"English","percentage":39}]}

>$ curl "http://localhost:8080/demo/search/percentage?c=France&l=English"
>France has 39% English as foreign language.

Now, time to kick off Travis CI ☺
