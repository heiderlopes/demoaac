package br.com.heiderlopes.demoaac.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import java.util.List;

import br.com.heiderlopes.demoaac.dao.BaseDados;
import br.com.heiderlopes.demoaac.model.Tarefa;
public class TarefaModel extends AndroidViewModel {
    private LiveData<List<Tarefa>> tarefas;
    private BaseDados bd;

    public TarefaModel(Application application) {
        super(application);
        bd = BaseDados.getDatabase(application.getApplicationContext());
        carregarDados();
    }

    public LiveData<List<Tarefa>> getTarefas() {
        return tarefas;
    }

    private void carregarDados() {
        tarefas = bd.tarefaDao().listarTodas();
    }
}