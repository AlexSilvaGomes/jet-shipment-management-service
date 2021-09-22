package com.jet.peoplemanagement.invoice;

import com.jet.peoplemanagement.model.BaseDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "invoiceItems")
public class InvoiceItems extends BaseDocument {

    @NotBlank
    @Indexed(unique = false, name = "invoiceIndex")
    private String invoiceId;

    @NotBlank
    @Indexed(unique = false, name = "shipmentIndex")
    private String shipmentCode;

    private String sku;

    private Double price;
}
