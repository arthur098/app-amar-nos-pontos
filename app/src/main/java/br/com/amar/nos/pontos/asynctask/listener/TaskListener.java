package br.com.amar.nos.pontos.asynctask.listener;

public interface TaskListener<T> {
    void onComplete(T t);
}
