## CMPE 275 Section 2, Fall 2016
### Lab 2 - MVC and Persistence

In this lab, you will build a mini web application to manage office phones and phone users through create, view, update, and delete. This application needs to be hosted in either Amazon EC2 or Google AppEngine. You must use Spring’s MVC framework for the UI implementation, and use JPA for the persistence.

Incomplete definitions of user, phone, and address are given below..

 ```java
package edu.sjsu.cmpe275.lab2;

public class User {
    private String id;
    private String firstname;
    private String lastname;
    private String title;
    private Address address;
    Private List<Phone> phones; // related to ORM
}

public class Phone {
    private String id;
    private String number; // Note, phone numbers must be unique
    private String description;
    private Address address;
}

public class Address {
    String street;
    String city;
    String state;
    String zip;
}

```

Relationship Between Objects
*	Each phone can be assigned to multiple users, and each user can have multiple phones.
*	Each phone (and user) has an address, which is an embedded object with four fields mapped to the corresponding four columns in the phone (or user) table.
*	When a person is deleted, all phones assigned to him are automatically unassigned from him.
*	A phone cannot be deleted if it is assigned to at least one user.

There are five types of requests your app need to support. For simplicity, no authentication or authorization is enforced for these requests. The specification below uses hostname to represent your DNS or IP.

1. Get a user as HTML
----

URL: <https://samplei-env.us-west-2.elasticbeanstalk.com/user/439>

Method: GET

```
This returns an HTML that renders the given user ID’s user record. The user fields are part of an HTML form.
```


