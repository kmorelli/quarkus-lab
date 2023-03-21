package kmorelli.auriga.services.json;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;

@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class MeioComunicacao {

    public int seq;
    public String tipo;
    public String descricao;

    @JsonbCreator
    public MeioComunicacao(
            @JsonbProperty("seq") int seq,
            @JsonbProperty("tipo") String tipo,
            @JsonbProperty("descricao") String descricao) {

        this.seq = seq;
        this.tipo = tipo;
        this.descricao = descricao;
    }

    public MeioComunicacao(String tipo, String descricao) {
        this(1000, tipo, descricao);
    }

}
