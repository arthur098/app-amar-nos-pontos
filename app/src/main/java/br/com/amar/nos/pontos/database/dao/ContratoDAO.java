package br.com.amar.nos.pontos.database.dao;

import androidx.room.*;
import br.com.amar.nos.pontos.model.Contrato;

import java.util.List;


@Dao
public interface ContratoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Contrato contrato);

    @Delete
    void delete(Contrato contrato);

    @Query("SELECT c.* FROM Contrato c WHERE c.id = :idContrato")
    Contrato findById(Long idContrato);

    @Query("SELECT c.* FROM Contrato c")
    List<Contrato> list();

    @Query("SELECT c.* FROM Contrato c WHERE c.idPessoa = :idPessoa")
    List<Contrato> findByIdPessoa(Long idPessoa);
}
