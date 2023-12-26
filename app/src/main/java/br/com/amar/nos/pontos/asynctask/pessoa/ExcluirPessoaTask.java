package br.com.amar.nos.pontos.asynctask.pessoa;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.ActionTaskListener;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.model.Pessoa;

public class ExcluirPessoaTask extends AsyncTask<Pessoa, Void, Void> {
    private final Pessoa pessoa;
    private final PessoaDAO pessoaDAO;
    private final ActionTaskListener listener;

    public ExcluirPessoaTask(Pessoa pessoa, PessoaDAO pessoaDAO, ActionTaskListener listener) {
        this.pessoa = pessoa;
        this.pessoaDAO = pessoaDAO;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(Pessoa... longs) {
        this.pessoaDAO.delete(this.pessoa);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        this.listener.onComplete();
        super.onPostExecute(unused);
    }
}
