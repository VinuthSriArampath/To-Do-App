package edu.icet.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;

public interface TaskService {
    void addTask(int id,String tsktitle);
    int getID();
    void remove(int id,JFXCheckBox checkBox, JFXListView todolist, JFXListView donelist);
}
