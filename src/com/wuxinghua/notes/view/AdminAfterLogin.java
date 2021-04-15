package com.wuxinghua.notes.view;

import com.wuxinghua.notes.bean.NoteBean;
import com.wuxinghua.notes.controller.NoteController;
import com.wuxinghua.notes.controller.UserController;
import com.wuxinghua.notes.dao.AdminDao;
import com.wuxinghua.notes.dao.ReadDao;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminAfterLogin extends Application{

    private UserController userController;
    private NoteController noteController;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Stage stage = new Stage();
        Button buttonUserInformation = new Button("查看用户信息");
        Button buttonNoteInformation = new Button("查看笔记信息");
        HBox hbox = new HBox();

        hbox.getChildren().addAll(buttonUserInformation,buttonNoteInformation);
        hbox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(hbox);
        stage.setScene(scene);
        stage.setTitle("管理员界面");

        stage.show();

        // 查看用户信息按钮点击事件
        buttonUserInformation.setOnAction(event -> {
            ReadDao readDao = new ReadDao();
            readDao.userInfomation();
            ObservableList<User> observableList = readDao.userInfomation();
            Label label = new Label("用户信息");
            label.setFont(new Font("微软雅黑", 20)); // 设置标签的字体
            Button button = new Button("返回");

            TableView table = new TableView();

            table.setEditable(true);

            TableColumn userNameCol = new TableColumn("用户名");
            TableColumn userIdCol = new TableColumn("用户ID");
            TableColumn userNoteNumberCol = new TableColumn("用户笔记条数");

            userNameCol.setMinWidth(100);
            userNameCol.setCellValueFactory(
                    new PropertyValueFactory<>("userName"));

            userIdCol.setMinWidth(100);
            userIdCol.setCellValueFactory(
                    new PropertyValueFactory<>("userId"));

            userNoteNumberCol.setMinWidth(200);
            userNoteNumberCol.setCellValueFactory(
                    new PropertyValueFactory<>("noteNumber"));
            table.getColumns().addAll(userNameCol, userIdCol, userNoteNumberCol);

            table.setItems(observableList);
            table.setEditable(true);
            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPadding(new Insets(10, 0, 0, 10));
            vBox.getChildren().addAll(label, table,button);

            stage.setScene(new Scene(vBox, 200, 250));
            stage.show();


            // 返回按钮点击事件
            button.setOnAction(e->{
                stage.setScene(scene);
                stage.setWidth(200);
                stage.setHeight(100);
                stage.setY(250);
                stage.setX(600);

            });
        });

        // 查看笔记按钮点击事件
        buttonNoteInformation.setOnAction(e->{


            noteController = new NoteController();
            ReadDao readDao = new ReadDao();
            Note note = new Note();
            NoteBean noteBean = noteController.lookNote(new User());

            ObservableList<Note> observableList = noteBean.getObservableList();

            System.out.println(observableList);

            Label labelTitle = new Label("用户笔记");
            labelTitle.setFont(new Font("微软雅黑", 20)); // 设置标签的字体
            Button button = new Button("返回");

            TableView table = new TableView();
            table.setEditable(true);

            TableColumn titleCol = new TableColumn("标题");
            TableColumn noteIdCol = new TableColumn("笔记id");
            TableColumn userNameCol = new TableColumn("昵称");
            TableColumn likeCol = new TableColumn("点赞数");
            TableColumn createTimeCol = new TableColumn("创作时间");
            TableColumn updateTimeCol = new TableColumn("更新时间");
            TableColumn visibilityCol = new TableColumn("可见性");

            titleCol.setMinWidth(100);
            titleCol.setCellValueFactory(
                    new PropertyValueFactory<>("title"));
            noteIdCol.setCellValueFactory(new PropertyValueFactory<>("noteId"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            likeCol.setCellValueFactory(new PropertyValueFactory<>("like"));
            createTimeCol.setCellValueFactory(new PropertyValueFactory<>("createTime"));
            updateTimeCol.setCellValueFactory(new PropertyValueFactory<>("updateTime"));
            visibilityCol.setCellValueFactory(new PropertyValueFactory<>("visibility"));


            table.getColumns().addAll(
                    titleCol,noteIdCol,userNameCol,likeCol,createTimeCol,updateTimeCol,visibilityCol);

            table.setItems(observableList);

            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPadding(new Insets(10, 0, 0, 10));
            vBox.getChildren().addAll(labelTitle, table, button);

            stage.setScene(new Scene(vBox, 200, 250));
            stage.show();

            // 返回按钮点击事件
            button.setOnAction(event -> {
                stage.setScene(scene);
            });

            // 鼠标左键双击
            table.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                    System.out.println(table.getSelectionModel().getSelectedItem());
                    String resultTitle = observableList.get(table.getSelectionModel().getSelectedIndex()).getTitle();
                    String resultUserName = observableList.get(table.getSelectionModel().getSelectedIndex()).getUserName();
                    int resultLike = observableList.get(table.getSelectionModel().getSelectedIndex()).getLike();
                    String resultCreateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getCreateTime();
                    String resultUpdateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getUpdateTime();

                    try {
                        noteController = new NoteController();

                        NoteBean noteBean1 =noteController.noteInsert(resultTitle,new User());
                        String text = noteBean1.getText();
                        System.out.println(noteBean1.getResult());
                        User user;
                        Note userNote = new Note();
                        NoteRead noteRead = new NoteRead();
                        AdminDao adminDao = new AdminDao();

                        user= adminDao.AdminGetUser(resultUserName,userNote);
                        userNote.setNoteText(text);
                        userNote.setTitle(resultTitle);
                        userNote.setUserName(resultUserName);
                        userNote.setCreateTime(resultCreateTime);
                        userNote.setUpdateTime(resultUpdateTime);
                        userNote.setLike(resultLike);
                        noteRead.start(stage,user,userNote);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });

        });



    }
}
