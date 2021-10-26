package com.jet.peoplemanagement.meli;

import com.jet.peoplemanagement.client.ClientService;
import com.jet.peoplemanagement.exception.GenericErrorException;
import com.jet.peoplemanagement.meli.model.SelectedShipments;
import com.jet.peoplemanagement.meli.order.OrderRoot;
import com.jet.peoplemanagement.meli.shipment.ShipmentRoot;
import com.jet.peoplemanagement.meli.user.MeliUser;
import com.jet.peoplemanagement.model.Client;
import com.jet.peoplemanagement.shipment.Shipment;
import com.jet.peoplemanagement.shipment.ShipmentFilter;
import com.jet.peoplemanagement.shipment.ShipmentService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.rmi.ServerException;
import java.util.*;

import static com.jet.peoplemanagement.shipmentStatus.DeliveryStatusEnum.POSTADO;
import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.OK;

@CrossOrigin
@RestController
@RequestMapping("/login")
@Api(value = "Controle para gerenciamento da comunicação com o mercado livre")
@Slf4j
public class MeliController {

    @Autowired
    MeliService meliService;

    @Autowired
    ClientService clientService;

    @Autowired
    ShipmentService shipmentService;

    Map<String, String> randomToClient = new HashMap<>();

    private final String BASE_URL = "https://api.mercadolibre.com/shipments/40320867783";

    @GetMapping(value = "/testRestTemplate")
    @ResponseBody
    public void testTestTemplate(@RegisteredOAuth2AuthorizedClient("meli") OAuth2AuthorizedClient authorizedClient) throws ServerException {

        RestTemplate restTemplate = new RestTemplate();

        // create headers
        HttpHeaders headers = new HttpHeaders();

        // set `content-type` header
        headers.setContentType(MediaType.APPLICATION_JSON);
        //headers.set("Authorization", authorizedClient.getAccessToken().toString());
        headers.setBearerAuth(authorizedClient.getAccessToken().getTokenValue());

        // set `accept` header
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // request body parameters
        Map<String, Object> map = new HashMap<>();
        //map.put("userId", 1);

        // build the request
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);

        // send POST request
        //ResponseEntity<String> response = restTemplate.getFo  rEntity

        ResponseEntity<String> response = restTemplate.exchange(BASE_URL + "/shipments/40320867783", HttpMethod.GET, entity, String.class);

