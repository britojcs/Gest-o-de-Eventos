package br.com.gestaodeeventos.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.model.Evento;
import br.com.gestaodeeventos.ui.adapter.EventoAdapter;
import br.com.gestaodeeventos.ui.presenter.EventoListPresenter;
import br.com.gestaodeeventos.ui.view.EventoListView;
import br.com.gestaodeeventos.util.Constants;
import br.com.gestaodeeventos.util.ItemClickListener;
import butterknife.BindView;

public class EventoListActivity extends BaseActivity implements EventoListView {

    public static final String TAG = EventoListActivity.class.getSimpleName();

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private EventoListPresenter presenter;
    private EventoAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_evento_lista;
    }

    @Override
    protected int getMenu() {
        return R.menu.menu_search;
    }

    @Override
    protected void adapterFilter(String query) {
        adapter.getFilter().filter(query);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupAdapter();
        setupRecyclerView();
        setupPresenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.unsubscribe();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setupAdapter() {
        adapter = new EventoAdapter(this, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.itemClicked(view, position);
            }
        });
    }

    private void setupPresenter() {
        presenter = new EventoListPresenter();
        presenter.attachView(this);
        presenter.subscribe();
        presenter.update(-1);
    }

    private void setupRecyclerView() {
        recyclerView.hasFixedSize();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider2));
        recyclerView.addItemDecoration(dividerItemDecoration);

        Log.d(TAG, "--> set layout manager");
    }

    @Override
    public void showList() {
        Log.d(TAG, "--> show image list");
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showAddedList(List<Evento> eventos) {
        Log.d(TAG, "--> show added list");
        adapter.addEventos(eventos);
    }

    @Override
    public void showLoading() {
        Log.d(TAG, "--> Loading...");
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        Log.d(TAG, "--> ...done loading");
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(int errorMessage) {
        Log.d(TAG, "--> Error");
        Snackbar snackbar = Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.tentar_novamente, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.update(1);
                    }
                });
        snackbar.getView().setBackgroundResource(R.color.error);
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    @Override
    public void navigateToDetailScreen(View view, int position) {
        Intent intent = new Intent(this, ParticipanteListActivity.class);
        intent.putExtra(Constants.FLAG, adapter.getEventos().get(position));
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
