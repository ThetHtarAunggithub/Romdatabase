package com.android.roomexample.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.android.roomexample.Entity.Student;

import java.util.List;

@Dao
public interface StudentDAO {
    @Query("SELECT * FROM student")
    List<Student> getAll();

    @Query("SELECT * FROM student where id = :id")
    Student findByID(String id);

    @Query("SELECT COUNT(*) from student")
    int countStudents();

    @Insert
    void insert(Student student);

    @Insert
    void insertAll(Student... students);

    @Delete
    void delete(Student student);
}
