package com.jet.peoplemanagement.controller;//Import classes:
import meli.ApiClient;
import meli.ApiException;
import meli.Configuration;
import meli_marketplace_lib.OAuth20Api;

public class GetToken {
    public void teste() {
        ApiClient defaultClient = Configuration.getDefaultApiClient();
        //defaultClient.setBasePath("https://api.mercadolibre.com");
        defaultClient.setBasePath("https://auth.mercadolivre.com.br");

        OAuth20Api apiInstance = new OAuth20Api(defaultClient);
        String grantType = "authorization_code"; // or 'refresh_token' if you need get one new token
        String clientId = "3729582442177052"; // Your client_id
        String clientSecret = "9MwBBwyh3hrIxGOB8Qr9e4Dldwypa3aL"; // Your client_secret
        String redirectUri = "http://localhost:8080/app/authMeli"; // Your redirect_uri
        String code = ""; // The parameter CODE, empty if your send a refresh_token
        String refreshToken = ""; // Your refresh_token
        try {
            Object response = apiInstance.getToken(grantType, clientId, clientSecret, redirectUri, code, refreshToken);
            System.out.println(response);
        } catch (ApiException e) {
            System.err.println("Exception when calling OAuth20Api#com.jet.peoplemanagement.controller.getToken");
            System.err.println("Status code: " + e.getCode());
            System.err.println("Reason: " + e.getResponseBody());
            System.err.println("Response headers: " + e.getResponseHeaders());
            e.printStackTrace();
        }
    }
}