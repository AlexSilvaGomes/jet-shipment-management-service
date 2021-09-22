package com.jet.peoplemanagement.config.meli;

import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequestEntityConverter;
import org.springframework.util.MultiValueMap;

public class CustomRequestEntityConverter implements Converter<OAuth2AuthorizationCodeGrantRequest, RequestEntity<?>> {

    private OAuth2AuthorizationCodeGrantRequestEntityConverter defaultConverter;

    public CustomRequestEntityConverter() {
        defaultConverter = new OAuth2AuthorizationCodeGrantRequestEntityConverter();
    }

    @Override
    public RequestEntity<?> convert(OAuth2AuthorizationCodeGrantRequest req) {
        RequestEntity<?> entity = defaultConverter.convert(req);
        MultiValueMap<String, String> params = (MultiValueMap<String,String>) entity.getBody();
        //params.add("test2", "extra2");

        //entity.getHeaders().setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        params.add("client_secret", "9MwBBwyh3hrIxGOB8Qr9e4Dldwypa3aL");
        params.add("client_id", "3729582442177052");


        MultiValueMap<String, String> paramsHeaders = (MultiValueMap<String,String>) entity.getHeaders();

        MultiValueMap<String, String> headers = new HttpHeaders();

        System.out.println(params.entrySet());
        return new RequestEntity<>(params, headers, entity.getMethod(), entity.getUrl());
    }

}