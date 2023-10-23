package br.com.amar.nos.pontos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.ContratoDAO;
import br.com.amar.nos.pontos.model.Contrato;

import java.util.List;

public class ContratoAdapter extends BaseAdapter {

    private final ContratoDAO dao;
    private List<Contrato> contratos;
    private final Context context;

    public ContratoAdapter(Context context, ContratoDAO dao, List<Contrato> contratos) {
        this.context = context;
        this.contratos = contratos;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return contratos.size();
    }

    @Override
    public Contrato getItem(int i) {
        return contratos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return contratos.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = getView(viewGroup);

        Contrato contrato = contratos.get(i);

        TextView produtoView = view.findViewById(R.id.item_contrato_produto);
        produtoView.setText(contrato.getProduto());

        return view;
    }

    private View getView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_contrato, viewGroup, false);
    }

    public void atualizaContratos(Long idPessoa) {
        this.contratos = dao.findByIdPessoa(idPessoa);
        this.notifyDataSetChanged();
    }
}
