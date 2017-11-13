package br.com.heiderlopes.demoaac.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.os.Bundle;

import java.util.List;

import br.com.heiderlopes.demoaac.R;
import br.com.heiderlopes.demoaac.dao.BaseDados;
import br.com.heiderlopes.demoaac.model.Tarefa;
import br.com.heiderlopes.demoaac.view.adapter.TarefaAdapter;
import br.com.heiderlopes.demoaac.view.listener.OnItemClickListener;
import br.com.heiderlopes.demoaac.viewmodel.TarefaModel;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaTarefasActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private RecyclerView rvTarefas;
    private TarefaAdapter adapter;
    private List<Tarefa> tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        rvTarefas = findViewById(R.id.rvTarefas);

        tarefas = new ArrayList<>();

        ViewModelProviders.of(this)
                .get(TarefaModel.class)
                .getTarefas()
                .observe(this, new Observer<List<Tarefa>>() {
                    @Override
                    public void onChanged(@Nullable List<Tarefa> tarefas) {
                        adapter.setList(tarefas);
                        rvTarefas.getAdapter().notifyDataSetChanged();
                    }
                });

        rvTarefas.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TarefaAdapter(tarefas, deleteClick);
        rvTarefas.setAdapter(adapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TarefaDialog dialog = new TarefaDialog();
                dialog.show(getFragmentManager(), "CriarTarefa");
            }
        });
    }

    private OnItemClickListener deleteClick = new OnItemClickListener() {
        @Override
        public void onClick(int position) {
            BaseDados db = BaseDados.getDatabase(ListaTarefasActivity.this.getApplicationContext());
            new ApagarAsyncTask(db).execute(adapter.getTarefa(position));
        }
    };

    private class ApagarAsyncTask extends AsyncTask<Tarefa, Void, Void> {

        private BaseDados db;

        ApagarAsyncTask(BaseDados appDatabase) {
            db = appDatabase;
        }

        @Override
        protected Void doInBackground(final Tarefa... params) {
            db.tarefaDao().apagar(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Toast.makeText(ListaTarefasActivity.this, "Registro excluído com sucesso", Toast.LENGTH_SHORT).show();
        }
    }
}