        // check response
        if (response.getStatusCode() == HttpStatus.CREATED) {
            System.out.println("Request Successful");
            System.out.println(response.getBody());
        } else {
            System.out.println("Request Failed");
            System.out.println(response.getStatusCode());
        }
    }

    @GetMapping(value = "/meli/shipment/{shipmentId}")
    @ResponseBody
    public ResponseEntity<ShipmentRoot> getShipmentById(@RequestParam(required = true, value = "clientId") String clientId,
                                                        @PathVariable("shipmentId") String shipmentId) {

        ShipmentRoot root = null;
        try {
            root = meliService.getShipmentById(clientId, shipmentId);
            return new ResponseEntity<>(root, OK);

        } catch (Exception e) {
            return buildMeliAuthRedirection(clientId);
        }

    }

    @PostMapping(value = "/meli/selectedShipments")
    @ResponseBody
    public ResponseEntity<HttpStatus> saveSelectedShipments(@RequestBody SelectedShipments selectedShipments) {

        ShipmentRoot root = null;
        Client client = new Client(selectedShipments.getClientId());
        List<Shipment> newShipmentList = new ArrayList<>();
        selectedShipments.getShipments().stream().forEach(ship -> {

            try {
                ShipmentRoot meliShipDetails =  meliService.getShipmentById(client.getId(), ship.getShipmentCode());

                Shipment jetShip = new Shipment();
                jetShip.setClient(client);

                jetShip.setShipmentCode(ship.getShipmentCode());
                jetShip.setSaleCode(ship.getSaleCode());
                jetShip.setReceiverNickName(ship.getReceiverNickName());
                jetShip.setReceiverName(meliShipDetails.getReceiverAddress().receiverName);
                jetShip.setReceiverCep(meliShipDetails.getReceiverAddress().zipCode);
                jetShip.setReceiverAddress(meliShipDetails.getReceiverAddress().streetName + ", "+ meliShipDetails.getReceiverAddress().streetNumber);
                jetShip.setReceiverAddressComp(meliShipDetails.getReceiverAddress().addressLine);
                jetShip.setReceiverCity(meliShipDetails.getReceiverAddress().city.name);
                jetShip.setReceiverNeighbor(meliShipDetails.getReceiverAddress().neighborhood.name);
                //jetShip.setZone(getSafeValue(zoneMap, i));
                jetShip.setProducts(ship.getProducts());

                newShipmentList.add(jetShip);

            } catch (ClientNotAuthenticateException e) {
                buildMeliAuthRedirection(client.getId());
            }
        });

        ShipmentFilter filter = new ShipmentFilter(null, null, client.getId(), POSTADO, now(), now(), null);
        List<Shipment> jetShipmentsToday = shipmentService.findByOptionalParams(filter);
        shipmentService.deleteByList(jetShipmentsToday);
        shipmentService.saveAll(client, newShipmentList);

        return new ResponseEntity<>(OK);
    }

    @GetMapping(value = "/meli/orders/{seller}")
    @ResponseBody
    public ResponseEntity<List<Shipment>> getOrders(@PathVariable("seller") String seller, @RequestParam(required = true, value = "clientId") String clientId) {

        OrderRoot orders = null;
        try {
            orders = meliService.getOrdersBySeller(clientId, seller);
            ShipmentFilter filter = new ShipmentFilter(null, null, clientId, POSTADO, now(), now(), null);
            List<Shipment> jetShipmentsToday = shipmentService.findByOptionalParams(filter);

            List<Shipment> meliShipmentsToday = new ArrayList<>();
            orders.orders.forEach(order -> {
                order.orderItems.forEach(meliShip -> {
                    Shipment jetShip = new Shipment();
                    //jetShip.setClient(new Client(clientId));

                    Optional<Shipment> shipFound = jetShipmentsToday.stream().filter(shipAlreadySent ->  shipAlreadySent.getShipmentCode().equals(order.getShipping().getId())).findFirst();

                    jetShip.setAlreadySent(shipFound.isPresent());

                    jetShip.setShipmentCode(order.getShipping().getId());
                    jetShip.setSaleCode(order.getId());
                    jetShip.setReceiverNickName(order.getBuyer().getNickname());
                    //jetShip.setReceiverName(getSafeValue(destMap, i));
                    //jetShip.setReceiverCep(getSafeValue(cepMap, i));
                    //jetShip.setReceiverAddress(getSafeValue(endMap, i));
                    //jetShip.setReceiverAddressComp(getSafeValue(compMap, i));
                    //jetShip.setReceiverCity(getSafeValue(cityMap, 1));
                    //jetShip.setReceiverNeighbor(getSafeValue(neighborMap, i));
                    //jetShip.setZone(getSafeValue(zoneMap, i));
                    jetShip.setProducts(order.toProducts());

                    meliShipmentsToday.add(jetShip);
                });
            });

            return new ResponseEntity<>(meliShipmentsToday, OK);

        } catch (ClientNotAuthenticateException clientNotAuthenticateException) {
            return buildMeliAuthRedirection(clientId);
        } catch(Exception ex){
            String message = "Algo de errado no fluxo de verificação de pacotes enviados/a enviar para a jetflex.";
            log.error(message + " No cliente: {}", clientId);
            throw new GenericErrorException(message);
        }

    }


    private ResponseEntity buildMeliAuthRedirection(String clientId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:8080/login/meli/testRedirect?clientId="+clientId));
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }


/*    @GetMapping("/auth-code")
    Mono<String> useOauthWithAuthCode() {
        Mono<String> retrievedResource = webClient.get()
                .uri(BASE_URL)
                .retrieve()
                .bodyToMono(String.class);
        return retrievedResource.map(string ->
                "We retrieved the following resource using Oauth: " + string);
    }*/

    //@GetMapping("/authMeli")
