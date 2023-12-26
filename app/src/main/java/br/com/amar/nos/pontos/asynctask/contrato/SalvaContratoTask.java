package br.com.amar.nos.pontos.asynctask.contrato;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.ActionTaskListener;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

public class SalvaContratoTask extends AsyncTask<Void, Void, Void> {

    private final Contrato contrato;
    private final ContratoDAO contratoDAO;
    private final ActionTaskListener listener;

    public SalvaContratoTask(Contrato contrato, ContratoDAO dao, ActionTaskListener listener) {
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
}
