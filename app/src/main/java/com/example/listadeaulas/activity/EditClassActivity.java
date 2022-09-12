package com.example.listadeaulas.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.listadeaulas.R;
import com.example.listadeaulas.data.Class;
import com.example.listadeaulas.data.ClassDAO;

public class EditClassActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtTitle;
    private EditText edtMatter;
    private EditText edtLink;
    private Button btnCancel;
    private Button btnSave;
    private Class aClass;
    private ClassDAO classDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);

        edtTitle = findViewById(R.id.edtTitle);
        edtMatter = findViewById(R.id.edtMatter);
        edtLink = findViewById(R.id.edtLink);
        btnCancel = findViewById(R.id.btnCancel);
        btnSave = findViewById(R.id.btnSave);

        classDAO =  ClassDAO.getInstance(this);
        aClass = (Class) getIntent().getSerializableExtra("class");

        if (aClass != null){
            edtTitle.setText(aClass.getTitle());
            edtMatter.setText(aClass.getMatter());
            edtLink.setText(aClass.getUrl());
        }

        btnCancel.setOnClickListener(this);
        btnSave.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.btnCancel){
            setResult(RESULT_CANCELED);
            finish();
        }
        else if (v.getId() == R.id.btnSave){
            String title = edtTitle.getText().toString(),
                    matter = edtMatter.getText().toString(),
                    link = edtLink.getText().toString(),
                    result;

            if (title.isEmpty()){
                findViewById(R.id.edtTitle).setBackgroundColor(Color.RED);
                Toast.makeText(this, "O Campo de título da aula deve ser preenchido", Toast.LENGTH_SHORT).show();
            }
            if (link.isEmpty()){
                findViewById(R.id.edtLink).setBackgroundColor(Color.RED);
                Toast.makeText(this, "O Campo de link da aula deve ser preenchido", Toast.LENGTH_SHORT).show();
            }
            else {

                if (aClass == null) {
                    Class aClass = new Class(title, matter, link);
                    classDAO.save(aClass);
                    result = getResources().getString(R.string.saveSuccess);
                } else {
                    aClass.setTitle(title);
                    aClass.setMatter(matter);
                    aClass.setUrl(link);
                    classDAO.update(aClass);
                    result = getResources().getString(R.string.updateSuccess);
                }

                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        }
        else if (v.getId() == R.id.btnCancel){
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}