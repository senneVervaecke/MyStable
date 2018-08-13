package com.example.sennevervaecke.mystable;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by sennevervaecke on 7/25/2018.
 */

public class Horse extends SugarRecord<Horse> implements Serializable {
    private String name;
    private String father;
    private String mother;
    private String fatherMother;
    private int year;
    private String reg;
    private String fei;

    public Horse(){}

    public Horse(String name, String father, String mother, String fatherMother, int year, String reg, String fei) {
        this.name = name;
        this.father = father;
        this.mother = mother;
        this.fatherMother = fatherMother;
        this.year = year;
        this.reg = reg;
        this.fei = fei;
    }

    public Horse(String name, String father, String mother, String fatherMother, int year) {
        this.name = name;
        this.father = father;
        this.mother = mother;
        this.fatherMother = fatherMother;
        this.year = year;
        this.reg = null;
        this.fei = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public String getFatherMother() {
        return fatherMother;
    }

    public void setFatherMother(String fatherMother) {
        this.fatherMother = fatherMother;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getReg() {
        return reg;
    }

    public void setReg(String reg) {
        this.reg = reg;
    }

    public String getFei() {
        return fei;
    }

    public void setFei(String fei) {
        this.fei = fei;
    }
}
