package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.TaskListener;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

import java.util.List;

public class BuscaEnderecoPorIdPessoaTask extends AsyncTask<Long, Void, List<Endereco>> {


    private final Long idPessoa;
    private final EnderecoDAO enderecoDAO;
    private final TaskListener<List<Endereco>> listener;

    public BuscaEnderecoPorIdPessoaTask(Long idPessoa, EnderecoDAO enderecoDAO, TaskListener<List<Endereco>> listener) {
        this.idPessoa = idPessoa;
        this.enderecoDAO = enderecoDAO;
        this.listener = listener;
    }

    @Override
    protected List<Endereco> doInBackground(Long... longs) {
        return this.enderecoDAO.listByIdPessoa(idPessoa);
    }

    @Override
    protected void onPostExecute(List<Endereco> enderecos) {
        this.listener.onComplete(enderecos);
        super.onPostExecute(enderecos);
    }
}
