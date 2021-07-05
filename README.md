# 🚀 Playground: Mock HTTP sever

This is a mock server that allows you to set custom responses to `POST` requests. You can configure which responses want to get and their probability. 

## 🏃🏽‍♀️How to run
```bash
./gradlew bootRun
```


## ✉️ Sending requests
The server by default is listening in port `8081`. All you need to do is send a post request to:

Mock server endpoint: `http://localhost:8081/any/`
</br>
HTTP method: `POST`
 
## ⚙️ Setting responses

Using the configuration endpoint you can set the server responses and their frequency.  
 
Configuration endpoint: `http://localhost:8081/service/config`
<br> 
HTTP method: `PUT`

## Examples
Here you'll find examples responses 

### Setting to return `202`
Setting to return `202` 100% of times, with 100ms of delay.

```json
{
  "responses": [{
    "fromPercent": 1,
    "toPercent": 100,
    "httpCode": 202
  }

  ],
  "delays": [{
    "fromPercent": 1,
    "toPercent": 100,
    "minDelay": 1,
    "maxDelay": 100
  }]
}
```

### Setting to return `202` and `404`
Setting to return `202` 50% of times and `404` 50% of times, with 100ms of delay.

```json
{
  "responses": [{
    "fromPercent": 1,
    "toPercent": 50,
    "httpCode": 202
  },
    {
      "fromPercent": 51,
      "toPercent": 100,
      "httpCode": 404
    }
  ],
  "delays": [{
    "fromPercent": 1,
    "toPercent": 100,
    "minDelay": 1,
    "maxDelay": 100
  }]
}
```
