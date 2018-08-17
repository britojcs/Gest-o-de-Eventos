package br.com.gestaodeeventos.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import br.com.gestaodeeventos.R;
import butterknife.BindView;

public abstract class BaseToolbarActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

}