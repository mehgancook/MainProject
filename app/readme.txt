The features that we currently have implemented:

1. A user can sign in using general email sign in (facebook not implmented yet).
2. A user can sign up using general email sign up (facebook not implemented yet).
3. A user can view their Askers. (To test, please use username: mehganc@uw.edu, password: awesome)
4. A user can view their Followers. (To test, please use username: mehganc@uw.edu, password: awesome)


The use cases that we currently have created are listed below. Please, note, that we will be adding facebook login, network checks, profile images, and groups in the next phase:


Use case 1: Register a user
Actor: A user
Preconditions: Have a valid email address.
Successful Condition: An account is successfully registered and the user is taken to the main homepage.

Main Scenario:
 
1)  If a non-user is not registering with facebook, the non-user clicks on the signup link on the splash page.
2) If a non-user is not registering with facebook, a non-user must enter their email, a password that is greater than 6 characters long, and their desired username on the next page.
3) Afterwards, a non-user becomes a user and has successfully registered with our app. They will be taken to a confirmation page afterwards.
 
Alternate Scenarios:
1) A user provides incorrect password information. A user must be allowed to re-enter their password into the same form. 
2) An email is already in use. A user must be allowed to re-enter a new email.


Use case 2: User logins
Actor: App user
Preconditions: The user must have a Slick Pick account
Successful Condition: A user successfully logs in and is taken to the main homepage.

Main Scenario:
 
1)  If a user is logging in with their email address, a user must click the link on the splash page that directs to a form where they provide their email and password.
2) User successfully logs in with our app.
3) User sees the main activity of the app, which is the your followers/askers section.
 
Alternate Scenarios:
1) A user provides an incorrect password. A user must be allowed to re-enter their password. 
2) A username and password combination is invalid.
 

Use case 3: Asker views followers.
Actor: Asker
Preconditions: The asker must have a Slick Pick account.
Successful Condition: An asker successfully goes from the main homepage using the menu navigation on the bottom of the page to views their followers.

Main Scenario: 

1) An asker is on any page of the app. Using the bottom navigation, they click the button to view followers/askers. (Currently only available activity.)
2) An asker clicks on the "followers" tab on the above.
3) An asker is successfully able to see all followers’ usernames.
4) If an asker, clicks a follower's username; they can view that user’s username and email.


Use case 4: Follower views Askers.
Actor: Follower
Preconditions: A follower must have a Slick Pick account.
Successful Condition: A follower successfully uses the navigation from their bottom of the screen navigation to view who they are following.

Main Scenario:

1) A follower  is on any page of the app. Using the bottom navigation, they click the button to view followers.
2) The follower clicks button to view their askers.
3) A follower is able to see all askers who they are following. They will see their username.
4) If a follower, clicks an asker's username, they can view that user’s username and email.
