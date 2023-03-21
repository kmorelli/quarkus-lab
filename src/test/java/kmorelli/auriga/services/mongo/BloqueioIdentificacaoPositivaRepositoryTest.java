package kmorelli.auriga.services.mongo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.Date;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
public class BloqueioIdentificacaoPositivaRepositoryTest {
    
    @Inject
    BloqueioIdentificacaoPositivaRepository repository;

    @Test
    void testFindByCPF() {

        var bloqueio = new BloqueioIdentificacaoPositiva();
        bloqueio.cpf = 1112223345;
        bloqueio.timestampBloqueio = Date.from(Instant.now());
        bloqueio.quantidadeTentativas = 1;
        repository.persist(bloqueio).await().indefinitely();

        var bloqueio3 = new BloqueioIdentificacaoPositiva();
        bloqueio3.cpf = 1112223345;
        bloqueio3.timestampBloqueio = Date.from(Instant.now());
        bloqueio3.quantidadeTentativas = 5;
        repository.persist(bloqueio3).await().indefinitely();

        var bloqueio2 = new BloqueioIdentificacaoPositiva();
        bloqueio2.cpf = 1112223345;
        bloqueio2.timestampBloqueio = Date.from(Instant.now());
        bloqueio2.quantidadeTentativas = 4;
        repository.persist(bloqueio2).await().indefinitely();

        System.out.println(repository.find("cpf", 1112223345).count().await().indefinitely());

        var busca = repository.findByCPF(1112223345L).await().indefinitely().get();

        System.out.println(repository.find("cpf", 1112223345).count().await().indefinitely());

        assertEquals(bloqueio3, busca);
    }

    @Test
    void naoExisteRegistro() {
        var busca = repository.findByCPF(33344455566L).await().indefinitely();
        assertTrue(busca.isEmpty());
    }
}
