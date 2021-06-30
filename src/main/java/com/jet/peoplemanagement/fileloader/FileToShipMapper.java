package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.shipment.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.interactive.documentnavigation.outline.PDOutlineItem;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Slf4j
public class FileToShipMapper {

    public static final String NICKNAME = "Nickname:";
    public static final String DESTINATARIO = "Destinatario:";
    public static final String ENDERECO = "Endereço:";
    public static final String COMPLEMENTO = "Complemento:";
    public static final String SKU = "SKU:";
    public static final String CIDADE = "Cidade:";
    public static final String CEP = "CEP:";
    public static final Map<String, String> regions = new HashMap<>();

    static {
        regions.put("CENTRO", "CENTRO");
        regions.put("BARUERI", "BARUERI");
        regions.put("CARAPICUÍBA", "CARAPICUÍBA");
        regions.put("COTIA", "COTIA");
        regions.put("DIADEMA", "DIADEMA");
        regions.put("ITAQUAQUECETUBA", "ITAQUAQUECETUBA");
        regions.put("MAUÁ", "MAUÁ");
        regions.put("SÃO CAETANO", "SÃO CAETANO");
        regions.put("TABOÃO", "TABOÃO");
    }

    public static List<Shipment> giveMeShipmentModel(File file) throws IOException {

        Instant init = Instant.now();

        List<Shipment> shipList = new ArrayList<>();
        List<String> vendaMap = new ArrayList();
        List<String> nickMap = new ArrayList();
        List<String> endMap = new ArrayList();
        List<String> envMap = new ArrayList<String>();
        List<String> skuMap = new ArrayList<String>();
        List<String> cityMap = new ArrayList<String>();
        List<String> cepMap = new ArrayList<String>();
        List<String> compMap = new ArrayList();
        List<String> destMap = new ArrayList();
        List<String> productNameMap = new ArrayList();
        List<String> zoneMap = new ArrayList();
        List<String> neighborMap = new ArrayList();


        //try (PDDocument document = PDDocument.load(new File(fileLocation))) {
        try (PDDocument document = PDDocument.load(file)) {

            AccessPermission ap = document.getCurrentAccessPermission();
            if (!ap.canExtractContent()) {
                throw new IOException("You do not have permission to extract text");
            }
            PDFTextStripper stripper = new PDFTextStripper();
            // This example uses sorting, but in some cases it is more useful to switch it off,
            // e.g. in some files with columns where the PDF content stream respects the
            // column order.
            stripper.setSortByPosition(false);

            String index[] = {};

            for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);

                //if(p != 1) break;
                // let the magic happen
                String text = stripper.getText(document);
                String auxIndex[] = text.split("\n");

                if (Arrays.asList(auxIndex).stream().filter(key -> key.contains("Despache as suas vendas o quanto antes")).findAny().isPresent()) {
                    break;
                }

                index = auxIndex;
                mapFields(vendaMap, nickMap, endMap, envMap, skuMap, cityMap, cepMap, compMap, destMap, productNameMap, zoneMap, neighborMap, index);
                //envio = buildShipment(index);
            }

           /* if (vendaMap.size() == 1) {
                shipList.add(buildShipment(index));
            } else {*/
            buildShipList(shipList, vendaMap, nickMap, endMap, envMap, cityMap,
                    cepMap, compMap, destMap, productNameMap, neighborMap, zoneMap);
            //  }

