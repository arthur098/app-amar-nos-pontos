package br.com.amar.nos.pontos.database.dao;

import androidx.room.*;
import br.com.amar.nos.pontos.model.Endereco;

import java.util.List;


@Dao
public interface EnderecoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Endereco endereco);

    @Delete
    void delete(Endereco endereco);

    @Query("SELECT e.* FROM Endereco e WHERE e.id = :idEndereco")
    Endereco findById(Long idEndereco);

    @Query("SELECT e.* FROM Endereco e")
    List<Endereco> list();

    @Query("SELECT e.* FROM Endereco e WHERE e.idPessoa = :idPessoa")
    List<Endereco> listByIdPessoa(Long idPessoa);
}
