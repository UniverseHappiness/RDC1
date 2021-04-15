package com.wuxinghua.notes.view;

import com.wuxinghua.notes.bean.NoteBean;
import com.wuxinghua.notes.bean.UserBean;
import com.wuxinghua.notes.controller.NoteController;
import com.wuxinghua.notes.controller.UserController;
import com.wuxinghua.notes.dao.AdminDao;
import com.wuxinghua.notes.dao.ReadDao;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class AfterLogin extends Application {

    private UserController userController;
    private NoteController noteController;


    public void start(Stage primaryStage, User user) throws Exception {

        ObservableList<String> title = FXCollections.observableArrayList();

        Stage stage = new Stage();
        Label label = new Label("用户名：");
        Label startUserName = new Label(user.getUserName());
        startUserName.setTextFill(Color.web("red"));

        Button startNewNote = new Button("新建笔记");
        Button lookNote = new Button("查看笔记");
        Button lookTitle = new Button("我的笔记");
        Button queryTitle = new Button("标题查询");
        Button queryUser = new Button("昵称查询");
        Button userMessage = new Button("我的信息");
        Button startExit = new Button("退出登录");

        GridPane gridPane1 = new GridPane();
        gridPane1.setStyle("-fx-background-color: lightblue");
        gridPane1.setAlignment(Pos.CENTER);
        gridPane1.add(startExit,3,8);
        gridPane1.add(startUserName,2,1);
        gridPane1.add(label,1,1);
        gridPane1.add(startNewNote,3,2);
        gridPane1.add(lookNote,3,3);
        gridPane1.add(lookTitle,3,4);
        gridPane1.add(queryTitle,3,5);
        gridPane1.add(queryUser,3,6);
        gridPane1.add(userMessage,3,7);

        Scene scene = new Scene(gridPane1);

        stage.setTitle("本地笔记本系统");
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();

        // 退出登录点击事件：回到用户登录界面
        startExit.setOnAction(e -> {
            Label l_thanks = new Label("感谢您的使用！");
            GridPane gridPane2 = new GridPane();
            gridPane2.getChildren().add(l_thanks);
            Scene sceneBye = new Scene(gridPane2);

            Stage stageBye = new Stage();
            stageBye.setScene(sceneBye);
            stageBye.showAndWait();
            stage.close();
        });


        // 添加标题点击事件
        startNewNote.setOnAction(e->{

            Label LabelTitle = new Label("请输入笔记标题");
            TextField createTitle = new TextField();
            Button buttonTitle = new Button("确定");
            HBox hBox = new HBox();
            hBox.getChildren().addAll(LabelTitle,createTitle,buttonTitle);
            Scene titleScene = new Scene(hBox);
            Stage titleStage = new Stage();
            titleStage.setTitle("笔记标题");
            titleStage.setScene(titleScene);
            titleStage.show();

            // 确定添加标题笔记事件
            buttonTitle.setOnAction(event->{
                Note note;
                File file = new File(createTitle.getText()+".txt");

                noteController = new NoteController();
                NoteBean noteBean = noteController.createNewTitle(createTitle.getText(),user);
                note = noteBean.getNote();

                if (noteBean.getNote() == null) {
                    Stage emptyTitlestage = new Stage();
                    HBox hBox1 = new HBox();
                    Label emptyTitleLabel = new Label(noteBean.getResult());
                    hBox1.getChildren().add(emptyTitleLabel);
                    emptyTitlestage.setScene(new Scene(hBox1));
                    emptyTitlestage.showAndWait();
                }else {
                    title.add(createTitle.getText());
                    titleStage.close();
                    user.setNoteNumber(user.getNoteNumber()+1);
                }
                try {
                    System.out.println(file.createNewFile());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }


                NoteWrite noteWrite = new NoteWrite();
                try {
                    noteWrite.start(stage,scene,user,note,createTitle.getText());

                } catch (Exception ex) {
                    System.out.println("笔记标题为空！异常");
                }



            });

        });

        // 查看笔记点击事件
        lookNote.setOnAction(e->{

            noteController = new NoteController();
            NoteBean noteBean =noteController.lookNote(user);

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
            titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            noteIdCol.setCellValueFactory(new PropertyValueFactory<>("noteId"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            likeCol.setCellValueFactory(new PropertyValueFactory<>("like"));
            createTimeCol.setCellValueFactory(new PropertyValueFactory<>("createTime"));
            updateTimeCol.setCellValueFactory(new PropertyValueFactory<>("updateTime"));
            visibilityCol.setCellValueFactory(new PropertyValueFactory<>("visibility"));


            table.getColumns().addAll(titleCol);

            table.setItems(observableList);

            VBox vBox = new VBox();
            vBox.setSpacing(5);
            vBox.setPadding(new Insets(10, 0, 0, 10));
            vBox.getChildren().addAll(labelTitle, table, button);

            stage.setScene(new Scene(vBox, 200, 250));
            stage.show();

            button.setOnAction(event -> {
                stage.setScene(scene);
            });

            table.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                    System.out.println(table.getSelectionModel().getSelectedItem());

                    String resultTitle = observableList.get(table.getSelectionModel().getSelectedIndex()).getTitle();
                    String resultUserName = observableList.get(table.getSelectionModel().getSelectedIndex()).getUserName();
                    int resultLike = observableList.get(table.getSelectionModel().getSelectedIndex()).getLike();
                    String resultCreateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getCreateTime();
                    String resultUpdateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getUpdateTime();

                    try {
//                        ReadDao readDao = new ReadDao();
                        NoteBean noteBean1;
                        noteBean1=noteController.noteInsert(resultTitle, user);
                        String text = noteBean1.getText();
                        System.out.println(text+"是否成功");

                        User userSearch;
                        Note userNote = new Note();
                        NoteRead noteRead = new NoteRead();
                        AdminDao adminDao = new AdminDao();
                        userSearch= adminDao.AdminGetUser(resultUserName,userNote);
                        userNote.setNoteText(text);
                        userNote.setTitle(resultTitle);
                        userNote.setUserName(resultUserName);
                        userNote.setCreateTime(resultCreateTime);
                        userNote.setUpdateTime(resultUpdateTime);
                        userNote.setLike(resultLike);
                        noteRead.start(stage,userSearch,userNote);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });


        });

        // 我的笔记点击事件
        lookTitle.setOnAction(e->{

            ReadDao readDao = new ReadDao();
            ObservableList<Note> observableList = readDao.noteInformation(user);

            Label labelTitle = new Label("我的笔记");
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
                    new PropertyValueFactory<>("title")); // 对象中的名称绑定

            noteIdCol.setCellValueFactory(new PropertyValueFactory<>("noteId"));
            userNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
            likeCol.setCellValueFactory(new PropertyValueFactory<>("like"));
            createTimeCol.setCellValueFactory(new PropertyValueFactory<>("createTime"));
            updateTimeCol.setCellValueFactory(new PropertyValueFactory<>("updateTime"));
            visibilityCol.setCellValueFactory(new PropertyValueFactory<>("visibility"));


            table.getColumns().addAll(titleCol,noteIdCol,userNameCol,likeCol,createTimeCol,updateTimeCol,visibilityCol); //将列添加到表格中

            table.setItems(observableList); //将集合绑定 表格


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

            // 鼠标左键点击次数为2的事件
            table.setOnMouseClicked((MouseEvent event) -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2){
                    System.out.println(table.getSelectionModel().getSelectedItem());

                    String resultTitle = observableList.get(table.getSelectionModel().getSelectedIndex()).getTitle();
                    try {
                        NoteBean noteBean1;
                        noteController = new NoteController();
                        noteBean1 = noteController.noteInsert(resultTitle,user);
                        String text = noteBean1.getText();
                        System.out.println(noteBean1.getResult());
                        Note note = new Note();
                        NoteWrite noteWrite = new NoteWrite();
                        note.setNoteText(text);
                        noteWrite.start(stage
,scene,user,note,resultTitle);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

            });

        });

        // 标题查询点击事件
        queryTitle.setOnAction(e->{
            Label labelSearch = new Label("请输入标题或标题的部分");
            TextField textFieldSearch = new TextField();
            Button buttonSearch = new Button("搜索");

            HBox hBox = new HBox();
            hBox.getChildren().addAll(labelSearch,textFieldSearch,buttonSearch);

            Stage stageSearch = new Stage();
            stageSearch.setScene(new Scene(hBox));
            stageSearch.show();

            // 搜索按钮点击事件
            buttonSearch.setOnAction(event->{
                stageSearch.close();

                ReadDao readDao = new ReadDao();

                ObservableList<Note> observableList = readDao.queryTitle(textFieldSearch.getText());
                Label labelTitle = new Label("查询笔记");
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
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
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

                button.setOnAction(event2 -> {
                    stage.setScene(scene);
                });

                table.setOnMouseClicked((MouseEvent event2) -> {
                    if (event2.getButton().equals(MouseButton.PRIMARY) && event2.getClickCount() == 2){
                        System.out.println(table.getSelectionModel().getSelectedItem());

                        String resultTitle = observableList.get(table.getSelectionModel().getSelectedIndex()).getTitle();
                        String resultUserName = observableList.get(table.getSelectionModel().getSelectedIndex()).getUserName();
                        int resultLike = observableList.get(table.getSelectionModel().getSelectedIndex()).getLike();
                        String resultCreateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getCreateTime();
                        String resultUpdateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getUpdateTime();

                        try {
                            noteController = new NoteController();

                            NoteBean noteBean1 = noteController.noteInsert(resultTitle,user);
                            String text = noteBean1.getText();
                            System.out.println(noteBean1.getResult());
                            User userSearch = new User();
                            Note userNote = new Note();
                            NoteRead noteRead = new NoteRead();
                            AdminDao adminDao = new AdminDao();

                            userSearch= adminDao.AdminGetUser(resultUserName,userNote);
                            userNote.setNoteText(text);
                            userNote.setTitle(resultTitle);
                            userNote.setUserName(resultUserName);
                            userNote.setCreateTime(resultCreateTime);
                            userNote.setUpdateTime(resultUpdateTime);
                            userNote.setLike(resultLike);
                            noteRead.start(stage,userSearch,userNote);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                });

            });
        });


        //昵称查询点击事件
        queryUser.setOnAction(event -> {
            Label labelSearch = new Label("请输入昵称或昵称的部分");
            TextField textFieldUser = new TextField();
            Button buttonSearch = new Button("搜索");

            HBox hBox = new HBox();
            hBox.getChildren().addAll(labelSearch,textFieldUser,buttonSearch);

            Stage stageSearch = new Stage();
            stageSearch.setScene(new Scene(hBox));
            stageSearch.show();

            buttonSearch.setOnAction(event2->{

                stageSearch.close();
                ReadDao readDao = new ReadDao();

                ObservableList<Note> observableList = readDao.userQuery(textFieldUser.getText());
                Label labelTitle = new Label("查询用户笔记");
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
                titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
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

                button.setOnAction(event3 -> {
                    stage.setScene(scene);
                    stage.show();
                });

                table.setOnMouseClicked((MouseEvent event3) -> {
                    if (event3.getButton().equals(MouseButton.PRIMARY) && event3.getClickCount() == 2){
                        System.out.println(table.getSelectionModel().getSelectedItem());

                        String resultTitle = observableList.get(table.getSelectionModel().getSelectedIndex()).getTitle();
                        String resultUserName = observableList.get(table.getSelectionModel().getSelectedIndex()).getUserName();
                        int resultLike = observableList.get(table.getSelectionModel().getSelectedIndex()).getLike();
                        String resultCreateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getCreateTime();
                        String resultUpdateTime = observableList.get(table.getSelectionModel().getSelectedIndex()).getUpdateTime();

                        try {
                            noteController = new NoteController();
                            NoteBean noteBean1 = noteController.noteInsert(resultTitle,user);
                            String text = noteBean1.getText();
                            System.out.println(noteBean1.getText());
                            System.out.println(noteBean1.getResult());
                            User userSearch = new User();
                            Note userNote = new Note();
                            NoteRead noteRead = new NoteRead();
                            AdminDao adminDao = new AdminDao();

                            userSearch= adminDao.AdminGetUser(resultUserName,userNote);
                            userNote.setNoteText(text);
                            userNote.setTitle(resultTitle);
                            userNote.setUserName(resultUserName);
                            userNote.setCreateTime(resultCreateTime);
                            userNote.setUpdateTime(resultUpdateTime);
                            userNote.setLike(resultLike);
                            noteRead.start(stage,userSearch,userNote);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }

                });

            });

        });

        // 我的信息按钮点击事件
        userMessage.setOnAction(event -> {
            Label labelMyInformation = new Label("我的信息");
            Label labelUserName = new Label("用户名:");
            Label labelId = new Label("用户id:");
            Label labelNoteNumber = new Label("笔记条数:");

            labelUserName.setFont(new Font("黑体",20));
            labelId.setFont(new Font("黑体",20));
            labelNoteNumber.setFont(new Font("黑体",20));
            labelMyInformation.setFont(new Font("微软雅黑", 50)); // 设置标签的字体

            Text userNameText = new Text(user.getUserName());
            Text userIdText = new Text(user.getUserId()+"");
            Text noteNumberText = new Text(user.getNoteNumber()+"");

            userNameText.setFont(new Font("宋体",20));
            userIdText.setFont(new Font("宋体",20));
            noteNumberText.setFont(new Font("宋体",20));


            VBox vBoxMy = new VBox();
            vBoxMy.setAlignment(Pos.TOP_LEFT);
            vBoxMy.getChildren().addAll(labelMyInformation);

            Button buttonModifyUser = new Button("更改信息");
            Button buttonModifyCode = new Button("修改密码");
            Button buttonReturn = new Button("返回");


            HBox hBox = new HBox();
            hBox.setSpacing(5);
            hBox.setAlignment(Pos.BOTTOM_LEFT);
            hBox.getChildren().addAll(buttonModifyCode,buttonModifyUser,buttonReturn);

            GridPane gridPane = new GridPane();
            gridPane.add(vBoxMy,1,0);
            gridPane.add(labelUserName,1,1);
            gridPane.add(userNameText,2,1);
            gridPane.add(labelId,1,2);
            gridPane.add(userIdText,2,2);
            gridPane.add(labelNoteNumber,1,3);
            gridPane.add(noteNumberText,2,3);
            gridPane.add(hBox,1,4);
            gridPane.setAlignment(Pos.CENTER);
            gridPane.setStyle("-fx-text-fill: #034093");
            gridPane.setStyle("-fx-background-color: #82b9aa");
            Scene sceneMyInformation = new Scene(gridPane);
            stage.setScene(sceneMyInformation);

            buttonReturn.setOnAction(e->{
                stage.setScene(scene);
            });

            buttonModifyUser.setOnAction(e->{

                Label labelNewUserName = new Label("新用户名");
                TextField textFieldName  = new TextField();
                Button buttonNew = new Button("确定更改");
                HBox hBoxUserIn = new HBox();
                hBoxUserIn.getChildren().addAll(labelNewUserName,textFieldName,buttonNew);
                Scene sceneModifyUser = new Scene(hBoxUserIn);
                Stage stageModifyUser = new Stage();
                stageModifyUser.setScene(sceneModifyUser);
                stageModifyUser.show();

                buttonNew.setOnAction(event1->{

                    userController = new UserController();
                    UserBean userBean = userController.modifyUserName(textFieldName.getText(),user);
                    Stage stageResult = new Stage();
                    Label labelResult =new Label(userBean.getResult());
                    VBox vBox1 = new VBox();
                    vBox1.setAlignment(Pos.CENTER);
                    vBox1.getChildren().addAll(labelResult);
                    stageResult.setScene(new Scene(vBox1));
                    stageResult.show();

                    if (userBean.getNewUserName()!= null){
                        stageModifyUser.close();
                        stage.close();
                    }


                });


            });

            buttonModifyCode.setOnAction(e->{

                Label labelOldPassword = new Label("旧密码");
                Label labelNewPassWord = new Label("新密码");
                Label labelCheckPassword = new Label("确认密码");

                PasswordField oldPasswordField = new PasswordField();
                PasswordField newPasswordField = new PasswordField();
                PasswordField checkPasswordField = new PasswordField();

                Button buttonClear= new Button("重置");
                Button buttonReturn2 = new Button("返回");
                Button OKButton = new Button("确认");

                GridPane gridPaneCode = new GridPane();

                gridPaneCode.setStyle("-fx-background-color: pink");

                gridPaneCode.add(labelOldPassword,0,0);
                gridPaneCode.add(oldPasswordField,1,0);
                gridPaneCode.add(labelNewPassWord,0,1);
                gridPaneCode.add(newPasswordField,1,1);
                gridPaneCode.add(labelCheckPassword,0,2);
                gridPaneCode.add(checkPasswordField,1,2);
                gridPaneCode.add(buttonClear,1,3);
                gridPaneCode.add(OKButton,0,3);
                gridPaneCode.add(buttonReturn2,2,3);
                //全部居中
                gridPaneCode.setAlignment(Pos.CENTER);
                //水平间距
                gridPaneCode.setHgap(5);
                //垂直间距
                gridPaneCode.setVgap(15);

                Scene sceneModifyCode = new Scene(gridPaneCode);

                stage.setScene(sceneModifyCode);

                buttonClear.setOnAction(event1 -> {
                    oldPasswordField.setText("");
                    newPasswordField.setText("");
                    checkPasswordField.setText("");
                });

                buttonReturn2.setOnAction(event1 -> {
                    stage.setScene(sceneMyInformation);
                });
                OKButton.setOnAction(event1-> {
                            userController = new UserController();
                            UserBean userBean = userController.modifyPassword(oldPasswordField.getText(), newPasswordField.getText(), checkPasswordField.getText(), user);

                            Text textResult = new Text();
                            textResult.setText(userBean.getResult());
                            VBox vBox2 = new VBox();
                            vBox2.setAlignment(Pos.CENTER);
                            vBox2.getChildren().addAll(textResult);
                            Stage stageResult = new Stage();
                            stageResult.setScene(new Scene(vBox2));
                            stageResult.showAndWait();
                            stage.show();
                            stage.setScene(sceneMyInformation);
                        });
            });

        });
    }




    @Override
    public void start(Stage stage) throws Exception {

    }

}
