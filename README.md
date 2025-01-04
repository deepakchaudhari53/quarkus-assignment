# quarkus-exercise

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

## Debugging the application
To debug the app, run the following command first,

```shell script
./mvnw quarkus:dev -Ddebug
```
This activates debug on port 5005. Then use your IDE to connect to localhost:5005 to debug the application.

```shell script
./mvnw compile quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```
The application runs on port 9090 (defined by quarkus.http.port in application.properties).

#### Swagger UI - http://localhost:9090/q/swagger-ui/

## Endpoints
| Method | Endpoint                            | Description                                                                           |
|--------|-------------------------------------|---------------------------------------------------------------------------------------|
| `POST`  | `/v1/facts`                        | Get a random fact from the Useless Facts API, stores it, and returns a shortened URL. |
| `GET`  | `/v1/facts/{shortenedUrl}`          | Returns the cached fact and increments the access count.                              |
| `GET`  | `/v1/facts/getAllFacts`             | Returns all cached facts and does not increment the access count.                     |
| `GET`  | `/v1/facts/{shortenedUrl}/redirect` | Redirects to the original fact and increments the access count.                       |
| `GET`  | `/v1/facts/admin/statistics`        | Provides access statistics for all shortened URLs.                                    |