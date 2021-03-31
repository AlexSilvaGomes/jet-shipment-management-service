package com.jet.peoplemanagement.delivery;

public enum DeliveryStatusEnum {

    POSTADO("Postado", "Postado pelo cliente"),
    COLETADO("Coletado", "Coletado pelo motoboy"),
    ENTRADA_BASE("Distribuição", "Entrada na base"),
    ATRIBUIDO("Atribuído", "Ditribuído na base"),
    REATRIBUIDO("Reatribuído", "Outro motoboy fará a entrega"),
    EM_CURSO("Saiu para entrega", "Está em curso para entrega"),
    ENTREGUE("Entregue", "Entrega realizada"),
    NAO_ENTREGUE("Não Entregue", "Cliente não pôde receber ou não houve tempo para entregar"),
    ;

    //Adicionar colaborador que recebeu na ENTRADA_BASE
    //Adicionar colaborador que atribuiu e conferiu

    private String keyword;
    private String desc;

    DeliveryStatusEnum(String keyword, String desc) {
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
