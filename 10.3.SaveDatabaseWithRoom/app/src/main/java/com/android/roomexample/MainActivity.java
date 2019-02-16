package com.android.roomexample;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.android.roomexample.DB.AppDatabase;
import com.android.roomexample.Entity.Student;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText etName,etTeamNo,etUniversity,etAttendYear;

    TextView tvData;

    List<Student> listStudent;

    private CoordinatorLayout coordinatorLayout;

    private boolean updatestate = false;
    private int updateid = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.Name);
        etTeamNo = (EditText) findViewById(R.id.TeamNo);
        etUniversity = (EditText) findViewById(R.id.University);
        etAttendYear = (EditText) findViewById(R.id.AttendYear);

        tvData = (TextView) findViewById(R.id.Data);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!updatestate) {
                    Log.i("PROCESS : ", "SAVE");
                    Student student = new Student(etName.getText().toString(), etTeamNo.getText().toString(), etUniversity.getText().toString(), etAttendYear.getText().toString());
                    AppDatabase.getAppDatabase(MainActivity.this).studentDAO().insert(student);
                }else{

                }

                updatestate = false;
                updateid = 0;
                ClearData();

                LoadAllStudent();

                Log.i("Total Student : ", ""+AppDatabase.getAppDatabase(MainActivity.this).studentDAO().countStudents());
                Snackbar.make(view, "Saved to : Database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {

            listStudent = AppDatabase.getAppDatabase(MainActivity.this).studentDAO().getAll();

            final String[] arrStudent = LoadStudentName();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Pick Student to Delete")
                    .setItems(arrStudent, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            String id = arrStudent[which].split("-")[0].trim(); // get id (eg. 1 - ko ko) -> 1
                            Student student = new Student();
                            student.setId(Integer.valueOf(id));
                            AppDatabase.getAppDatabase(MainActivity.this).studentDAO().delete(student);

                            LoadAllStudent();

                            Snackbar.make(coordinatorLayout, "Delete Successful. Total Student is " + AppDatabase.getAppDatabase(MainActivity.this).studentDAO().countStudents(), Snackbar.LENGTH_LONG)
                                    .show();
                        }
                    });
            builder.show();

        }else if (id == R.id.action_update) {

            final String[] arrStudent = LoadStudentName();

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Pick Student to Update")
                    .setItems(arrStudent, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            updatestate = true;
                            updateid = Integer.parseInt(arrStudent[which].split("-")[0].trim()); // get id (eg. 1 - ko ko) -> 1

                            Student student = AppDatabase.getAppDatabase(MainActivity.this).studentDAO().findByID(""+updateid);
                            etName.setText(student.getName());
                            etTeamNo.setText(student.getTeamno());
                            etUniversity.setText(student.getUniversity());
                            etAttendYear.setText(student.getAttendyear());
                        }
                    });
            builder.show();

        }else if (id == R.id.action_get) {

            LoadAllStudent();
        }

        return super.onOptionsItemSelected(item);
    }

    private String[] LoadStudentName(){
        listStudent = AppDatabase.getAppDatabase(MainActivity.this).studentDAO().getAll();
        Log.i("LoadStudentName ", listStudent.size()+"");
        String[] arrStudent = new String[listStudent.size()];
        int index = 0;
        for (Student student : listStudent) {
            arrStudent[index] = student.getId() + " - " + student.getName();
            index++;
        }

        return arrStudent;
    }

    private void LoadAllStudent(){

        String strAllStdent = "";

        List<Student> studentList = AppDatabase.getAppDatabase(MainActivity.this).studentDAO().getAll();

        for (Student student : studentList){

            String strStdent = student.getId() + ", ";
            strStdent += student.getName() + ", ";
            strStdent += student.getTeamno() + ", ";
            strStdent += student.getUniversity() + ", ";
            strStdent += student.getAttendyear();

            strAllStdent += strStdent + "\n\n";
        }

        tvData.setText(strAllStdent);
    }

    private void ClearData(){

        etName.setText("");
        etTeamNo.setText("");
        etUniversity.setText("");
        etAttendYear.setText("");
    }
}
