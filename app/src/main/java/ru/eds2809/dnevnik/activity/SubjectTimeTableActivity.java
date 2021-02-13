package ru.eds2809.dnevnik.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ru.eds2809.dnevnik.R;
import ru.eds2809.dnevnik.adapters.SubjectTimeTableAdapter;
import ru.eds2809.dnevnik.adapters.SwipeToDeleteCallback;
import ru.eds2809.dnevnik.models.Appraisal;
import ru.eds2809.dnevnik.models.Subject;
import ru.eds2809.dnevnik.models.User;
import ru.eds2809.dnevnik.repository.AppraisalRepository;

import static ru.eds2809.dnevnik.utils.Utils.showToast;

public class SubjectTimeTableActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private User mUser;
    private Subject mSubject;
    private boolean editable;
    private RecyclerView recyclerView;
    private SubjectTimeTableAdapter adapter;
    private TextView avgScoreView;
    private AppCompatButton addButton;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_time_table_a_ctivity);
        mUser = (User) getIntent().getSerializableExtra("user");
        mSubject = (Subject) getIntent().getSerializableExtra("subject");
        editable = getIntent().getBooleanExtra("editable", false);

        getAppraisal();

        recyclerView = findViewById(R.id.subject_list_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        adapter = new SubjectTimeTableAdapter(this::openEditAppraisalDialog);

        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter, this::deleteItemWipe, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);


        avgScoreView = findViewById(R.id.avgScore);

        addButton = findViewById(R.id.addScoreButton);

        addButton.setOnClickListener(v -> {
            openEditAppraisalDialog(null);
        });

        swipeRefreshLayout = findViewById(R.id.subject_list_recyclerView_swipe);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void deleteItemWipe(int position) {
        Appraisal appraisal = adapter.getAppraisalList().get(position);
        AppraisalRepository.getInstance()
                .delete(appraisal.getId(), s -> {
                    showToast(this, "s");
                    updateAvgScore();
                });
    }

    private void getAppraisal() {
        AppraisalRepository.getInstance()
                .getAppraisalByUserIdAndSubjectId(mUser.getId(), mSubject.getId(), this::updateView);
    }

    private void updateView(List<Appraisal> appraisals) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.setAppraisalList(appraisals);
        updateAvgScore();
    }

    private float getAvgScore(List<Appraisal> appraisals) {
        float avgScore = 0;
        if (appraisals != null && appraisals.size() != 0) {
            for (Appraisal a : appraisals) {
                avgScore += a.getScore();
            }
            avgScore /= appraisals.size();
        }

        return avgScore;
    }

    private void openEditAppraisalDialog(Appraisal appraisal) {
        if (editable) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            View view = getLayoutInflater().inflate(R.layout.edit_score_layout_dialog, null);
            final EditText valueKey = view.findViewById(R.id.edit_score);
            boolean notNull = appraisal != null;
            if (notNull) {
                valueKey.setText(String.valueOf(appraisal.getScore()));
            }
            builder.setTitle(notNull ? "Редактор оценки" : "Добавить оценку");
            builder.setView(view);
            builder.setCancelable(false);
            builder.setPositiveButton("сохранить",
                    (dialog, id) -> {
                        long score = Long.parseLong(valueKey.getText().toString());
                        if (notNull) {
                            appraisal.setScore(score);
                            SubjectTimeTableActivity.this.putAppraisal(appraisal);
                        } else {
                            SubjectTimeTableActivity.this.putAppraisal(new Appraisal(mSubject.getId(), mUser.getId(), score));
                        }
                        dialog.dismiss();
                    });
            builder.setNegativeButton("отмена", (d, i) -> {
                d.cancel();
            });

            builder.show();
        }
    }

    public void putAppraisal(Appraisal appraisal) {
        long score = appraisal.getScore();
        if (score > 1 && score <= 5) {
            if (appraisal.getId() != null) {
                adapter.addOrUpdate(appraisal);
            } else {
                AppraisalRepository.getInstance()
                        .put(appraisal, this::putAppraisalResponse);
            }
        } else {
            showToast(this, "Не верная оценка");
        }
    }

    private void putAppraisalResponse(Appraisal appraisal) {
        adapter.addOrUpdate(appraisal);
        updateAvgScore();
    }

    private void updateAvgScore() {
        float avgScore = getAvgScore(adapter.getAppraisalList());
        if (avgScore != 0) {
            avgScoreView.setText(String.format("%.2f", avgScore));
        } else {
            avgScoreView.setText("");
        }
    }

    @Override
    public void onRefresh() {
        getAppraisal();
    }
}