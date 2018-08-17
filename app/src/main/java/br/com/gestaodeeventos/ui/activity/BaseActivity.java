package br.com.gestaodeeventos.ui.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import br.com.gestaodeeventos.R;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    private SearchView searchView;

    protected abstract int getLayout();

    protected abstract int getMenu();

    protected abstract void adapterFilter(String query);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            getMenuInflater().inflate(getMenu(), menu);
        } catch (Exception e) {
            return false;
        }

        try {
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(searchManager != null ? searchManager.getSearchableInfo(getComponentName()) : null);
            searchView.setMaxWidth(Integer.MAX_VALUE);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    adapterFilter(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String query) {
                    adapterFilter(query);
                    return false;
                }
            });
        } catch (Exception e) {
            // não existe opção de Pesquisa
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_search:
                return true;
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onBackPressed() {
        if (searchView != null && !searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    protected void setTitulo(String titulo) {
        getSupportActionBar().setTitle(titulo);
    }

    protected void setSubTitulo(String titulo) {
        getSupportActionBar().setSubtitle(titulo);
    }

    protected void setIndicator() {
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    protected void setIndicatorX() {
        setIndicator();
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
    }

}