package br.com.amar.nos.pontos.asynctask.contrato;

import android.os.AsyncTask;
import br.com.amar.nos.pontos.asynctask.listener.ActionTaskListener;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

import java.util.List;

public class ExcluirContratoTask extends AsyncTask<List<Contrato>, Void, Void> {
    private final ContratoDAO contratoDAO;
    private final ActionTaskListener listener;
    private final List<Contrato> contratos;

    public ExcluirContratoTask(ContratoDAO contratoDAO, List<Contrato> contratos, ActionTaskListener listener) {
        this.contratos = contratos;
        this.contratoDAO = contratoDAO;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(List<Contrato>... contratoes) {
        Contrato[] contratoArray = new Contrato[contratos.size()];

        for (int i = 0; i < contratos.size(); i++) {
            contratoArray[i] = contratos.get(i);
        }

        this.contratoDAO.delete(contratoArray);
        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        listener.onComplete();
        super.onPostExecute(unused);
    }
}
