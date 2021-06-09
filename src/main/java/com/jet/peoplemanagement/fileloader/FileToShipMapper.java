package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.shipment.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class FileToShipMapper {

    public static final String NICKNAME = "Nickname:";
    public static final String DESTINATARIO = "Destinatario:";
    public static final String ENDERECO = "Endereço:";
    public static final String COMPLEMENTO = "Complemento:";
    public static final String SKU = "SKU:";
    public static final String CIDADE = "Cidade:";
    public static final String CEP = "CEP:";

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

            for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);

                //if(p != 1) break;
                // let the magic happen
                String text = stripper.getText(document);
                String index[] = text.split("\n");
                mapFields(vendaMap, nickMap, endMap, envMap, skuMap, cityMap, cepMap, compMap, destMap, index);
                //envio = buildShipment(index);
            }

            buildShipList(shipList, vendaMap, nickMap, endMap, envMap, cityMap, cepMap, compMap, destMap);
            Instant end = Instant.now();
            Duration duration = Duration.between(init, end);
            log.info("Shipment convert for {} envios time {}", shipList.size(), duration);
        }

        return shipList;
    }

    private static void buildShipList(List<Shipment> shipList, List<String> vendaMap, List<String> nickMap, List<String> endMap, List<String> envMap, List<String> cityMap, List<String> cepMap, List<String> compMap, List<String> destMap) {
        for (int i = 0; i < envMap.size(); i++) {
            Shipment ship = new Shipment();
            ship.setShipmentCode(envMap.get(i));
            ship.setSaleCode(vendaMap.get(i));
            ship.setReceiverNickName(nickMap.get(i));
            ship.setReceiverName(destMap.get(i));
            ship.setReceiverCep(cepMap.get(i));
            ship.setReceiverAddress(endMap.get(i));
            ship.setReceiverAddressComp(compMap.get(i));
            ship.setReceiverCity(cityMap.get(i));
            shipList.add(ship);
        }
    }

    private static void mapFields(List<String> vendaMap, List<String> nickMap, List<String> endMap, List<String> envMap, List<String> skuMap, List<String> cityMap, List<String> cepMap, List<String> compMap, List<String> destMap, String[] index) {
        for (int i = 0; i < index.length; i++) {
            String s = index[i];

            if (s.contains("Venda:") && s.contains("Envio:")) {
                //envio = initEnvio(envio, "Venda:Envio:", keyMap, envioMap);
                String[] shipmentCodeMoreSaleCode = s.split("\\s+");

                String saleValue = shipmentCodeMoreSaleCode[1].trim();
                String envioValue = shipmentCodeMoreSaleCode[3].trim();

                vendaMap.add(saleValue);
                envMap.add(envioValue);

                //envio.setShipmentCode(shipmentCodeMoreSaleCode[3]);
                //envio.setSaleCode(shipmentCodeMoreSaleCode[1]);
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
                // envio = initEnvio(envio, DESTINATARIO ,keyMap, envioMap);
                // envio.setReceiverName(s.replace(DESTINATARIO,"").trim());

                String value = s.replace(DESTINATARIO, "").trim();
                destMap.add(value);
            }

            if (s.contains(NICKNAME)) {
                String value = s.replace(NICKNAME, "").trim();
                nickMap.add(value);
                //envio = initEnvio(envio, NICKNAME ,keyMap, envioMap);
                //envio.setReceiverNickName(s.replace(NICKNAME,"").trim());
            }

            if (s.contains(ENDERECO)) {
                String value = s.replace(ENDERECO, "").trim();
                endMap.add(value);
                //envio = initEnvio(envio, ENDERECO ,keyMap, envioMap);
                //envio.setReceiverAddress(s.replace(ENDERECO,"").trim());
            }

            if (s.contains(COMPLEMENTO)) {
                //envio = initEnvio(envio, COMPLEMENTO ,keyMap, envioMap);
                //envio.setReceiverAddressComp(s.replace(COMPLEMENTO,"").trim());

                String value = s.replace(COMPLEMENTO, "").trim();
                compMap.add(value);
            }


            //envio.setReceiverCity(index[19].replace("Cidade:","").trim());
            //envio.setReceiverCep(index[20].replace("CEP:","").trim());

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

        if (!index[11].contains("Venda") || !index[11].contains("Envio")) {
            throw new IllegalArgumentException("Arquivo  enviado não está no padrão esperado");
        }

        Shipment envio = new Shipment();


        envio.setProductName(index[0]);
        envio.setProductDesc(index[1] + " " + index[2]);
        envio.setSku(index[5]);

        String[] shipmentCodeMoreSaleCode = index[11].split("\\s+");
        envio.setSaleCode(shipmentCodeMoreSaleCode[1]);
        //envio.setSaleCode(UUID.randomUUID().toString());

        envio.setShipmentCode(shipmentCodeMoreSaleCode[3]);
        //envio.setShipmentCode(UUID.randomUUID().toString());
        envio.setShipType(index[12]);
        envio.setZone(index[13]);
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
