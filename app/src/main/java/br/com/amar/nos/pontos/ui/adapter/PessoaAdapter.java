package br.com.amar.nos.pontos.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.com.amar.nos.pontos.R;
import br.com.amar.nos.pontos.database.dao.PessoaDAO;
import br.com.amar.nos.pontos.model.Pessoa;

import java.util.List;

public class PessoaAdapter extends BaseAdapter {

    private final PessoaDAO pessoaDAO;
    private List<Pessoa> pessoas;
    private final Context context;

    public PessoaAdapter(Context context, PessoaDAO pessoaDAO) {
        this.context = context;
        this.pessoas = pessoaDAO.list();
        this.pessoaDAO = pessoaDAO;
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

        Pessoa pessoa = this.pessoas.get(i);
        TextView textViewNome = view.findViewById(R.id.item_pessoa_nome);

        textViewNome.setText(pessoa.getNomeCompleto());

        return view;
    }

    private View getView(ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.item_pessoa, viewGroup, false);
    }

    public void atualizaPessoa() {
        this.pessoas = this.pessoaDAO.list();
        this.notifyDataSetChanged();
    }

    public void excluir(Long id) {
        this.pessoaDAO.delete(this.pessoaDAO.findById(id));
        atualizaPessoa();
    }
}
