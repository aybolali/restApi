[![CircleCI](https://dl.circleci.com/status-badge/img/gh/aybolali/restApi/tree/master.svg?style=svg)](https://dl.circleci.com/status-badge/redirect/gh/aybolali/restApi/tree/master)

RESTFUL APIs Using Spring WebFlux.

First of all, we activate the docker container for MongoDB
![Снимок экрана (1120)](https://github.com/aybolali/restApi/assets/110097999/1c123dea-10a0-47c7-b2ab-5b57e94a19e0)

Then running the application we can now get all Categories with the request GET 
![Снимок экрана (1121)](https://github.com/aybolali/restApi/assets/110097999/871a461e-e8a0-4536-b8d6-9c9d0a2a488d)

For finding certain data with identity ID
![Снимок экрана (1141)](https://github.com/aybolali/restApi/assets/110097999/f3813373-3fd2-4da7-8aff-fc5021645d51)

Then we will create another new data with a POST request
![Снимок экрана (1180)](https://github.com/aybolali/restApi/assets/110097999/c84095d3-6d94-4a31-b636-c66029adc6aa)

Verifying that we created an object by getting its ID with the request GET 
![Снимок экрана (1181)](https://github.com/aybolali/restApi/assets/110097999/0b531f89-d5de-47fb-a02a-c7ffa39b01d8)

Furthermore, we are now updating a created object with method PUT with its identity ID
![Снимок экрана (1186)](https://github.com/aybolali/restApi/assets/110097999/c184f8a8-cb15-4844-91ad-afd0f156bdc6)

Checking by the end of the list we here make sure that it's updated with its identical ID 1 which a description value from "2" to "123123"
![Снимок экрана (1186)](https://github.com/aybolali/restApi/assets/110097999/424e5613-4577-4833-a137-62a119b0b7ec)

The last method that remains is the DELETE method. Here we give some ID for targeting which object to remove from the database with the method.
![Снимок экрана (1189)](https://github.com/aybolali/restApi/assets/110097999/9ca61341-fe06-4042-bad1-b05ef692e45f)

For verifying by checking the end of the list we are making sure that the last newly created object is deleted.
![Снимок экрана (1188)](https://github.com/aybolali/restApi/assets/110097999/73bb7cf2-eefe-4a1c-ae5c-c1d151b19fc7)




