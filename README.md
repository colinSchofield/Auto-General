![](logo.png)

# Auto and General Technical Test
##### Synopsis
This program has been written in Java 1.8 as a Spring Boot application. Both the `Tasks` and `Todo` RESTful endpoints have been 
implemented, with validation and are both running correctly. They may be tested by pointing your browser at [integrationTest](https://join.autogeneral.com.au/test/1.0/integrationTest?url=http%3A%2F%2F13.239.134.226%2Ftechtest%2F1.0).

The application itself is running as an EC2 off of a `t2.micro`, which is deployed into the default VPC. For the purpose of 
the **Todo list**, a DynamoDb NoSQL database was used, as this provides the best possible performance, scalability, resilience and cost.
 
The EC2 is situated behind an ELB and with a mindfulness to production, this would no doubt involve an ASG over multiple Availability Zones.

##### how to run the Application locally
You must have the following to run this application:
- Java 1.8 compiler 
- Apache maven 3 

Then do the following:
```
1. mvn clean install
2. mvn spring-boot:run
```

The first command will compile the program and ensure that all the unit test cases pass (note: this will generate a fair amount of logging). 

The second will run the console application, allowing you to connect locally to the application, should you wish, with your [browser](http://localhost/techtest/1.0/tasks/validateBrackets?input=1{()}2) i.e.: `http://localhost/techtest/1.0/tasks/validateBrackets?input=1{()}2`.
 
 **Note:** It is also available over the internet.

##### Test Cases

During the design and coding of this application, particularly attention has been allowed for TDD. The Intellij code coverage was run, 
revealing that **100%** of classes and **71%** of lines were covered by the Unit/Integration Tests. `Mockito` was used during testing.

##### Further Considerations and Issues

- At this stage I am running the application on port `80`, *only*. I did have it running under SSL (i.e. port `443`), but I did this by
using a self-signed signature, which did not play nicely with the Auto and General integration tests, so I have reverted it out, at this
 stage. Further reading indicates that we can get around this by applying a redirect, using an [Application Load Balancer](https://exampleloadbalancer.com/redirect_demo.html).
 
- In the past I have used Lambda and Serverless technology. This application is an ideal candidate for this approach. 
By deploying an Amazon API Gateway (and by using the Swagger configuration as Input) together with DynamoDb, this sounds like 
 the optimal solution and would be a tad cheaper. Note though, that I was hesitant to use this approach because it would not 
 have showcased my abilities with Spring Boot.

- Fully productionising this application, would require a DevOps engineer to bring the application under the CI/CD pipeline.
The application would probably be loaded into Terraform, the convention followed for creating IAM polices (i.e. allowing roles minimum access to DynamoDb) and the 
using Ansible/Puppet the configuration files. Also, the application currently is set to write `DEBUG` level of logs to the 
console, this would need to be changed, by storing them into a log file and perhaps deploying Kibana or whatever ELK stack 
is considered the norm at Auto and General.

Thanks for giving me the opportunity of facing this challenge, it was fun!

Colin Schofield   
e: colin_sch@yahoo.com  
p: 0448 644 233