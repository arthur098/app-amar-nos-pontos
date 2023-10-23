package br.com.amar.nos.pontos.database;


import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import br.com.amar.nos.pontos.database.dao.*;
import br.com.amar.nos.pontos.model.Contrato;
import br.com.amar.nos.pontos.model.Endereco;
import br.com.amar.nos.pontos.model.Pessoa;

@Database(entities = { Pessoa.class, Endereco.class, Contrato.class }, version = 1, exportSchema = false)
public abstract class AmarDatabase extends RoomDatabase {

    public static final String NOME_BANCO_DE_DADOS = "amar-nos-pontos.db";

    public abstract PessoaDAO pessoaDAO();
    public abstract ContratoDAO contratoDAO();
    public abstract EnderecoDAO enderecoDAO();

    public static AmarDatabase getInstance(Context context) {
        return Room.databaseBuilder(context, AmarDatabase.class, NOME_BANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();
    }
}
