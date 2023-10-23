package br.com.amar.nos.pontos.asynctask.contrato;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

public class BuscaContratoTask extends AsyncTask<Long, Void, Contrato> {

    private final Long idContrato;
    private final Long idPessoa;
    private final ContratoDAO dao;
    private BuscaContratoListener listener;
    public BuscaContratoTask(Long idContrato, Long idPessoa, ContratoDAO dao, BuscaContratoListener listener) {
        this.idContrato = idContrato;
        this.idPessoa = idPessoa;
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

    public interface BuscaContratoListener {
        void onComplete(Contrato contrato);
    }
}
