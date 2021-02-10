package ru.eds2809.dnevnik;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.gson.Gson;

import ru.eds2809.dnevnik.activity.TimetableActivity;
import ru.eds2809.dnevnik.models.User;
import ru.eds2809.dnevnik.repository.UserRepository;
import ru.eds2809.dnevnik.utils.Utils;

import static ru.eds2809.dnevnik.utils.Utils.containSharedPreference;
import static ru.eds2809.dnevnik.utils.Utils.hideKeyboard;
import static ru.eds2809.dnevnik.utils.Utils.saveToSP;
import static ru.eds2809.dnevnik.utils.Utils.showToast;


public class MainActivity extends AppCompatActivity {
    private AppCompatButton buttonLogin;
    private final Gson mGson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (containSharedPreference(this, "user")) {
            User user = mGson.fromJson(Utils.getFromSharedPreference(this, "user"), User.class);
            startTimetableActivity(user);
        }
        setContentView(R.layout.activity_main);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EditText loginEditText = findViewById(R.id.login);
        EditText passwordEditText = findViewById(R.id.password);

        buttonLogin.setOnClickListener(v -> {
            hideKeyboard(this);
            v.setEnabled(false);
            String login = loginEditText.getEditableText().toString();
            String password = passwordEditText.getEditableText().toString();
            auth(login, password);
        });
    }

    public void auth(String login, String password) {
        UserRepository.getInstance()
                .auth(login, password, this::onResponse);
    }

    public void onResponse(User user) {
        if (user != null) {
            showToast(this, "Успех");
            saveToSP(this, "user", mGson.toJson(user));
            startTimetableActivity(user);
        } else {
            showToast(this, "Не удача");
        }
        buttonLogin.setEnabled(true);
    }

    private void startTimetableActivity(User user) {
        Intent intent = new Intent(this, TimetableActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}