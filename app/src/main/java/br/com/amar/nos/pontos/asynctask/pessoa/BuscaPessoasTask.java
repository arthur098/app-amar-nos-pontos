package br.com.amar.nos.pontos.asynctask.pessoa;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.TaskListener;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.model.Pessoa;

import java.util.List;

public class BuscaPessoasTask extends AsyncTask<Void, Void, List<Pessoa>> {
    private final PessoaDAO pessoaDAO;
    private final TaskListener<List<Pessoa>> listener;

    public BuscaPessoasTask(PessoaDAO pessoaDAO, TaskListener<List<Pessoa>> listener) {
        this.pessoaDAO = pessoaDAO;
        this.listener = listener;
    }

    @Override
    protected List<Pessoa> doInBackground(Void... voids) {
        return this.pessoaDAO.list();
    }

    @Override
    protected void onPostExecute(List<Pessoa> pessoas) {
        this.listener.onComplete(pessoas);
        super.onPostExecute(pessoas);
    }
}
