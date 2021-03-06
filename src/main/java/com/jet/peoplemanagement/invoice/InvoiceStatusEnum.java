package com.jet.peoplemanagement.invoice;

public enum InvoiceStatusEnum {

    VISUALIZACAO("Visualizacao", "Fatura gerada para visualização ou envio ao cliente"),
    GERADO("Gerado", "Fatura gerada para visualização ou envio ao cliente"),
    EM_ATRASO("Em atraso", "Fatura está em atraso"),
    PAGO("Pago", "Fatura paga / finalizada"),
    CANCELADO("Cancelado", "Fatura cancelada"),
    ;

    private String keyword;
    private String desc;

    InvoiceStatusEnum(String keyword, String desc) {
        this.keyword = keyword;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
