package ru.eds2809.dnevnik.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.eds2809.dnevnik.R;
import ru.eds2809.dnevnik.adapters.SubjectTimeTableAdapter;
import ru.eds2809.dnevnik.models.Appraisal;
import ru.eds2809.dnevnik.models.Subject;
import ru.eds2809.dnevnik.models.User;
import ru.eds2809.dnevnik.repository.AppraisalRepository;

public class SubjectTimeTableActivity extends AppCompatActivity {

    private User mUser;
    private Subject mSubject;
    private boolean editable;
    private RecyclerView recyclerView;
    private SubjectTimeTableAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_time_table_a_ctivity);
        mUser = (User) getIntent().getSerializableExtra("user");
        mSubject = (Subject) getIntent().getSerializableExtra("subject");
        editable = getIntent().getBooleanExtra("editable", false);

        recyclerView = findViewById(R.id.subject_list_recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);

        adapter = new SubjectTimeTableAdapter(scoreClickListener);

        recyclerView.setAdapter(adapter);

        getAppraisal(mUser.getId(), mSubject.getId());
    }

    private void getAppraisal(long userId, long subjectId) {
        AppraisalRepository.getInstance()
                .getAppraisalByUserIdAndSubjectId(userId, subjectId, this::setAdapter);
    }

    private void setAdapter(List<Appraisal> appraisals) {
        adapter.setAppraisalList(appraisals);
    }
}