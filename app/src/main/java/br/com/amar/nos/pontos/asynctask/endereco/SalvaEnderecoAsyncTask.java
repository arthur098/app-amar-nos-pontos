package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

public class SalvaEnderecoAsyncTask extends AsyncTask<Void, Void, Void> {

    private final Endereco endereco;

    private SalvaEnderecoListener salvaEnderecoListener;

    public SalvaEnderecoAsyncTask(Endereco endereco, SalvaEnderecoListener listener) {
        this.endereco = endereco;
        this.salvaEnderecoListener = listener;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        if (this.endereco.getId() == null || this.endereco.getId() == 0) {
            EnderecoDAO.save(this.endereco);
        } else {
            EnderecoDAO.update(this.endereco);
        }
        this.salvaEnderecoListener.onComplete();
        return null;
    }

    public interface SalvaEnderecoListener {
        void onComplete();
    }
}
