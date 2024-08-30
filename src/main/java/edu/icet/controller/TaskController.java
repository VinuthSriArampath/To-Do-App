package edu.icet.controller;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import edu.icet.dbconnection.DBConnection;
import edu.icet.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class TaskController implements TaskService{


    @Override
    public int getID() {
        try {
            int count=0;
            PreparedStatement pstm = connection.prepareStatement("SELECT COUNT(*) FROM todotable;");
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                count=resultSet.getInt("COUNT(*)");
            }
            return count+1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    Connection connection=DBConnection.getInstance().getConnection();


    @Override
    public void addTask(int id,String tsktitle) {
        String SQL="INSERT INTO todotable VALUES(?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,id);
            pstm.setObject(2,tsktitle);
            pstm.setObject(3,LocalDate.now());
            boolean b = pstm.executeUpdate() > 0;

            if (b){
                new Alert(Alert.AlertType.INFORMATION,"Task Added!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    ObservableList<Object> list= FXCollections.observableArrayList();
    @Override
    public void remove(int id,JFXCheckBox checkBox, JFXListView todolist, JFXListView donelist) {
        todolist.getItems().remove(checkBox);
        int idrm = -1;
        String titlerm="";
        LocalDate donedate=null;
        ObservableList<Task> donetasklist=FXCollections.observableArrayList();
        try {
            String SQL="SELECT * FROM todotable WHERE taskid=?;";
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,id);
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                donetasklist.add(new Task(
                        idrm=resultSet.getInt("taskid"),
                        titlerm=resultSet.getString("tasktitle"),
                        donedate=LocalDate.now()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String SQL="DELETE FROM todotable WHERE taskid=?";
        try {
            PreparedStatement pstm = connection.prepareStatement(SQL);
            pstm.setObject(1,id);
            boolean b = pstm.executeUpdate() > 0;

            if (b){
                new Alert(Alert.AlertType.INFORMATION,"Task is Done!").show();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String SQLinsert="INSERT INTO donetable VALUES(?,?,?)";
        try {
            PreparedStatement pstm = connection.prepareStatement(SQLinsert);
            pstm.setObject(1,idrm);
            pstm.setObject(2,titlerm);
            pstm.setObject(3,donedate);
            boolean b = pstm.executeUpdate() > 0;

            if (b){
                System.out.println("task moved");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        list.add(checkBox);
        checkBox.setDisable(true);
        donelist.setItems(list);

    }

}
