package br.com.gestaodeeventos.ui.view;

public interface ListView extends View {

    void showList();

    void showLoading();

    void hideLoading();

    void showError(int message);

    void navigateToDetailScreen(android.view.View view, int position);

}
