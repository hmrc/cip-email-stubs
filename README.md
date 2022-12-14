# cip-email-stubs

### Summary

Backend stub to mock downstream services, currently the stub mocks the following services:

- [GovNotify](https://docs.notifications.service.gov.uk/rest-api.html#send-an-email) - sends email to citizen
- [GovNotify](https://docs.notifications.service.gov.uk/rest-api.html#get-the-status-of-one-message) - get the status of
  a message
- [InternalAuth](https://github.com/hmrc/internal-auth#token-authentication) - checks authentication token is valid

The default port for cip-email-stubs is 6199.

#### Default ports for related services

```
cip-email-frontend:     6180
cip-email:              6181
cip-email-validation:   6182
cip-email-verification: 6183
```

### Testing

Sending passcode email test cases

```
      "teamonly@something.com"        => BadRequest
      "trialmode@something.com"       => BadRequest
      "systemclock@something.com"     => Forbidden
      "invalidtoken@something.com"    => Forbidden
      "ratelimit@something.com"       => TooManyRequests
      "toomanyrequests@something.com" => TooManyRequests
      "exception@something.com"       => InternalServerError
      Any                             => Created
```

Notification Id test cases

```
      "validation-d385-4b17-a0b4-23a85c0c5b1a" => BadRequest
      "invalidtoken-d385-4b17-a0b4-23a85c0c5b1a" => Forbidden
      "systemclock-d385-4b17-a0b4-23a85c0c5b1a" => Forbidden
      "noresult-d385-4b17-a0b4-23a85c0c5b1a" => NotFound
      
      "16770ea0-d385-4b17-a0b4-23a85c0c5b1a" => permanent_failure
      "26770ea0-d385-4b17-a0b4-23a85c0c5b1a" => technical_failure
      "36770ea0-d385-4b17-a0b4-23a85c0c5b1a" => temporary_failure
      "46770ea0-d385-4b17-a0b4-23a85c0c5b1a" => created
      "56770ea0-d385-4b17-a0b4-23a85c0c5b1a" => sending
      "66770ea0-d385-4b17-a0b4-23a85c0c5b1a" => pending
      "76770ea0-d385-4b17-a0b4-23a85c0c5b1a" => sent
      "86770ea0-d385-4b17-a0b4-23a85c0c5b1a" => not found
      Any                                    => delivered
```

Internal auth test cases

```
      ""                    => Unauthorized("No token")
      "invalid-token"       => Unauthorized("Invalid token")
      "invalid-permission"  => Forbidden("Not authorized")
      Any                   => Ok
```

### Running app

    sm --start CIP_EMAIL_ALL

Run the services against the current versions in dev, stop the CIP_EMAIL_STUBS service and start manually

    sm --stop CIP_EMAIL_STUBS
    cd cip-email-stubs
    sbt run

For reference here are the details for running each of the services individually

    cd cip-email-frontend
    sbt run
 
    cd cip-email
    sbt run

    cd cip-email-validation
    sbt run

    cd cip-email-verification
    sbt run

### Curl microservice (for curl microservice build jobs)

#### Verify

##### Using curl locally

```
curl \
  --verbose \
  --request POST \
  --header 'Content-Type: application/json' \
  --data '{"email_address":"someone@example.com","template_id":"dce5ac8a-0970-41a0-b993-bde1beab5825","personalisation":{"clientServiceName":"cip-email-service","passcode":"BTXDYC","timeToLive":"1"}}' \
  'http://localhost:6199/v2/notifications/email'
```

##### Using jenkins script

```
-X POST 
    -H "Content-type: application/json" -d '{
        "email_address": "someone@example.com", "template_id": "dce5ac8a-0970-41a0-b993-bde1beab5825", "personalisation": {
            "clientServiceName": "cip-email-service", "passcode": "BTXDYC", "timeToLive": "1"
        }
    }' 'https://cip-email-stubs.protected.mdtp/v2/notifications/email'
```

#### Check notification status

##### Using curl locally

```
curl \
  --verbose \
  --request GET \
  'http://localhost:6199/v2/notifications/ecf20f0a-86af-4ebf-9012-e48bc6a31174'
```

##### Using jenkins script

```
-X GET 
    -H "Content-type: application/json"
    'https://cip-email-stubs.protected.mdtp/v2/notifications/ecf20f0a-86af-4ebf-9012-e48bc6a31174'
```

#### Check authentication token

##### Using curl locally

```
curl \
  --verbose \
  --request POST \
  --header 'Authorization: Bearer fake-token' \
  'http://localhost:6199/internal-auth/auth'
```

##### Using jenkins script

```
-X POST 
    -H "Content-type: application/json"
    -H "Authorization: Bearer fake-token"
    'https://cip-email-stubs.protected.mdtp/internal-auth/auth'
```

### License

This code is open source software licensed under
the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
