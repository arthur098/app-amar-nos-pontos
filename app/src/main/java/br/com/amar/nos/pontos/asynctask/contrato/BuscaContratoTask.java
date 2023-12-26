package br.com.amar.nos.pontos.asynctask.contrato;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.TaskListener;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

public class BuscaContratoTask extends AsyncTask<Long, Void, Contrato> {

    private final Long idContrato;
    private final ContratoDAO dao;
    private final TaskListener<Contrato> listener;
    public BuscaContratoTask(Long idContrato, ContratoDAO dao, TaskListener<Contrato> listener) {
        this.idContrato = idContrato;
        this.listener = listener;
        this.dao = dao;
    }

    @Override
    protected Contrato doInBackground(Long... longs) {
        return dao.findById(idContrato);
    }

    @Override
    protected void onPostExecute(Contrato contrato) {
        this.listener.onComplete(contrato);
        super.onPostExecute(contrato);
    }
}
