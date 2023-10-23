package br.com.amar.nos.pontos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.EnderecoDAO;
import br.com.amar.nos.pontos.model.Endereco;

import java.util.ArrayList;

public class EnderecoAdapter extends BaseAdapter {

    private final ArrayList<Endereco> enderecos = new ArrayList<>();
    private final Context context;
    private final Long idPessoa;
    private final EnderecoDAO dao;

    public EnderecoAdapter(Context context, Long idPessoa, EnderecoDAO dao) {
        this.context = context;
        this.idPessoa = idPessoa;
        this.dao = dao;
    }

    @Override
    public int getCount() {
        return this.enderecos.size();
    }

    @Override
    public Endereco getItem(int i) {
        return this.enderecos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return this.enderecos.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = getView(viewGroup);

        Endereco endereco = enderecos.get(i);
        TextView textViewEndereco = view.findViewById(R.id.item_endereco_descricao);

        String texto = endereco.getComplemento() + " " + endereco.getLogradouro();
        textViewEndereco.setText(texto);

        return view;
    }

    public View getView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_endereco, viewGroup, false);
    }

    public void atualizarEnderecos() {
        enderecos.clear();
        enderecos.addAll(this.dao.listByIdPessoa(idPessoa));
        this.notifyDataSetChanged();
    }
}