![user_Html](https://cloud.githubusercontent.com/assets/14006620/20550970/13bb01c4-b0f0-11e6-83b7-0d1fc634c2ef.png)


*	Firstname, lastname, address, and title must be editable.
*	List of assigned phones must be shown: you must show at least the phone numbers. The phones are NOT editable here.
*	NO need to show the owners of the phones here.
*	The HTML page should contain two buttons, Update and Delete. When Update is clicked, it updates the user as specified in (4), using HTTP POST. When the Delete button is created, it deletes the user using HTTP DELETE.
*	It is up to you to decide the layout and look-and-feel of the rendered user record.
*	If the user of the given user ID does not exist, a customized 404 HTML page with the message “Sorry, the requested user with ID XXX does not exist.” Note: XXX is the ID specified in the request, and you MUST return HTTP error code 404 as well.

2. Get a user back as JSON
----

URL: <https://samplei-env.us-west-2.elasticbeanstalk.com/user/439?json=true>

Method: GET


```
This returns the given user’s record in JSON format.
```


![user_JSON](https://cloud.githubusercontent.com/assets/14006620/20550969/13bae3ba-b0f0-11e6-9d19-28373f4be244.png)


#### This JSON is meant for readonly, and is not an HTML page or form.
*	The content you put in the JSON must match what you have in (1).
*	All error handlings should be the same as the previous request in (1) as well.


3. Get the user creation HTML
----

URL: <https://samplei-env.us-west-2.elasticbeanstalk.com/user>

Method: GET


```
This returns an HTML form that should be almost the same as (1), except that
```


![User home](https://cloud.githubusercontent.com/assets/14006620/20550968/13b97386-b0f0-11e6-81eb-d2cc8d5bc081.png)



*	All fields including the ID should be initially empty and should be editable except the ID.
*	The page should contain one button, labelled Create. When Create is clicked, it creates the user as specified in (4), using HTTP POST.
*	The return code should follow the HTTP convention.


4. Create or update a user
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/user/666?firstname=XX&lastname=YY&title=abc&street=AAA&city=BBB&state=CCC&zip=95012>

Method: POST



![createPhone_Query](https://cloud.githubusercontent.com/assets/14006620/20550971/13bb5638-b0f0-11e6-9ef1-21904777600b.png)


```
This request creates or update the user for the given user ID.
```


*	For simplicity, all the user fields other than the ID (firstname, lastname, address, and title) are passed as query parameters, and you can assume the request always comes with all the fields specified.
*	The corresponding user should be create/updated accordingly.
*	In the end, the request returns the newly created/updated user in HTML, the same as

 GET https://hostname/user/userId


(5) Delete a user
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/user/666>

Method: DELETE

```
This request deletes the user for the given user ID.
```

![userDelete_Request](https://cloud.githubusercontent.com/assets/14006620/20550976/13ce367c-b0f0-11e6-8089-4fd182705a03.png)

```
Response
```

![userDelete_res](https://cloud.githubusercontent.com/assets/14006620/20550975/13ce15d4-b0f0-11e6-8172-a8c4e7cba152.png)



*	If the user does not exist, it should return the same 404 page as in (1) with error code 404.
*	Otherwise, delete the corresponding user, and redirect the request to the user creation page at https://hostname/user.
*	The phones should also unassigned from the user.



(6) Get a Phone as HTML
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/phone/39>

Method: GET


```
This returns an HTML that renders the phone of the given ID. The phone fields are part of an HTML form.
```


![phoneView](https://cloud.githubusercontent.com/assets/14006620/20550977/13ce35dc-b0f0-11e6-9b56-36b94c28cf67.png)


*	Phone number, description, and address must be editable.
*	List of assigned users must be shown: you must show at least the assigned users’ names and IDs. You must also provide widgets to allow add/remove of users. Do NOT show other phones a user is assigned.
*	The HTML page should also contain two buttons, Update and Delete. When Update is clicked, it updates the phone as specified in (4), using HTTP POST. When the Delete button is created, it deletes the phone using HTTP DELETE.
*	A phone cannot be deleted if there is still a user assigned to it: a 400 error should be returned for such request.
*	It is up to you to decide the layout and look-and-feel of the rendered phone record.
*	If the phone of the given phone ID does not exist, a customized 404 HTML page with the message “Sorry, the requested phone with ID XXX does not exist.” Note: XXX is the ID specified in the request, and you MUST return HTTP error code 404 as well.



(7) Get a phone back as JSON
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/phone/39?json=true>

Method: GET

```
This returns the given phone’s record in JSON format.
```

![viewPhone_JSON](https://cloud.githubusercontent.com/assets/14006620/20550980/13ddaaa8-b0f0-11e6-9803-3b2e66c6e488.png)



#### This JSON is meant for readonly, and is not an HTML page or form.

*	The content you put in the JSON must match what you have in (6).
*	All error handlings should be the same as the previous request in (6) as well.


(8) Get the phone creation HTML
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/phone>

Method: GET

```
This returns an HTML form that should be almost the same as (1), except that
```
![phoneIndex](https://cloud.githubusercontent.com/assets/14006620/20550981/13e09ff6-b0f0-11e6-908f-455c2bcedb02.png)


*	All fields including the ID should be initially empty and should be editable except the ID.
*	The page should contain one button, labelled Create. When Create is clicked, it creates the phone as specified in (4), using HTTP POST.
*	The return code should follow the HTTP convention.


(9) Create or update a phone
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/phone/39?number=XX&description=YY&street=AAA&city=BBB&state=CCC&zip=95012&users[]=666&users[]=439>

Method: POST


```
This request creates or update the phone for the given phone ID.
```

![phoneUpdate](https://cloud.githubusercontent.com/assets/14006620/20550984/13e1dd8a-b0f0-11e6-9cf9-7a323c7a40f2.png)


```
Response
```

![phoneUpdate_Res](https://cloud.githubusercontent.com/assets/14006620/20550982/13e1d556-b0f0-11e6-919e-174e444e3a36.png)


*	For simplicity, all the phone fields other than the ID (number and description) are passed as query parameters, and you can assume the request always comes with all the fields specified. The users assignments must be taken care of too. (TBA: add the format users are passed in.)
*	The corresponding phone should be create/updated accordingly.
*	In the end, the request returns the newly created/updated phone in HTML, the same as GET https://hostname/phone/phoneId


(10) Delete a phone
----

URL: <http://samplei-env.us-west-2.elasticbeanstalk.com/phone/39>

Method: DELETE

```
This request deletes the phone for the given phone ID.
```

![userDelete_userAssigned](https://cloud.githubusercontent.com/assets/14006620/20550988/13f387ce-b0f0-11e6-8515-6fa0e80bd7b1.png)

```
If the user is removed
```

![userDelete_success](https://cloud.githubusercontent.com/assets/14006620/20550990/13f4571c-b0f0-11e6-9502-29d7e35a9922.png)


![userDelete_alert](https://cloud.githubusercontent.com/assets/14006620/20550989/13f44948-b0f0-11e6-875d-894d39497ef4.png)

```
After the phone is deleted successfully
```

![userDelete_404](https://cloud.githubusercontent.com/assets/14006620/20550986/13ee70f4-b0f0-11e6-9f72-342864f0c4db.png)


*	If the phone does not exist, it should return the same 404 page as in (1) with error code 404.
*	A phone cannot be deleted if it’s still assigned, or 400 errors will be returned.
*	Otherwise, delete the corresponding phone, and redirect the request to the phone creation page at https://hostname/phone.
*	The phones should also unassigned from the user.


### Additional Requirements


1. You must follow the MVC design pattern and use Spring’s MVC framework; particularly, (1) and (2) should share the same model, even though they implement different views.
2. All the 10 operations should be transactional.
3. You must use JPA and persist the user data into a database. If you are on Amazon EC2, you need to use MySQL; For Google AppEngine, you can use either the built-in datastore, or Cloud SQL.
