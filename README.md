# Mock Twitter Client

This is a small app that uses Twitter as a basis for functionality and communicates with a simulated local server.

Requirements: As this project is using Retrolambda please ensure that Java 8 is installed and JAVA8_HOME is added as an environment variable.

#Features

###User log in
The server database is seeded with an array of accounts stored in '/assets/accounts.json'. The user passwords are plain text
without any encryption in order to simplify development. In order to sign in please enter one of the email and password
combinations within the file. If you don't want to view the file you simply log in as 'thomapalmer@gmail.com' with 'password1'
as your password. Don't worry all of the passwords are just as creative and secure.

<u>Client Login Actions

- Server responds with a successful login response
- The login response contains the user email and an auth token
- User email and auth token are stored in shared preference to be used as authentication headers when communicating with the server

<u>Server Login Actions
- Server validates the received credentials by doing a lookup in the account table
- If a match is found, the user email and an auth token(a random long) are stored in the session table in order to validate future client interactions

###Tweet feed
After login you will be presented with a feed of tweets starting with the most recent. The server database is seeded with 1000 tweets
generated on www.json-generator.com. The json template model was derived by hitting the twitter search api and stripping out
any unnecessary data. The seed data is stored in the assets folder as 'tweets.json'.

I've implemented a simple implementation of endless
scrolling on the feed. Tweets are retrieved 50 items at a time and more are added to the list as the user nears the end. Tweets received from the server
are stored in the local db and retrieved from the local db on subsequent loads. This is done by maintaining a createdAt timestamp which is
updated with the oldest tweet received from each call to the server.

### Post a new tweet

Pressing the floating action button on the feed will allow you to post a new tweet. Currently there is a small delay when posting the tweet
and I have not provided any user feedback. I apologize for that. The activity will close after the tweet has successfully posted to the server and appear
on the top of your feed.

###Logout
Log out functionality is exposed as a menu item in the feed. Simply click 'Logout' and you will be returned to the Login Screen.
This also clears shared preferences(user email, auth token, lastCreatedAt) as well as any tweets that are stored in the local db.

#Architecture

In an attempt to follow Clean Architecture practices the code was separated in three main packages:
- data/persistence layer: Typically all persistence & data source related code resides in this layer. The fake twitter server resides
in this layer. This was done using MockRetrofit. MockRetrofit provided an efficient way of mocking the server responses including
network delays and failure. The duration of delays and frequency of failure can be easily modified but the default was sufficient for
this project.

- domain/business layer: Application or business logic in the form of use cases.

- presentation layer: All frontend/ui related code. I tend to follow to MVP in this layer but for this
project I decided to give the relatively new databinding library a try. As a result the ui layer ended as a strange
MVP ViewModel pattern. Given more experience with the library and discussions with other developers I'm not sure I would
continue to use presenters with databinding as they seemed redundant for this project at least.

I'll be honest in saying the layers may have become a little muddled in my haste to complete this project but I feel the project
is still a decent representation of clean architecture.

#Tests

The majority of tests in the project are unit tests. Some of the presenters and all of the use cases have unit tests.
I also included an espresso test which tests the login and logout flow. As stated in my application, I have not had the opportunity
to work for a startup that valued proper automated testing so I was a little rusty when it came to setting up my testing.

I did not include any tests for the server and its routes. However server interactions can typically be tested using the same method
in which the TwitterServer was mocked so it's a pretty good example of how it would be done.


# Choice of Libraries

- Retrofit: Simple robust server interaction simply by creating annotated interface methods. I have used Retrofit in almost
all of my projects and find it to the be fastest and cleanest way of managing server interactions. For this project, I chose
to pass the authentication headers as parameters in the routes. I typically add them globally using a Okhttp NetworkInterceptor
instead but it was much more simple with MockRetrofit to have them passed in as parameters.

- SqlBrite: Lightweight SqliteOpenHelper Wrapper with some nice features such as Observable queries. However, I didn't end up using any
of the additional features provided by this library due to some issues with Java 8 generics.

- Retrolamba: Pretty simple one here. Just allows lambda's to be used prior to Java 8. Fantastic.

- Dagger2: Dependency injection. Fairly new to this one and I used it more extensively in this project than others. Pretty sure
I butchered its usage but it seemed to the trick.

- Other libraries: Stetho, JodaTime.