//    @GetMapping("/login/oauth2/code/meli")
//    public void auth(@RequestParam(required = false) String code) {
//        log.info("result: "+ code);
//    }
//
//    @GetMapping(value = "/test", produces = MediaType.TEXT_HTML_VALUE)
//    @ResponseBody
//    public String test(HttpServletResponse response) throws ServerException {
//        String responseType = "code";
//        String clientId = "3729582442177052";
//        String redirectUri = "http://localhost:8080/api/authMeli";
//        try {
//            Response s = meliService.getCode(responseType, clientId, redirectUri);
//            log.info(s.headers().toString());
//            return s.toString();
//           // response.sendRedirect("https://www.mercadolivre.com/jms/mlb/lgz/msl/login/H4sIAAAAAAAEA12QwWrDQAxE_0XgnEySbhKSLpiSHnLrNxhlV45F1tailVvakH8vTuillwExw9MwN0hy4bG170zgIVKHUzKoISe0TnRoOYKHIUENhY3-zvMcQcWBjLSAv82cC8V36kRnUoepENSAk_Vtl-QL_PMV1HAR8NCb5eJXqzmwHEgDRkn8qbQMMizP-jBE-QeNZXwLiWm0lmOz2bvX3cFtt-5lv1_v3EIpslKwdlJuZmy1OVbuVLlTkoCpl2LV5nhYH9aVO2HmWSfrPyjxQqlkGQs9BmiCRIJ7DR0Wa00xXMGbTlQD5pw4PKo8J_jfAu6_xoINeUsBAAA/user");
//        } catch (Exception ex){
//            throw new ServerException(ex.getMessage());
//        }
//    }

    @GetMapping(value = "/meli/testRedirect")
    @ResponseBody
    public ResponseEntity<Object> testTestTemplate(@RequestParam(required = true, value = "clientId") String clientId) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        String stateUUID = UUID.randomUUID().toString();
        randomToClient.put(stateUUID, clientId);

        String fooResourceUrl = "https://auth.mercadolivre.com.br/authorization?response_type=code" +
                "&client_id=3729582442177052" +
                "&redirect_uri=http://localhost:8080/login/oauth2/code/meli" +
                "&state="+stateUUID;
        //httpResp.addHeader("Location", fooResourceUrl);
        //httpResp.addHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
        //httpResp.addHeader("Access-Control-Allow-Headers", "x-requested-with, authorization");
        //httpResp.addHeader("Access-Control-Max-Age", "3600");
        //httpResp.sendRedirect(fooResourceUrl);

        //ResponseEntity<String> response = restTemplate.getForEntity(fooResourceUrl , String.class);
        //log.info("response", response);

//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Baeldung-Example-Header",
//                "Value-ResponseEntityBuilderWithHttpHeaders");
//
//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                .body("Response with header using ResponseEntity");

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(fooResourceUrl));
        headers.set("type", "redirection");
        Map<String, String> body = new HashMap<>();
        body.put("redirectUrl", URI.create(fooResourceUrl).toString());
        return new ResponseEntity<>(body, headers, HttpStatus.NON_AUTHORITATIVE_INFORMATION);

    }

    @GetMapping("/oauth2/code/meli")
    public RedirectView auth(@RequestParam(required = true) String code, @RequestParam(required = false) String state) {

        RedirectView redirectView = new RedirectView();

        if(randomToClient.containsKey(state)){
            String clientId = randomToClient.remove(state);
            try {
                meliService.getToken(code, clientId, false);
                MeliUser meliUser = meliService.getUserData(clientId);
                Client client = clientService.findById(clientId);
                client.setSellerId(meliUser.getId());
                clientService.update(clientId, client);

                redirectView.setUrl("http://localhost:4200/#/partners/upload");

            } catch (ClientNotAuthenticateException e) {
                //inserir status de  erro na autenticação para informar ao cliente no front
                redirectView.setUrl("http://localhost:4200/#/partners/upload");
                log.error("Problema na autenticação do cliente", e);
            }
        } else{
            redirectView.setUrl("http://localhost:4200/#/partners/upload");
        }
        return redirectView;
    }

}