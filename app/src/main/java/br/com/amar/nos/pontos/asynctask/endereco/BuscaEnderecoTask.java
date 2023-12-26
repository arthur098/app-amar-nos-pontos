package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.TaskListener;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

public class BuscaEnderecoTask extends AsyncTask<Long, Integer, Endereco> {

    private final Long idEndereco;
    private final EnderecoDAO dao;
    private TaskListener<Endereco> listener;

    public BuscaEnderecoTask(Long idEndereco, EnderecoDAO dao, TaskListener<Endereco> listener) {
        this.idEndereco = idEndereco;
        this.listener = listener;
        this.dao = dao;
    }

    @Override
    protected Endereco doInBackground(Long... Long) {
        Endereco endereco = dao.findById(idEndereco);
        return endereco != null ? endereco : new Endereco();
    }

    @Override
    protected void onPostExecute(Endereco endereco) {
        this.listener.onComplete(endereco);
        super.onPostExecute(endereco);
    }
}
