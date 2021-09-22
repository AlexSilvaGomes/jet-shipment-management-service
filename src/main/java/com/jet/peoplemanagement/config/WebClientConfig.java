package com.jet.peoplemanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebClientConfig {


    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    WebClient webClientForAuthorized(ReactiveClientRegistrationRepository clientRegistrations, ServerOAuth2AuthorizedClientRepository authorizedClients) {
//        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
//        return WebClient.builder()
//                .filter(oauth)
//                .build();
//    }

//    @Bean
//    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations,
//                               ServerOAuth2AuthorizedClientRepository authorizedClients) {
//        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
//                clientRegistrations, authorizedClients);
//        oauth.setDefaultOAuth2AuthorizedClient(true);
//        return WebClient.builder().filter(oauth).build();
//    }


    /*@Bean
    WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
                new ServerOAuth2AuthorizedClientExchangeFilterFunction(
                        clientRegistrations,
                        new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
        oauth.setDefaultClientRegistrationId("meli");
        return WebClient.builder()
                .filter(oauth)
                .build();
    }*/
}