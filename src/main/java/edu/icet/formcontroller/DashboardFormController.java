package edu.icet.formcontroller;

import com.jfoenix.controls.*;
import com.sun.javafx.scene.control.behavior.ComboBoxListViewBehavior;
import edu.icet.controller.TaskController;
import edu.icet.controller.TaskService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardFormController {

    @FXML
    private JFXListView<Object> donelist;

    @FXML
    private JFXListView<Object> todolist;
    @FXML
    private JFXTextField txttask;
    TaskService service=new TaskController();
    ObservableList<Object> item= FXCollections.observableArrayList();
    @FXML
    void btnAddTaskOnAction(ActionEvent event) {
        int id= service.getID();
        JFXCheckBox checkBox=new JFXCheckBox(txttask.getText());
        checkBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                service.remove(id,checkBox,todolist,donelist);
            }
        });
        service.addTask(id,checkBox.getText());
        item.add(checkBox);
        todolist.setItems(item);
    }
    @FXML
    void btnShowHistoryOnAction(ActionEvent event) {
        Stage stage=new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../../../view/history_form.fxml"))));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
