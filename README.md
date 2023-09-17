
# Spring MicroService
example on Spring MicroService with Spring Config Cloud, GateWay, Eureka, Resilience4J


 
# instructions

- This Repo contain 7 projects
  - **Eureka** : for discover each MicorServices without having to hard code the host and port anywhere
      - we registry all project in **Eureka** [Api-GateWay - Currency-Conversion - Currency-Exchange]
  - **Api-GateWay** : for handle one port for all MicorServices Apis
  - **spring-cloud-config-server** : acts as a centralized config mechanism for storing config of all MicorServices
     - we conect all  MicorServices [limits-service - Currency-Conversion - Currency-Exchange] with **spring-cloud-config-server**
     - only limits-service get its config from **spring-cloud-config-server** just for testing not else
     -  before using **spring-cloud-config-server** must push **limits-service-config.properties** to git
  - **limits-service-config.properties** : where **spring-cloud-config-server** store its config
  - **limits-service** :  MicorService get its config from **spring-cloud-config-server** As I said before
  - **Currency-Conversion** :  MicorService convert currenct amount you enter to Country currency you want
     - example : http://localhost:8100/currency-conversion/from/EUR/to/INR/quantity/80
  - **Currency-Exchange** : MicorService tell as the currency exchange from country currency to another


> [!IMPORTANT]
> **Currency-Conversion MicorService** get the currency exchange number from **Currency-Exchange MicorService**.

> [!WARNING]
> in **spring-cloud-config-server** application.properties change spring.cloud.config.server.git.uri to your location.\
> this uri is the location where **spring-cloud-config-server** store its config.
