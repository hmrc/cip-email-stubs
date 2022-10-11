# cip-email-stubs

### Summary

Backend stub to mock downstream services, currently the stub mocks the following services:

- [GovNotify](https://docs.notifications.service.gov.uk/rest-api.html#send-an-email) - sends email to citizen
- [GovNotify](https://docs.notifications.service.gov.uk/rest-api.html#get-the-status-of-one-message) - get the status of
  a message

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

Notification Id Test cases

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
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmNmEzYTQwZC0yOWIyLTQ2ZjUtYmNkMS0zNmE0ZjY4MzcxNzEiLCJpYXQiOjE2NTgzMTU5MDV9.HdKMVoNm4S3353SvFvjaktb8J5yKsFATsyMjjRDlNxg' \
  --data '{"email":"someone@example.com","template_id":"dce5ac8a-0970-41a0-b993-bde1beab5825","personalisation":{"clientServiceName":"cip-email-service","passcode":"BTXDYC","timeToLive":"1"}}' \
  'http://localhost:6199/v2/notifications/email'
```

##### Using jenkins script

```
-X POST 
    -H "Content-type: application/json"
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmNmEzYTQwZC0yOWIyLTQ2ZjUtYmNkMS0zNmE0ZjY4MzcxNzEiLCJpYXQiOjE2NTgzMTU5MDV9.HdKMVoNm4S3353SvFvjaktb8J5yKsFATsyMjjRDlNxg"
    -d "{"email":"someone@example.com","template_id":"dce5ac8a-0970-41a0-b993-bde1beab5825","personalisation":{"clientServiceName":"cip-email-service","passcode":"BTXDYC","timeToLive":"1"}}"    
    'https://cip-email-stubs.protected.mdtp/v2/notifications/email'
```

#### Check notification status

##### Using curl locally

```
curl \
  --verbose \
  --request GET \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmNmEzYTQwZC0yOWIyLTQ2ZjUtYmNkMS0zNmE0ZjY4MzcxNzEiLCJpYXQiOjE2NTgzMTY3Njd9._0__Ubwncx84sp5Q3FhztZB7xkjSKFy9WVTunzZE4DQ' \
  'http://localhost:6199/v2/notifications/ecf20f0a-86af-4ebf-9012-e48bc6a31174'
```

##### Using jenkins script

```
-X GET 
    -H "Content-type: application/json"
    -H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJmNmEzYTQwZC0yOWIyLTQ2ZjUtYmNkMS0zNmE0ZjY4MzcxNzEiLCJpYXQiOjE2NTgzMTU5MDV9.HdKMVoNm4S3353SvFvjaktb8J5yKsFATsyMjjRDlNxg"
    'https://cip-email-stubs.protected.mdtp/v2/notifications/ecf20f0a-86af-4ebf-9012-e48bc6a31174'
```

### License

This code is open source software licensed under
the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
