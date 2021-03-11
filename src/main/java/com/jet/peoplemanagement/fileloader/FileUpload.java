package com.jet.peoplemanagement.fileloader;

import com.jet.peoplemanagement.model.BaseDocument;
import com.jet.peoplemanagement.model.Client;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Document(collection = "uploadedFiles")
public class FileUpload extends BaseDocument {

    @DBRef
    @NotNull
    @Indexed
    private Client client;

    @NotBlank
    private String name;

    @NotBlank
    @Indexed(unique = true)
    private String shipmentCode;

    @NotBlank
    private String status;

    @NotBlank
    private String message;
}
