package br.com.gestaodeeventos.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.model.Evento;
import br.com.gestaodeeventos.api.model.Paginador;
import br.com.gestaodeeventos.api.model.Participante;
import br.com.gestaodeeventos.ui.adapter.ParticipanteAdapter;
import br.com.gestaodeeventos.ui.component.EndlessRecyclerViewScrollListener;
import br.com.gestaodeeventos.ui.component.HeaderView;
import br.com.gestaodeeventos.ui.presenter.ParticipanteListPresenter;
import br.com.gestaodeeventos.ui.view.ParticipanteListView;
import br.com.gestaodeeventos.util.Constants;
import br.com.gestaodeeventos.util.ImageUtil;
import br.com.gestaodeeventos.util.ItemClickListener;
import butterknife.BindView;

public class ParticipanteListActivity extends BaseToolbarActivity implements ParticipanteListView, AppBarLayout.OnOffsetChangedListener {

    public static final String TAG = ParticipanteListActivity.class.getSimpleName();

    @BindView(R.id.appbar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.toolbar_header_view)
    HeaderView toolbarHeaderView;
    @BindView(R.id.float_header_view)
    HeaderView floatHeaderView;
    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    @BindView(R.id.img_evento)
    ImageView imgEvento;
    @BindView(R.id.txt_registros)
    TextView txtRegistros;
    @BindView(R.id.progress)
    ProgressBar progressBar;

    private ParticipanteListPresenter presenter;
    private ParticipanteAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    private Paginador paginador;
    private Evento evento;
    private boolean isHideToolbarView = false;

    @Override
    protected int getLayout() {
        return R.layout.activity_participante_lista;
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
        setIndicator();

        evento = (Evento) getIntent().getSerializableExtra(Constants.FLAG);

        paginador = new Paginador();
        paginador.setPagina(1);
        paginador.setRegistrosPorPagina(Constants.REST_PER_PAGE_LIMIT);

        Glide.with(this)
                .applyDefaultRequestOptions(ImageUtil.imgOptions())
                .load(ImageUtil.base64ToBitmap(evento.getImagem()))
                .into(imgEvento);

        collapsingToolbarLayout.setTitle(" ");

        toolbarHeaderView.bindTo(evento.getNome(), evento.getLocal());
        floatHeaderView.bindTo(evento.getNome(), evento.getLocal());

        appBarLayout.addOnOffsetChangedListener(this);

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
        adapter = new ParticipanteAdapter(this, new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                presenter.itemClicked(view, position);
            }
        });
    }

    private void setupPresenter() {
        presenter = new ParticipanteListPresenter();
        presenter.attachView(this);
        presenter.subscribe();
        presenter.update(paginador, evento.getId());
    }

    private void setupRecyclerView() {
        recyclerView.hasFixedSize();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider1));
        recyclerView.addItemDecoration(dividerItemDecoration);

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                paginador.setPagina(page);
                presenter.update(paginador, evento.getId());
            }
        };
        recyclerView.addOnScrollListener(scrollListener);

        Log.d(TAG, "--> set layout manager");
    }

    @Override
    public void showList() {
        recyclerView.setAdapter(adapter);
        Log.d(TAG, "--> show image list");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showAddedList(Paginador paginador, List<Participante> participantes) {
        this.paginador = paginador;
        adapter.addParticipantes(participantes);

        txtRegistros.setText(adapter.getParticipantes().size() + "/" + paginador.getTotalRegistros());

        Log.d(TAG, "--> show added list");
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
                        paginador.setPagina(1);
                        presenter.update(paginador, evento.getId());
                    }
                });
        snackbar.getView().setBackgroundResource(R.color.error);
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    @Override
    public void navigateToDetailScreen(View view, int position) {
        Intent intent = new Intent(this, ParticipanteActivity.class);
        intent.putExtra(Constants.FLAG, adapter.getParticipantes().get(position));
        startActivity(intent);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (percentage == 1f && isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.VISIBLE);
            isHideToolbarView = !isHideToolbarView;
        } else if (percentage < 1f && !isHideToolbarView) {
            toolbarHeaderView.setVisibility(View.GONE);
            isHideToolbarView = !isHideToolbarView;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
