package br.com.amar.nos.pontos.asynctask.pessoa;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.ActionTaskListener;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.model.Pessoa;

public class SalvarPessoaTask extends AsyncTask<Void, Void, Void> {

    private final Pessoa pessoa;
    private final PessoaDAO pessoaDAO;
    private final ActionTaskListener listener;

    public SalvarPessoaTask(Pessoa pessoa, PessoaDAO pessoaDAO, ActionTaskListener listener) {
        this.pessoa = pessoa;
        this.pessoaDAO = pessoaDAO;
        this.listener = listener;
    }


    @Override
    protected Void doInBackground(Void... voids) {
        if(this.pessoa.getId() == null) {
            this.pessoaDAO.save(this.pessoa);
        } else {
            this.pessoaDAO.update(this.pessoa);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        listener.onComplete();
        super.onPostExecute(unused);
    }
}
