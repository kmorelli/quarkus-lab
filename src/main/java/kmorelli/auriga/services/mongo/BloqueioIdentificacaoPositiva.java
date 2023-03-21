package kmorelli.auriga.services.mongo;

import java.util.Date;

import org.bson.types.ObjectId;

import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(database = "bloqueio")
public class BloqueioIdentificacaoPositiva {

    public ObjectId id;
    public long cpf;
    public Date timestampBloqueio;
    public int quantidadeTentativas;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (int) (cpf ^ (cpf >>> 32));
        result = prime * result + ((timestampBloqueio == null) ? 0 : timestampBloqueio.hashCode());
        result = prime * result + quantidadeTentativas;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BloqueioIdentificacaoPositiva other = (BloqueioIdentificacaoPositiva) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (cpf != other.cpf)
            return false;
        if (timestampBloqueio == null) {
            if (other.timestampBloqueio != null)
                return false;
        } else if (!timestampBloqueio.equals(other.timestampBloqueio))
            return false;
        if (quantidadeTentativas != other.quantidadeTentativas)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "BloqueioIdentificacaoPositiva [id=" + id + ", cpf=" + cpf + ", timestampBloqueio=" + timestampBloqueio
                + ", quantidadeTentativas=" + quantidadeTentativas + "]";
    }

}
