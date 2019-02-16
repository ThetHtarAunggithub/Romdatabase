package com.android.roomexample.Entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "student")
public class Student {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "teamno")
    private String teamno;

    @ColumnInfo(name = "university")
    private String university;

    @ColumnInfo(name = "attendyear")
    private String attendyear;

    public Student() {}

    public Student(String name,String teamno,String university,String attendyear)
    {
        this.name=name;
        this.teamno=teamno;
        this.university=university;
        this.attendyear=attendyear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamno() {
        return teamno;
    }

    public void setTeamno(String teamno) {
        this.teamno = teamno;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getAttendyear() {
        return attendyear;
    }

    public void setAttendyear(String attendyear) {
        this.attendyear = attendyear;
    }
}
