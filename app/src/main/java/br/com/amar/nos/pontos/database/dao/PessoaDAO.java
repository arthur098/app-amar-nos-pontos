package br.com.amar.nos.pontos.database.dao;

import androidx.room.*;
import br.com.amar.nos.pontos.model.Pessoa;

import java.util.List;


@Dao
public interface PessoaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void save(Pessoa pessoa);

    @Delete
    void delete(Pessoa pessoa);

    @Query("SELECT p.* FROM Pessoa p WHERE p.id = :idPessoa")
    Pessoa findById(Long idPessoa);

    @Query("SELECT p.* FROM Pessoa p")
    List<Pessoa> list();
}
