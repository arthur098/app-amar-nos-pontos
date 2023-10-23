package br.com.amar.nos.pontos.asynctask.contrato;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

public class SalvaContratoAsyncTask extends AsyncTask<Void, Void, Void> {

    private final Contrato contrato;
    private final ContratoDAO contratoDAO;
    private SalvaContratoListener listener;

    public SalvaContratoAsyncTask(Contrato contrato, ContratoDAO dao, SalvaContratoListener listener) {
        this.contrato = contrato;
        this.listener = listener;
        this.contratoDAO = dao;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        contratoDAO.save(contrato);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        listener.onComplete();
        super.onPostExecute(unused);
    }

    public interface SalvaContratoListener {
        void onComplete();
    }
}
