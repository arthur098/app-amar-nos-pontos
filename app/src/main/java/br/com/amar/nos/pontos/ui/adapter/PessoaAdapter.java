package br.com.amar.nos.pontos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.enumerator.EnumEstadoCivil;
import br.com.amar.nos.pontos.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaAdapter extends BaseAdapter {

    private static List<Pessoa> pessoas;
    private final Context context;

    public PessoaAdapter(Context context) {
        this.context = context;
        Pessoa pessoa = new Pessoa();
        pessoa.setNomeCompleto("Arthur Oliveira Rodrigues");
        pessoa.setId(1L);
        pessoa.setEstadoCivil(EnumEstadoCivil.CASADO);
        pessoa.setNacionalidade("Brasileiro");
        pessoa.setProfissao("Programador");
        pessoa.setCpfCnpj("70302652116");

        PessoaDAO.save(pessoa);

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