            Instant end = Instant.now();
            Duration duration = Duration.between(init, end);
            log.info("Shipment convert for {} envios time {}", shipList.size(), duration);
        }

        return shipList;
    }

    private static void buildShipList(List<Shipment> shipList, List<String> vendaMap, List<String> nickMap,
                                      List<String> endMap, List<String> envMap, List<String> cityMap,
                                      List<String> cepMap, List<String> compMap, List<String> destMap, List<String> productNameMap,
                                      List<String> neighborMap, List<String> zoneMap) {

        for (int i = 0; i < envMap.size(); i++) {
            Shipment ship = new Shipment();

            ship.setShipmentCode(getSafeValue(envMap, i));
            ship.setSaleCode(getSafeValue(vendaMap, i));
            ship.setReceiverNickName(getSafeValue(nickMap, i));
            ship.setReceiverName(getSafeValue(destMap, i));
            ship.setReceiverCep(getSafeValue(cepMap, i));
            ship.setReceiverAddress(getSafeValue(endMap, i));
            ship.setReceiverAddressComp(getSafeValue(compMap, i));
            ship.setReceiverCity(getSafeValue(cityMap, 1));
            ship.setProductName(getSafeValue(productNameMap, i));
            ship.setReceiverNeighbor(getSafeValue(neighborMap, i));
            ship.setZone(getSafeValue(zoneMap, i));

            shipList.add(ship);
        }
    }

    private static String getSafeValue(List<String> list, int i) {
        return i < list.size() ? list.get(i) : "";
    }

    private static void mapFields(List<String> vendaMap, List<String> nickMap, List<String> endMap,
                                  List<String> envMap, List<String> skuMap, List<String> cityMap,
                                  List<String> cepMap, List<String> compMap, List<String> destMap,
                                  List<String> productNameMap, List<String> zoneMap, List<String> neighborMap,
                                  String[] index) {

        for (int i = 0; i < index.length; i++) {
            String s = index[i];

            if (s.contains("Venda:") && s.contains("Envio:")) {
                String[] shipmentCodeMoreSaleCode = s.split("\\s+");

                String saleValue = shipmentCodeMoreSaleCode[1].trim();
                String envioValue = shipmentCodeMoreSaleCode[3].trim();

                vendaMap.add(saleValue);
                envMap.add(envioValue);
            }

            if (s.contains(SKU)) {
                String value = s.replace(SKU, "").trim();
                skuMap.add(value);
            }

            if (s.contains(CIDADE)) {
                String value = s.replace(CIDADE, "").trim();
                cityMap.add(value);
            }

            if (s.contains(CEP)) {
                String value = s.replace(CEP, "").trim();
                cepMap.add(value);
            }

            if (s.contains(DESTINATARIO)) {
                String value = s.replace(DESTINATARIO, "").trim();
                destMap.add(value);
            }

            if (s.contains(NICKNAME)) {
                String value = s.replace(NICKNAME, "").trim();
                nickMap.add(value);
            }

            if (s.contains(ENDERECO)) {
                String value = s.replace(ENDERECO, "").trim();
                endMap.add(value);
            }

            if (s.contains(COMPLEMENTO)) {
                //envio = initEnvio(envio, COMPLEMENTO ,keyMap, envioMap);
                //envio.setReceiverAddressComp(s.replace(COMPLEMENTO,"").trim());
                String value = s.replace(COMPLEMENTO, "").trim();
                compMap.add(value);
            }

            if (s.contains("Quantidade:")) {
                //envio = initEnvio(envio, COMPLEMENTO ,keyMap, envioMap);
                //envio.setReceiverAddressComp(s.replace(COMPLEMENTO,"").trim());
                String value = index[i - 1].trim();
                productNameMap.add(value);
            }

            if (s.matches("(Pacote com)\\s[1-9].+")) {
                //envio = initEnvio(envio, COMPLEMENTO ,keyMap, envioMap);
                //envio.setReceiverAddressComp(s.replace(COMPLEMENTO,"").trim());
                String value = index[i].trim();
                productNameMap.add(value);
            }

            String upperVal = s.toUpperCase().trim();

            if (upperVal
                    .matches("(LESTE|OESTE|NORTE|SUL|EMBU DAS ARTES|GUARULHOS|OSASCO|SANTO ANDRÉ|SÃO BERNARDO|EMBU)\\s[1-9]+") ||
                    regions.containsKey(upperVal)) {

                String checkIgualsNeighbor = index[i - 1].trim().toUpperCase();
                if(!upperVal.equals(checkIgualsNeighbor)){ //checa se bairro tem o mesmo nome que a regiao
                    String neighbor = index[i + 1].trim();
                    neighborMap.add(neighbor);
                    zoneMap.add(s.trim());
                }

            }
        }
    }

    private static Shipment initEnvio(Shipment envio, String key, HashMap<String, String> keyMap,
                                      HashMap<String, Shipment> envioMap) {
        if (envio == null || keyMap.containsKey(key)) {
            envio = new Shipment();
            envioMap.put(String.valueOf(keyMap.size()), envio);
        } else {
            keyMap.put(key, "");
        }
        return envio;
    }

    private static Shipment buildShipment(String[] index) {

        if (!index[17].contains("Venda") || !index[17].contains("Envio")) {
            throw new IllegalArgumentException("Arquivo  enviado não está no padrão esperado");
        }

        Shipment envio = new Shipment();

        envio.setProductName(index[0]);
        envio.setProductDesc(index[1] + " " + index[2]);
        envio.setSku(index[5]);

        String[] shipmentCodeMoreSaleCode = index[17].split("\\s+");
        envio.setSaleCode(shipmentCodeMoreSaleCode[1]);
        //envio.setSaleCode(UUID.randomUUID().toString());
        envio.setShipmentCode(shipmentCodeMoreSaleCode[3]);
        //envio.setShipmentCode(UUID.randomUUID().toString());

        envio.setShipType(index[6]);
        envio.setZone(index[7]);
        envio.setReceiverNeighbor(index[14]);
        envio.setReceiverName(index[15].replace("Destinatario:", "").trim());
        envio.setReceiverNickName(index[16].replace("Nickname:", "").trim());
        envio.setReceiverAddress(index[17].replace("Endereço:", "").trim());
        envio.setReceiverAddressComp(index[18].replace("Complemento:", "").trim());
        envio.setReceiverCity(index[19].replace("Cidade:", "").trim());
        envio.setReceiverCep(index[20].replace("CEP:", "").trim());

        return envio;
    }

}
