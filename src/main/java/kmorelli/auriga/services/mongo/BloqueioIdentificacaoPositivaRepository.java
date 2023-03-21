package kmorelli.auriga.services.mongo;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import org.bson.conversions.Bson;

import com.mongodb.client.model.Filters;

import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoRepository;
import io.smallrye.mutiny.Uni;

@ApplicationScoped
public class BloqueioIdentificacaoPositivaRepository
        implements ReactivePanacheMongoRepository<BloqueioIdentificacaoPositiva> {

    public Uni<Optional<BloqueioIdentificacaoPositiva>> findByCPF(long cpf) {

        Uni<List<BloqueioIdentificacaoPositiva>> listaUni = find("cpf", cpf).list();

        return listaUni.onItem().transformToUni(lista -> {

            if (lista.size() == 1) {
                return Uni.createFrom().item(Optional.of(lista.get(0)));
            }

            if (lista.size() > 1) {
                return resolveRedundancia(lista);
            }

            return Uni.createFrom().item(Optional.empty());

        });

    }

    private Uni<Optional<BloqueioIdentificacaoPositiva>> resolveRedundancia(List<BloqueioIdentificacaoPositiva> lista) {

        Optional<BloqueioIdentificacaoPositiva> item = lista.stream().reduce(
                (parcial, atual) -> parcial.quantidadeTentativas >= atual.quantidadeTentativas ? parcial : atual);

        if (item.isEmpty()) {
            return Uni.createFrom().item(item);
        }

        Bson deleteQuery = Filters.ne("_id", item.get().id);
        Uni<Optional<BloqueioIdentificacaoPositiva>> itemUni = mongoCollection().deleteMany(deleteQuery)
                .replaceWith(item);

        return itemUni;
    }

}
