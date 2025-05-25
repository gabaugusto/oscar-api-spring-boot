package br.com.oscar.api.repositories;

import br.com.oscar.api.models.IndicadosAoOscar;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

    /*
        // Uma Repository é uma interface que estende a interface JpaRepository
        // JpaRepository é uma interface que possui métodos prontos para se comunicar com o banco de dados

        Classe responsavel por se comunicar com o banco de dados:
        SALVAR, LER, ATUALIZAR E DELETAR
    */

@Repository
public interface IndicadosAoOscarRepository extends JpaRepository<IndicadosAoOscar, Integer> {

    List<IndicadosAoOscar> findBynomeIndicadoContaining(String name);

    Optional<IndicadosAoOscar> findIndicadoByidRegistro(int id);

    // Lista Opcionional
    Optional<List<IndicadosAoOscar>> findIndicadoBynomeIndicado(String name);

    List<IndicadosAoOscar> findIndicadoByanoFilmagem(int id);

}
