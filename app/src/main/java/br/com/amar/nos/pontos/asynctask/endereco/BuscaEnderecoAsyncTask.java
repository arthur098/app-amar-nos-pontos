package br.com.amar.nos.pontos.asynctask.endereco;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

public class BuscaEnderecoAsyncTask extends AsyncTask<Long, Integer, Endereco> {

    private final Long idEndereco;
    private EnderecoBuscadoListener listener;

    public BuscaEnderecoAsyncTask(Long idEndereco, EnderecoBuscadoListener listener) {
        this.idEndereco = idEndereco;
        this.listener = listener;
    }

    @Override
    protected Endereco doInBackground(Long... Long) {
        Endereco endereco = EnderecoDAO.buscarPorId(idEndereco);
        return endereco != null ? endereco : new Endereco();
    }

    @Override
    protected void onPostExecute(Endereco endereco) {
        this.listener.onEnderecoBuscado(endereco);
        super.onPostExecute(endereco);
    }

    public interface EnderecoBuscadoListener {
        void onEnderecoBuscado(Endereco endereco);
    }
}
