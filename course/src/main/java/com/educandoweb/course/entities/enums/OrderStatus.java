package com.educandoweb.course.entities.enums;

public enum OrderStatus {
    WAITING_PAYMENT(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELED(5);

    /*
    O Enum precisa ter enumeração, porque caso seja adicionado algum outro tipo de status, a ordem entre eles
    fica alterada.
     */

    private int code; // variavel que guarda o codigo de cada status

    OrderStatus(int code) {
        this.code = code; //esse construtor recebe o número e o armazena no atributo code
    }

    public int getCode() { // esse permite consultar o status de cada codigo
        return code;
    }

    public static OrderStatus valueOf(int code) {
        for (OrderStatus value : OrderStatus.values()) { // pra percorrer todos os OrderStatus
            if (value.getCode() == code) {
                return value;
    /*
    É um metodo que vai permitir encontrar o status do meu pedido a partir do codigo numerico armazenado
    sttaic - permite chamar o método diretamente pela classe sem criar uma variavel antes

    for - vai percorrer todo os Enum de OrderStatus e comparar com o valor em questão de um Order,
    no qual está sendo avaliado

    if - condição, que a partir do "get"--(que é o status atual de value.getCode())-- que pega o valor,
    caso igual á algum codigo do status dado por Enum vai retornar o valor do codigo
    */
            }
        }
        throw  new IllegalArgumentException("Invalid OrderStatus code");
        // lança um erro se o codigo pego pelo get n for igual a nenhum codigo listado em OrderStatus
    }

}
