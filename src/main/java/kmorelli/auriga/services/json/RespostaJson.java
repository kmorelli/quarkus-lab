package kmorelli.auriga.services.json;

import java.util.ArrayList;
import java.util.List;

import javax.json.bind.annotation.JsonbCreator;
import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbPropertyOrder;
import javax.json.bind.config.PropertyOrderStrategy;

@JsonbPropertyOrder(PropertyOrderStrategy.ANY)
public class RespostaJson {

    public int codigo;
    public String perfil;
    public List<MeioComunicacao> meios;
    
    @JsonbCreator
    public RespostaJson(@JsonbProperty("codigo") int codigo , @JsonbProperty("perfil") String perfil) {
        this.codigo = codigo;
        this.perfil = perfil;
    }

    public void addMeioComunicacao(MeioComunicacao meio) {
        if (meios == null) {
            meios = new ArrayList<MeioComunicacao>();
        }
        meios.add(meio);
    }

}
