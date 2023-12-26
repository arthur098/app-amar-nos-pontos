package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.ActionTaskListener;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

public class SalvaEnderecoTask extends AsyncTask<Void, Void, Void> {

    private final Endereco endereco;
    private final EnderecoDAO dao;

    private final ActionTaskListener salvaEnderecoListener;

    public SalvaEnderecoTask(Endereco endereco, EnderecoDAO dao, ActionTaskListener listener) {
        this.endereco = endereco;
        this.salvaEnderecoListener = listener;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        this.dao.save(endereco);
        this.salvaEnderecoListener.onComplete();
        return null;
    }
}
