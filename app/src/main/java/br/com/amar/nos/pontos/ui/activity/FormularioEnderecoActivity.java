package br.com.amar.nos.pontos.ui.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import br.com.amar.nos.pontos.databinding.ActivityFormularioEnderecoBinding;

public class FormularioEnderecoActivity extends AppCompatActivity {

    ActivityFormularioEnderecoBinding viewBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBind = ActivityFormularioEnderecoBinding.inflate(getLayoutInflater());
        setContentView(viewBind.getRoot());
        setSupportActionBar(viewBind.toolbar);

    }
}
