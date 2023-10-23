package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

public class SalvaEnderecoAsyncTask extends AsyncTask<Void, Void, Void> {

    private final Endereco endereco;
    private final EnderecoDAO dao;

    private SalvaEnderecoListener salvaEnderecoListener;

    public SalvaEnderecoAsyncTask(Endereco endereco, EnderecoDAO dao, SalvaEnderecoListener listener) {
        this.endereco = endereco;
        this.salvaEnderecoListener = listener;
        this.dao = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        dao.save(endereco);
        this.salvaEnderecoListener.onComplete();
        return null;
    }

    public interface SalvaEnderecoListener {
        void onComplete();
    }
}
