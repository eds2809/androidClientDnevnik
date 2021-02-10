package ru.eds2809.dnevnik.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import ru.eds2809.dnevnik.R;
import ru.eds2809.dnevnik.adapters.SubjectListRVAdapter;
import ru.eds2809.dnevnik.models.Subject;
import ru.eds2809.dnevnik.models.User;
import ru.eds2809.dnevnik.models.UserRole;
import ru.eds2809.dnevnik.repository.UserRepository;

import static ru.eds2809.dnevnik.utils.Utils.saveToSP;
import static ru.eds2809.dnevnik.utils.Utils.toJson;

public class TimetableActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private User mUser;
    private User currentUser;
    private RecyclerView recyclerView;
    private SubjectListRVAdapter subjectListRVAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    protected Spinner headSpinner;
    private ArrayAdapter<User> adapter;
    private int spinerSelectedPos = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        currentUser = mUser = (User) getIntent().getSerializableExtra("user");
        recyclerView = findViewById(R.id.subject_list_recyclerView);
        swipeRefreshLayout = findViewById(R.id.subject_list_SwipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        subjectListRVAdapter = new SubjectListRVAdapter(this::selectedSubjectAction);
        headSpinner = findViewById(R.id.head_spinner);
        headSpinner.setPrompt("Выбирете ученика");

        if (mUser.getRole() == UserRole.PUPIL_ROLE) {
            headSpinner.setVisibility(View.GONE);
        }

        headSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Получаем выбранный объект
                if (mUser.getRole() != UserRole.PUPIL_ROLE) {
                    currentUser = (User) parent.getItemAtPosition(position);
                    //showToast(parent.getContext(), currentUser.getName());
                    setDataSubjectListRV(currentUser.getSubjects());
                    spinerSelectedPos = position;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        subjectListRVAdapter.setSubjectList(mUser.getSubjects());
        recyclerView.setAdapter(subjectListRVAdapter);

        getAllUserToSpinner();
    }

    private void getAllUserToSpinner() {
        if (mUser.getRole() != UserRole.PUPIL_ROLE) {
            UserRepository.getInstance()
                    .getAllUsers(this::setAdapterToSpinner);
        }
    }

    private void setAdapterToSpinner(List<User> users) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        headSpinner.setAdapter(adapter);
        if (spinerSelectedPos != -1){
            headSpinner.setSelection(spinerSelectedPos);
        }
    }

    private void setDataSubjectListRV(List<Subject> subjectList) {
        swipeRefreshLayout.setRefreshing(false);
        subjectListRVAdapter.setSubjectList(subjectList);
    }


    private void call(User user) {
        setDataSubjectListRV(user.getSubjects());
        if (mUser.getId() == user.getId()) {
            saveToSP(this, "user", toJson(user));
        }
    }

    @Override
    public void onRefresh() {
        getUser();
        getAllUserToSpinner();
    }

    private void getUser() {
        UserRepository.getInstance()
                .getUserById(currentUser.getId(), this::call);
    }

    public void selectedSubjectAction(Subject subject) {
        Intent intent = new Intent(this, SubjectTimeTableActivity.class);
        intent.putExtra("subject", subject);
        intent.putExtra("user", currentUser);
        intent.putExtra("editable", mUser.getRole() != UserRole.PUPIL_ROLE);
        startActivity(intent);
    }
}