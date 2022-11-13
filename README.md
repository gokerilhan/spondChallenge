# spondChallenge

Spond challenge app for retrieving NASA API data...

Used Spring Boot with MongoDb for caching purposes.

sample API call for finding closest top 10 asteroids:

http://localhost:8080/getStats?startDate=2022-10-16&endDate=2022-10-17


sample API call for finding the asteroid with largest diameter in a given year
http://localhost:8080/getLargest?year=2021

