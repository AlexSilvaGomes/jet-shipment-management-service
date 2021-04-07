package com.jet.peoplemanagement.invoice;

import com.jet.peoplemanagement.model.BaseDocument;
import com.jet.peoplemanagement.model.Client;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Document(collection = "invoices")
@Data
public class Invoice extends BaseDocument {

    @DBRef
    @NotNull
    @Indexed
    private Client client;

    private int totalItems;

    private BigDecimal amount;
    private BigDecimal discount;
    private BigDecimal tax;

    private InvoiceStatusEnum status = InvoiceStatusEnum.GERADO;

    private List<InvoiceItems> items;
}
