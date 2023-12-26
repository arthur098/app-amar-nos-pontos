package br.com.amar.nos.pontos.asynctask.contrato;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.TaskListener;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

import java.util.List;

public class BuscaContratoPorIdPessoaTask extends AsyncTask<Long, Void, List<Contrato>> {

    private final Long idPessoa;
    private final ContratoDAO contratoDAO;
    private final TaskListener<List<Contrato>> listener;

    public BuscaContratoPorIdPessoaTask(Long idPessoa, ContratoDAO contratoDAO, TaskListener<List<Contrato>> listener) {
        this.idPessoa = idPessoa;
        this.contratoDAO = contratoDAO;
        this.listener = listener;
    }

    @Override
    protected List<Contrato> doInBackground(Long... longs) {
        return this.contratoDAO.findByIdPessoa(this.idPessoa);
    }

    @Override
    protected void onPostExecute(List<Contrato> contratos) {
        this.listener.onComplete(contratos);
        super.onPostExecute(contratos);
    }
}
