package br.com.gestaodeeventos.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import br.com.gestaodeeventos.R;
import br.com.gestaodeeventos.api.model.Participante;
import br.com.gestaodeeventos.ui.presenter.ParticipantePresenter;
import br.com.gestaodeeventos.ui.view.ParticipanteView;
import br.com.gestaodeeventos.util.Constants;
import br.com.gestaodeeventos.util.DateUtil;
import br.com.gestaodeeventos.util.ImageUtil;
import butterknife.BindView;

public class ParticipanteActivity extends BaseActivity implements ParticipanteView {

    public static final String TAG = ParticipanteActivity.class.getSimpleName();

    @BindView(R.id.coordinator_layout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.txt_codigo)
    TextView txtCodigo;
    @BindView(R.id.txt_nome)
    TextView txtNome;
    @BindView(R.id.txt_email)
    TextView txtEmail;
    @BindView(R.id.txt_data_cadastro)
    TextView txtDataCadastro;
    @BindView(R.id.txt_checkin)
    TextView txtCheckin;
    @BindView(R.id.lyt_assinatura)
    LinearLayout lytAssinatura;
    @BindView(R.id.img_assinatura)
    ImageView imgAssinatura;

    private ParticipantePresenter presenter;
    private Participante participante;

    @Override
    protected int getLayout() {
        return R.layout.activity_participante;
    }

    @Override
    protected int getMenu() {
        return 0;
    }

    @Override
    protected void adapterFilter(String query) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIndicatorX();

        participante = (Participante) getIntent().getSerializableExtra(Constants.FLAG);

        setupPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @SuppressLint({"SetTextI18n", "SimpleDateFormat"})
    @Override
    public void showData(Participante p) {
        if (p == null)
            p = participante;

        txtCodigo.setText(getString(R.string.codigo) + ": " + p.getId());
        txtNome.setText(p.getNome());
        txtEmail.setText(p.getEmail() != null ? p.getEmail() : "-");

        txtDataCadastro.setText(getString(R.string.data_cadastro) + ": " + DateUtil.formatDateLong(p.getDataCadastro()));
        txtCheckin.setText(getString(R.string.checkin) + ": " + DateUtil.formatDateLong(p.getCheckin()));

        String assinatura = p.getAssinatura();
        if (assinatura != null && !assinatura.equals("")) {
            lytAssinatura.setVisibility(View.VISIBLE);

            Glide.with(this)
                    .applyDefaultRequestOptions(ImageUtil.imgOptions())
                    .load(ImageUtil.base64ToBitmap(p.getAssinatura()))
                    .into(imgAssinatura);
        }
    }

    @Override
    public void showError(int errorMessage) {
        Log.d(TAG, "--> Error");
        Snackbar snackbar = Snackbar.make(coordinatorLayout, errorMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.update(participante.getId());
                    }
                });
        snackbar.getView().setBackgroundResource(R.color.error);
        snackbar.setActionTextColor(getResources().getColor(android.R.color.white));
        snackbar.show();
    }

    private void setupPresenter() {
        presenter = new ParticipantePresenter();
        presenter.attachView(this);
        presenter.subscribe();
        presenter.update(participante.getId());
    }

}
