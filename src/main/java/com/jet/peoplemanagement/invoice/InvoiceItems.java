package com.jet.peoplemanagement.invoice;

import com.jet.peoplemanagement.model.BaseDocument;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;

@Data
@Document(collection = "invoiceItems")
public class InvoiceItems extends BaseDocument {

    @NotBlank
    @Indexed(unique = false, name = "invoiceIndex")
    private Invoice invoice;

    @NotBlank
    @Indexed(unique = false, name = "shipmentIndex")
    private String shipmentCode;
}
