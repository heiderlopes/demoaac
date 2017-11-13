package br.com.heiderlopes.demoaac.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.os.Bundle;

import java.util.List;

import br.com.heiderlopes.demoaac.R;
import br.com.heiderlopes.demoaac.model.Tarefa;
import br.com.heiderlopes.demoaac.view.adapter.TarefaAdapter;
import br.com.heiderlopes.demoaac.viewmodel.TarefaModel;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView rv;
    private TarefaAdapter adapter;
    private List<Tarefa> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        rv = findViewById(R.id.rv);

        tarefas = new ArrayList<>();

        ViewModelProviders.of(this)
                .get(TarefaModel.class)
                .getTarefas()
                .observe(this, new Observer<List<Tarefa>>() {
                    @Override
                    public void onChanged(@Nullable List<Tarefa> tarefas) {
                        adapter.setList(tarefas);
                        rv.getAdapter().notifyDataSetChanged();
                    }
                });

        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TarefaAdapter(tarefas);
        rv.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TarefaDialog dialog = new TarefaDialog();
                dialog.show(getFragmentManager(), "CriarTarefa");
            }
        });
    }
}