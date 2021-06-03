package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.shipment.Shipment;
import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Slf4j
public class FileToShipMapper {

    public static Shipment giveMeShipmentModel(File file) throws IOException {

        Shipment envio = null;
        Instant init = Instant.now();

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
            stripper.setSortByPosition(true);

            for (int p = 1; p <= document.getNumberOfPages(); ++p) {
                // Set the page interval to extract. If you don't, then all pages would be extracted.
                stripper.setStartPage(p);
                stripper.setEndPage(p);

                if(p != 1) break;

                // let the magic happen
                String text = stripper.getText(document);
                String index[]=text.split("\n");
                envio = buildShipment(index);

                // do some nice output with a header
               /* String pageStr = String.format("page %d:", p);
                System.out.println(pageStr);
                for (int i = 0; i < pageStr.length(); ++i) {
                    System.out.print("-");
                }
                System.out.println();
                System.out.println(text.trim());
                System.out.println();*/
            }

            Instant end = Instant.now();
            Duration duration = Duration.between(init,  end);
            log.info("Shipment convert for {} time {}", envio.getShipmentCode(), duration);

        }

        return envio;
    }

    private static Shipment buildShipment(String[] index) {

        if(!index[11].contains("Venda") || !index[11].contains("Envio")){
            throw new IllegalArgumentException("Arquivo  enviado não está no padrão esperado");
        }

        Shipment envio = new Shipment();

        envio.setProductName(index[0]);
        envio.setProductDesc(index[1] + " " + index[2]);
        envio.setSku(index[5]);

        String[]  shipmentCodeMoreSaleCode = index[11].split("\\s+");
        envio.setSaleCode(shipmentCodeMoreSaleCode[1]);
        //envio.setSaleCode(UUID.randomUUID().toString());

        envio.setShipmentCode(shipmentCodeMoreSaleCode[3]);
        //envio.setShipmentCode(UUID.randomUUID().toString());
        envio.setShipType(index[12]);
        envio.setZone(index[13]);
        envio.setReceiverNeighbor(index[14]);
        envio.setReceiverName(index[15].replace("Destinatario:","").trim());
        envio.setReceiverNickName(index[16].replace("Nickname:","").trim());
        envio.setReceiverAddress(index[17].replace("Endereço:","").trim());
        envio.setReceiverAddressComp(index[18].replace("Complemento:","").trim());
        envio.setReceiverCity(index[19].replace("Cidade:","").trim());
        envio.setReceiverCep(index[20].replace("CEP:","").trim());

        return envio;
    }

}
