package br.com.amar.nos.pontos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.enumerator.EnumEstadoCivil;
import br.com.amar.nos.pontos.model.Pessoa;

import java.util.List;

public class PessoaAdapter extends BaseAdapter {

    private static List<Pessoa> pessoas;
    private final Context context;

    public PessoaAdapter(Context context) {
        this.context = context;

        pessoas = PessoaDAO.listar();
    }

    @Override
    public int getCount() {
        return pessoas.size();
    }

    @Override
    public Pessoa getItem(int i) {
        return pessoas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return pessoas.get(i).getId();
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view = getView(viewGroup);

        Pessoa pessoa = pessoas.get(i);
        TextView textViewNome = view.findViewById(R.id.item_pessoa_nome);

        textViewNome.setText(pessoa.getNomeCompleto());

        return view;
    }

    private View getView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_pessoa, viewGroup, false);
    }

    public void atualizaPessoa() {
        pessoas = PessoaDAO.listar();
        this.notifyDataSetChanged();
    }

}
