package edu.icet.formcontroller;

import edu.icet.dbconnection.DBConnection;
import edu.icet.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class HistoryFormController implements Initializable {

    @FXML
    private TableColumn<?, ?> colldonedate;

    @FXML
    private TableColumn<?, ?> colltaskid;

    @FXML
    private TableColumn<?, ?> colltasktitle;

    @FXML
    private TableView<Task> donetasktable;

    Connection connection= DBConnection.getInstance().getConnection();

    public void load(){
        colltaskid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colltasktitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colldonedate.setCellValueFactory(new PropertyValueFactory<>("localDate"));
        ObservableList<Task> donetasklist= FXCollections.observableArrayList();
        try {
            String SQL="SELECT * FROM donetable";
            PreparedStatement pstm = connection.prepareStatement(SQL);
            ResultSet resultSet = pstm.executeQuery();
            while(resultSet.next()){
                donetasklist.add(new Task(
                resultSet.getInt("taskid"),
                resultSet.getString("tasktitle"),
                LocalDate.parse(resultSet.getString("completiondate"))
                ));
            }
            donetasktable.setItems(donetasklist);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        load();
    }
}
