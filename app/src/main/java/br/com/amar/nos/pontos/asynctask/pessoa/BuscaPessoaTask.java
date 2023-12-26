package br.com.amar.nos.pontos.asynctask.pessoa;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.TaskListener;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.model.Pessoa;

public class BuscaPessoaTask extends AsyncTask<Long, Void, Pessoa> {

    private final PessoaDAO pessoaDAO;
    private final Long idPessoa;
    private final TaskListener<Pessoa> listener;

    public BuscaPessoaTask(Long idPessoa, PessoaDAO pessoaDAO, TaskListener<Pessoa> listener) {
        this.idPessoa = idPessoa;
        this.pessoaDAO = pessoaDAO;
        this.listener = listener;
    }

    @Override
    protected Pessoa doInBackground(Long... longs) {
        return this.pessoaDAO.findById(idPessoa);
    }

    @Override
    protected void onPostExecute(Pessoa pessoa) {
        this.listener.onComplete(pessoa);
        super.onPostExecute(pessoa);
    }
}
