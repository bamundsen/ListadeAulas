package com.example.listadeaulas.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.listadeaulas.R;
import com.example.listadeaulas.adapter.ListAdapter;
import com.example.listadeaulas.data.Class;
import com.example.listadeaulas.data.ClassDAO;
import com.example.listadeaulas.dialog.DeleteDialog;
import com.example.listadeaulas.dialog.LinkDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener, ActionMode.Callback,
        AdapterView.OnItemLongClickListener, DeleteDialog.OnDeleteListener,
        LinkDialog.OnConfirmListener, View.OnClickListener {

    private ListView list;
    private ListAdapter adapter;
    private ClassDAO classDAO;
    private int selected;

    private static final int REQ_EDIT = 100;
    private FloatingActionButton fabAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = findViewById(R.id.lstMain);
        fabAdd = findViewById(R.id.fabAdd);

        adapter = new ListAdapter(this);

        list.setAdapter(adapter);

        classDAO = ClassDAO.getInstance(this);

        list.setOnItemClickListener(this);
        list.setOnItemLongClickListener(this);
        fabAdd.setOnClickListener(this);

        updateItemsList();
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.actAbout){
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivityForResult(intent, REQ_EDIT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        startActionMode(this);
        selected = position;
        view.setBackgroundColor(Color.GRAY);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LinkDialog dialog = new LinkDialog();
        dialog.setAttributes(adapter.getItem(position).getTitle(), adapter.getItem(position).getUrl());
        dialog.show(getSupportFragmentManager(), "linkDialog");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_EDIT && resultCode == RESULT_OK){
            updateItemsList();
        }
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        getMenuInflater().inflate(R.menu.action_mode, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        Class aClass = adapter.getItem(selected);

        if (item.getItemId() == R.id.actEdit){
            Intent intent = new Intent(getApplicationContext(), EditClassActivity.class);
            intent.putExtra("class", aClass);
            startActivityForResult(intent, REQ_EDIT);
            mode.finish();
            return true;
        }
        else if (item.getItemId() == R.id.actDelete){
            DeleteDialog dialog = new DeleteDialog();
            dialog.setaClass(aClass);
            dialog.show(getSupportFragmentManager(), "deleteDialog");
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        list.getChildAt(selected).setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public void onDelete(Class aClass) {
        classDAO.delete(aClass);
        updateItemsList();
    }

    @Override
    public void onConfirm(String title, String link) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fabAdd){
            Intent intent = new Intent(getApplicationContext(), EditClassActivity.class);
            startActivityForResult(intent, REQ_EDIT);
        }
    }

    public void updateItemsList(){
        if (!classDAO.list().isEmpty()){
            List<Class> classes = classDAO.list();
            adapter.setItemsList(classes);
        }
    }

}