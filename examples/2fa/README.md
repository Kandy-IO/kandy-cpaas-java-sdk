# Two Factor Authentication Starter App

This is an elementary  login authentication use case of two-factor authentication via SMS. The main focus of this application is to understand and implement the 2FA flow, so least amount of stress is given to the authentication/login mechanism.

## Installation
1. Copy `.env.example` and rename to `.env` and add the appropriate values. Check `Configuration` section for more details.
2. To install dependencies, run:
```bash
gradle wrapper --gradle-version=4.10.3
```
3. To start the server, run:
```bash
gradle appRun
```

## Configuration
There are a few environment variables (check `.env` file) to make the application simpler and help us focus on the key aspects a two-factor authentication system via SMS. Some of the variables are pre-filled and some are left blank which are left on the user to place appropriate values. All the variables are mandatory.

ENV KEY           | Description
------------------|-------------
CLIENT_ID         | Private project key
CLIENT_SECRET     | Private project secret
BASE_URL          | URL of the CPaaS server to use
PHONE_NUMBER      | Phone number that would receive the verification code
DESTINATION_EMAIL | Password to be entered against the EMAIL provided
PORT              | The port on which the local server would run
EMAIL             | Email used in the login screen of the application
PASSWORD          | Password to be entered against the EMAIL provided

## Usage
The application comprises of three simple pages, login, code verification, dashboard/portal
> + On opening the application in the browser, the login screen is presented. The user needs to enter the `Email` / `Password` that are specified in the `.env` file and click on the `Login` button.
> + Once the credentials are verified, the verification page is presented to user. Here the user has 2 options, either receive 2FA via SMS or via EMAIL. This phone number/email corresponds to the one entered in the `.env` file as `PHONE_NUMBER`/`DESTINATION_EMAIL`.
> + The user now needs to enter the verification code received in the mentioned phone number and click `Verify` button.
> + The application verifies the entered code. If the code validates, the user is redirected to the dashboard section; else the user will be promoted with an error alert `Code invalid or expired` and is required to re-enter the verification code.
> + As the user is authenticated, the dashboard opens up. The user can logout from the dashboard and login screen would be presented.
