package com.wuxinghua.notes.view;

import com.wuxinghua.notes.bean.Msg;
import com.wuxinghua.notes.dao.NoteDao;
import com.wuxinghua.notes.dao.WriteDao;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import com.wuxinghua.notes.service.RenameService;
import com.wuxinghua.notes.util.ValidateUtil;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class NoteWrite extends Application {

    static String oldTitle;

    /**
     * 增加水平盒子：显示标题
     * @param titleText
     * @param note
     * @param user
     * @return
     */
    public HBox addHBox(String titleText, Note note, User user) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12)); //节点到边缘的距离
        hBox.setSpacing(10); // 节点之间的间距
        hBox.setStyle("-fx-background-color: #336699;"); // 背景色

        TextField titleTextField = new TextField(titleText);
        titleTextField.setPrefSize(1000,20);
        Label titleLabel = new Label("标题");

        Button saveTitle = new Button("保存标题");
        saveTitle.setPrefSize(100, 20);
        hBox.getChildren().addAll(titleLabel, titleTextField, saveTitle);

        // 保存标题按钮点击事件
        saveTitle.setOnAction(event -> {
            RenameService renameService = new RenameService();
            note.setTitle(titleTextField.getText());


            if (!ValidateUtil.isValidNoteTitle(titleTextField.getText())){
                Label labelEmpty = new Label("标题不能为空");
                hBox.getChildren().add(labelEmpty);
            }else {
                try {
                    if (oldTitle == null) {
                        oldTitle = renameService.rename(note, titleText);

                    } else {
                        System.out.println("原旧标题" + oldTitle);
                        oldTitle = renameService.rename(note, oldTitle);
                        System.out.println("新旧标题" + oldTitle);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return hBox;
    }

    /**
     * 增加垂直盒子：增加按钮
     * @param stage
     * @param sceneAfterLogin
     * @param note
     * @param user
     * @return
     */
    public VBox addVBox(Stage stage, Scene sceneAfterLogin, Note note,User user) {
        VBox vBox = new VBox();

        vBox.setPadding(new Insets(15, 15, 15, 15)); //节点到边缘的距离
        vBox.setSpacing(10); //节点之间的间距
        vBox.setStyle("-fx-background-color: #25e746;"); //背景色
        vBox.setAlignment(Pos.BOTTOM_RIGHT);

        Button commit = new Button("发布笔记");
        // 未实现
        Button insertPicture = new Button("插入图片");
        // 未实现
        Button wordNumber = new Button("统计字数");
        Button deleteNote = new Button("删除笔记");
        Button cancel = new Button("返回");

        vBox.getChildren().addAll(commit,insertPicture,wordNumber,deleteNote,cancel);


        // 发布笔记按钮点击事件
        commit.setOnAction(e->{
            if (ValidateUtil.isSaveInformation(note.getNoteText())) {
                WriteDao writeDao = new WriteDao();
                writeDao.noteWriteCommit(note);
                writeDao.DBModifyVisibility(note);
                oldTitle = null;
                System.out.println(note.getNoteText());
                Label labelSuccess = new Label("提交成功");
                VBox vBoxSuccess = new VBox();
                vBoxSuccess.setAlignment(Pos.CENTER);
                vBoxSuccess.getChildren().addAll(labelSuccess);

                Stage stageCommit = new Stage();
                stageCommit.setScene(new Scene(vBoxSuccess));
                stageCommit.showAndWait();

                stage.setScene(sceneAfterLogin);
                stage.show();
            }else{
                Label labelNo = new Label("你尚未保存信息或信息为空，请点击保存按钮");
                vBox.getChildren().add(labelNo);
            }
        });

        // 删除笔记点击事件
        deleteNote.setOnAction(e->{
            NoteDao noteDao = new NoteDao();
            Msg msg =  noteDao.fileNoteDelete(note.getTitle());
            noteDao.titleRemove(user,note);
            Label label = new Label(msg.getResult());

            VBox vBoxDelete = new VBox();
            vBoxDelete.getChildren().add(label);
            Stage stageDelete = new Stage();
            stageDelete.setScene(new Scene(vBoxDelete));
            stageDelete.showAndWait();

            stage.setScene(sceneAfterLogin);
            stage.show();
        });

        // 返回点击事件
        cancel.setOnAction(e->{
            HBox hBox = new HBox();

            Text text = new Text("你尚未发布笔记，确定要退出吗？");
            Button yes = new Button("是");
            Button no = new Button("否");
            hBox.getChildren().addAll(text,yes,no);
            Stage stage1 = new Stage();
            stage1.setScene(new Scene(hBox));
            stage1.show();

            yes.setOnAction(event->{
                stage1.close();
                stage.setScene(sceneAfterLogin);

            });

            no.setOnAction(event -> {
                stage1.close();
            });

        });

        return vBox;
    }

    /**
     * 增加网格布局：显示笔记内容及其部分按钮
     * @param note
     * @return
     */
    public GridPane addGridPane(Note note) {

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10,10,10,10));
        Button wordWrap = new Button("自动换行");
        Button cancelWordWrap = new Button("取消自动换行");
        Button save = new Button("保存");

        TextArea noteTextArea = new TextArea(note.getNoteText());
        noteTextArea.setPrefSize(2000,2000);
        //noteTextArea.setMouseTransparent(true); // 鼠标无作用

        gridPane.add(noteTextArea,0,0);
        gridPane.add(wordWrap,0,1);
        gridPane.add(cancelWordWrap,0,2);
        gridPane.add(save,0,3);


        // 自动换行点击事件
        wordWrap.setOnAction(e->{
            noteTextArea.setWrapText(true);

        });

        // 取消自动换行按钮点击事件
        cancelWordWrap.setOnAction(e->{
            noteTextArea.setWrapText(false);
        });

        // 保存按钮点击事件
        save.setOnAction(e->{
            note.setNoteText(noteTextArea.getText());

        });

        return gridPane;
    }

    /**
     * 增加水平盒子：设置笔记为公开或私有
     * @param note
     * @return
     */
    public HBox addHBox(Note note){

        HBox hBox = new HBox();

        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        // 创建一组Radio Button
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("公开");
        rb1.setToggleGroup(group);
        rb1.setUserData("公开");

        RadioButton rb2 = new RadioButton("私有");
        rb2.setToggleGroup(group);
        rb2.setUserData("私有");
        rb2.setSelected(true);

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            if (group.getSelectedToggle() != null) {
                if (group.getSelectedToggle().getUserData().toString().equals("公开")){
                    note.setVisibility(true);
                    System.out.println("正确");
                }
                else{
                    note.setVisibility(false);
                    System.out.println("错误");
                }
                System.out.println(group.getSelectedToggle().getUserData().toString());
            }
        });

        hBox.getChildren().addAll(rb1,rb2);

        return hBox;
    }

    public void start(Stage stage,Scene afterLoginScene, User user,Note note,String titleText) throws Exception {
        // 边框面版
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(addHBox(titleText,note,user));
        borderPane.setBottom(addHBox(note));
        borderPane.setCenter(addGridPane(note));
        borderPane.setRight(addVBox(stage,afterLoginScene,note,user));


        Scene scene = new Scene(borderPane);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
