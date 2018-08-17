package br.com.gestaodeeventos.ui.presenter;

import br.com.gestaodeeventos.ui.view.View;

public interface Presenter<T extends View> {

    void attachView(T view);

}