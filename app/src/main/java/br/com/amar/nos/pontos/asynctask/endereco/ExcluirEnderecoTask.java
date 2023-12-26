package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.ActionTaskListener;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

import java.util.List;

public class ExcluirEnderecoTask extends AsyncTask<List<Endereco>, Void, Void> {
    private final List<Endereco> enderecos;
    private final EnderecoDAO enderecoDAO;
    private final ActionTaskListener listener;

    public ExcluirEnderecoTask(List<Endereco> enderecos, EnderecoDAO enderecoDAO, ActionTaskListener listener) {
        this.enderecos = enderecos;
        this.enderecoDAO = enderecoDAO;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(List<Endereco>... lists) {
        Endereco[] enderecoArray = new Endereco[enderecos.size()];

        for (int i = 0; i < enderecos.size(); i++) {
            enderecoArray[i] = enderecos.get(i);
        }

        this.enderecoDAO.delete(enderecoArray);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        this.listener.onComplete();
        super.onPostExecute(unused);
    }
}
