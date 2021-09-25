package com.jet.peoplemanagement.meli;

import com.jet.peoplemanagement.meli.order.OrderRoot;
import com.jet.peoplemanagement.meli.shipment.ShipmentRoot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

//@FeignClient(name = "meliClient", url = "https://auth.mercadolivre.com.br")
@Slf4j
@Service
public class MeliService {

    /*@GetMapping("/authorization")
    @Headers("Accept: application/json")
    Response getCode(@RequestParam(name = "response_type") String responseType,
                     @RequestParam(name = "client_id") String clientId,
                     @RequestParam(name = "redirect_uri") String redirectUri);*/
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    MeliAuth meliAuth;

    @Value("${meli.baseUrl}")
    private String baseUrl;

    Map<String, String> stateToClientId = new HashMap<>();

    public OrderRoot getOrdersBySeller(String clientId, String seller) throws Exception {

        String url = baseUrl + "/orders/search";

        MeliOAuthClient meliClient = verifyAndGetToken(clientId);

        HttpHeaders headers = getHttpHeaders(meliClient);
        HttpEntity<Map<String, Object>> entityReq = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("seller", seller);

        UriComponentsBuilder builder = getUriComponentsBuilder(url, params);
        try {
            ResponseEntity<OrderRoot> response = restTemplate.exchange(builder.build().toString(), HttpMethod.GET, entityReq, OrderRoot.class);

            // check response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Request Successful");
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response.getBody();

        } catch (Exception e) {
            //log.error(e.getMessage());
            throw e;
        }
    }

    private MeliOAuthClient verifyAndGetToken(String clientId) throws Exception {
        MeliOAuthClient meliClient;

        if (meliAuth.hasToken(clientId)) {
            meliClient = meliAuth.getToken(clientId);
            if (meliAuth.isExpired(clientId)) {
                meliClient = getToken(meliClient.getRefreshToken(), "", true);
            }
        } else {
            throw new Exception("Não tem token de acesso, precisa seguir o fluxo");
        }
        return meliClient;
    }

    private UriComponentsBuilder getUriComponentsBuilder(String url, Map<String, String> params) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }
        return builder;
    }

    public ShipmentRoot getShipmentById(String clientId, String shipmentId) throws Exception {

        String url = baseUrl + "/shipments/{id}";

        MeliOAuthClient meliClient = verifyAndGetToken(clientId);

        HttpHeaders headers = getHttpHeaders(meliClient);

        Map<String, Object> body = new HashMap<>();
        HttpEntity<Map<String, Object>> entityReq = new HttpEntity<>(body, headers);
        Map<String, String> pathParams = new HashMap<>();
        pathParams.put("id", shipmentId);

        try {
            ResponseEntity<ShipmentRoot> response = restTemplate.exchange(url, HttpMethod.GET, entityReq, ShipmentRoot.class, pathParams);

            // check response
            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Request Successful");
            } else {
                System.out.println("Request Failed");
                System.out.println(response.getStatusCode());
            }
            return response.getBody();

        } catch (Exception e) {
            //log.error(e.getMessage());
            throw e;
        }
    }

    public MeliOAuthClient getToken(String code, String clientId, boolean isRefreshToken) {

//        if (!stateToClientId.containsKey(state)) {
//            log.error("clientId/state not found in oAuthResponse from meli");
//        }
        try {
            //String clientId = stateToClientId.containsKey(state) ? stateToClientId.get(state) : "clientNotFount";
            String url = baseUrl + "/oauth/token";
            HttpHeaders headers = getHttpHeaders(null);
            //body
            Map<String, Object> body = new HashMap<>();
            String grantType = isRefreshToken ? "refresh_token" : "authorization_code";
            body.put("grant_type", grantType);
            body.put("client_secret", "9MwBBwyh3hrIxGOB8Qr9e4Dldwypa3aL");
            body.put("client_id", "3729582442177052");
            if (isRefreshToken) {
                body.put("refresh_token", code);
            } else {
                body.put("code", code);
            }
            body.put("redirect_uri", "http://localhost:8080/login/oauth2/code/meli");

            HttpEntity<Map<String, Object>> entityReq = new HttpEntity<>(body, headers);

            restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
            ResponseEntity<MeliOAuthClient> response = restTemplate.exchange(url, HttpMethod.POST, entityReq, MeliOAuthClient.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Sucesso no retorno da url {}", url);
            } else {
                log.info("Falha no retorno da url {}", url);
            }
            meliAuth.putToken(clientId, response.getBody());
            return response.getBody();

        } catch (Exception e) {
            throw e;
        }
    }


    private HttpHeaders getHttpHeaders(MeliOAuthClient client) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (client != null) {
            //headers.setBearerAuth(client.getAccessToken().getTokenValue());
            headers.setBearerAuth(client.getAccessToken());
        }
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }


}