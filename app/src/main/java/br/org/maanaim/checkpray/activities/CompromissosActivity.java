package br.org.maanaim.checkpray.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import br.org.maanaim.checkpray.R;
import br.org.maanaim.checkpray.SharedPreference.MySaveSharedPreference;
import br.org.maanaim.checkpray.util.DataUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import br.org.maanaim.checkpray.fragments.CompromissosDialogFragment;

public class CompromissosActivity extends AppCompatActivity {

    public static final String TAG_COMPROMISSOS = "tagCompromissos";

    private SimpleDateFormat dateFormat;

    @BindView(R.id.calendar)
    MaterialCalendarView mCalendar;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.txt_subTitleToolBar)
    TextView mSubTitle;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.ripple_effect)
    RippleView mRippleViewReport;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compromissos);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        fab.setOnClickListener(new FloatButton(getSupportFragmentManager()));

        initCalendar();

        mRippleViewReport.setOnClickListener(new BotaoRelatorio());


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_logout:
                logout();
                return true;
            case R.id.menu_hoje:
                setToday();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setToday() {
        mCalendar.clearSelection();

        mCalendar.setDateSelected(CalendarDay.today(), true);
        mCalendar.setCurrentDate(CalendarDay.today());
    }

    private void logout() {
        MySaveSharedPreference.clearSharedPreference(this);
        Intent it = new Intent(CompromissosActivity.this, LoginActivity.class);
        startActivity(it);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }


    private void initCalendar() {

        mCalendar.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .commit();
        ;
        mCalendar.setTopbarVisible(false);
        mCalendar.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mCalendar.getCurrentDate().getDate().getTime());

        int mes = calendar.get(Calendar.MONTH);
        int ano = calendar.get(Calendar.YEAR);

        mTitle.setText(DataUtil.getSiglaMes(mes));
        mSubTitle.setText(String.valueOf(ano));

        mCalendar.setCurrentDate(new Date());
        mCalendar.setSelectedDate(new Date());

        mCalendar.setOnMonthChangedListener(new MonthChangedListener());

    }

    class MonthChangedListener implements OnMonthChangedListener {
        @Override
        public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
            mTitle.setText(DataUtil.getSiglaMes(date.getMonth()));
            mSubTitle.setText(String.valueOf(date.getYear()));
        }
    }

    class FloatButton implements View.OnClickListener {


        FragmentManager fragmentManager;

        public FloatButton(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
        }

        @Override
        public void onClick(View view) {
            CalendarDay day = mCalendar.getSelectedDate();
            CompromissosDialogFragment dialogFragment = CompromissosDialogFragment.novaInstancia(day);
            dialogFragment.show(fragmentManager, "dialog_compromissos");
        }
    }

    class BotaoRelatorio implements View.OnClickListener {
        @Override
        public void onClick(View view) {

        }
    }

}
