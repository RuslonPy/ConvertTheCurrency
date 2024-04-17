
Regarding the Testing case:

1. Unit Testing:
   Unit tests should verify that the conversion logic works correctly. 
   This includes:
Testing a successful conversion from one currency to another.
Testing conversions with different amounts and currency pairs.
Verifying that the converted amount is rounded to two decimal places.
Testing error scenarios, such as when one of the currencies is not supported.
fetchConversionRate method: Unit tests should verify that the method fetches the conversion rate correctly from the external APIs. This includes:
Mocking WebClient calls to simulate responses from both API providers.
Testing scenarios where the first API provider fails to provide a conversion rate, and the method switches to the second provider.
Testing scenarios where both API providers fail to provide a conversion rate, and the method returns an empty Mono.

2. Integration Testing:
    Integration tests should ensure that the CurrencyConversionService class interacts correctly with the WebClient and handles actual HTTP requests and responses.
Mocking external API responses may not provide complete coverage, so integration tests can complement unit tests by testing real API interactions.
Integration tests should cover scenarios such as successful conversions, network errors, and API provider failures.

3. Edge Cases and Error Handling:
    Test edge cases such as when the amount to be converted is zero or negative, unsupported currencies, and extreme values.
Test error handling for scenarios such as network timeouts, connection errors, and unexpected responses from the API providers.

How to Run:
To run the Currency Conversion Service:

1. Ensure you have Java 17 installed on your system.
2. Clone the project repository from the source.
3. Import the project into your preferred IDE (e.g., IntelliJ IDEA, Eclipse).
4. Build the project using Maven.
5. Run the application as a Spring Boot application.

Authentication with External Providers:

1. API Key Authentication: The external providers may require an API key for 
   authentication. The service can include the API key as a query parameter or 
   in the request headers when making WebClient requests.

2. OAuth2 Authentication: If the external providers use OAuth2 for authentication, 
   the service can obtain access tokens through the OAuth2 authorization flow. 
   It can then include the access token in the request headers when making WebClient requests.

3. Basic Authentication: Some APIs may use basic authentication, where the service 
   needs to include a username and password in the request headers. 
   The service can securely store the credentials and include them in the WebClient requests.
