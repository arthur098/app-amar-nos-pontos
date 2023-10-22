package br.com.amar.nos.pontos.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import br.com.amar.nos.pontos.model.Endereco;

import java.util.List;

public class SelecionarEnderecoDialog extends DialogFragment {

    private final Context context;
    private final SelecionarEnderecoListener listener;
    private final List<Endereco> enderecos;
    public SelecionarEnderecoDialog(Context context, List<Endereco> enderecos, SelecionarEnderecoListener listener) {
        this.context = context;
        this.enderecos = enderecos;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        ListAdapter listAdapter = buildAdapter();
        builder
                .setTitle("Selecione o endereço")
                .setSingleChoiceItems(listAdapter, 0, (dialogInterface, i) ->{
                    listener.onClick((Endereco) listAdapter.getItem(i));
                    dialogInterface.dismiss();
                })
                .setNegativeButton("Cancelar", null);

        return builder.create();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void exibeDialogo(FragmentManager supportFragmentManager) {
        this.show(supportFragmentManager, "selecionar_endereco");
    }

    private ListAdapter buildAdapter() {
        return new ArrayAdapter<>(context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, this.enderecos);
    }

    public interface SelecionarEnderecoListener {
        void onClick(Endereco enderecoSelecionado);
    }
}
