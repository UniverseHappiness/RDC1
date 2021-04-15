package com.wuxinghua.notes.view;

import com.wuxinghua.notes.dao.NoteDao;
import com.wuxinghua.notes.entity.Note;
import com.wuxinghua.notes.entity.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class NoteRead extends Application {


    /**
     * 增加水平盒子：显示笔记标题、作者等具体信息
     * @param note
     * @param user
     * @return
     */
    public HBox addHBox(Note note, User user) {
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(15, 12, 15, 12)); //节点到边缘的距离
        hBox.setSpacing(10); //节点之间的间距
        hBox.setStyle("-fx-background-color: #336699;"); //背景色

        Text titleText = new Text(note.getTitle());
        titleText.setFont(new Font("微软雅黑",20));
        Text authorText = new Text(note.getUserName());
        authorText.setFont(new Font("微软雅黑",20));
        Text creaTimeText = new Text(note.getCreateTime());
        creaTimeText.setFont(new Font("微软雅黑",20));
        Text updateTimeText = new Text(note.getUpdateTime());
        updateTimeText.setFont(new Font("微软雅黑",20));

        Label titleLabel = new Label("标题:");
        titleLabel.setFont(new Font("黑体",20));
        Label authorLabel = new Label("作者:");
        authorLabel.setFont(new Font("黑体",20));
        Label createTimeLabel = new Label("创作时间:");
        createTimeLabel.setFont(new Font("黑体",20));
        Label updateTimeLabel = new Label("更新时间:");
        createTimeLabel.setFont(new Font("黑体",20));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(titleLabel, titleText,authorLabel,authorText,createTimeLabel,creaTimeText,updateTimeLabel,updateTimeText);

        return hBox;
    }

    /**
     * 增加网格面板：显示文本内容及部分按钮
     * @param note
     * @param user
     * @return
     */
    public GridPane addGridPane(Note note, User user) {

        GridPane gridPane = new GridPane();
        TextArea textArea = new TextArea();

        textArea.setText(note.getNoteText());
        textArea.setEditable(false);
        textArea.setPrefSize(1000,500);

        Button wordWrap = new Button("自动换行");
        Button cancelWordWrap = new Button("取消自动换行");
        Button likeButton = new Button("点赞");
        Button cancelLikeButton = new Button("取消点赞");

        Label likeLabel = new Label("❤");
        Label haveLikeLabel = new Label("请勿重复点赞");
        Label cancelLikeLabel = new Label("你已经取消点赞");
        likeLabel.setVisible(false);
        haveLikeLabel.setVisible(false);
        cancelLikeLabel.setVisible(false);
        gridPane.add(textArea,0,0);
        gridPane.add(wordWrap,0,1);
        gridPane.add(cancelWordWrap,0,2);

        gridPane.add(likeLabel,1,3);
        gridPane.add(haveLikeLabel,2,0);
        gridPane.add(cancelLikeLabel,2,0);

        gridPane.add(likeButton,1,1);

        gridPane.add(cancelLikeButton,1,2);

        // 自动换行按钮点击事件
        wordWrap.setOnAction(e->{
            textArea.setWrapText(true);

        });

        // 取消自动换行按钮点击事件
        cancelWordWrap.setOnAction(e->{
            textArea.setWrapText(false);
        });

        // 点赞按钮点击事件
        likeButton.setOnMouseClicked(e->{
            int like = note.getLike();

            if (likeLabel.isVisible()) {
                haveLikeLabel.setVisible(true);
                cancelLikeLabel.setVisible(false);
            }else {
                note.setLike(like + 1);

                NoteDao noteDao = new NoteDao();
                noteDao.like(like + 1, note);
                likeLabel.setVisible(true);
            }


        });

        // 取消点赞按钮点击事件
        cancelLikeButton.setOnMouseClicked(e->{

            if (likeLabel.isVisible()){
                int like = note.getLike();
                note.setLike(like - 1);
                NoteDao noteDao = new NoteDao();
                noteDao.like(like - 1, note);
                haveLikeLabel.setVisible(false);
                likeLabel.setVisible(false);
            }
            else {
                haveLikeLabel.setVisible(false);
                cancelLikeLabel.setVisible(true);

            }

        });
        return gridPane;
    }


    /**
     * 增加垂直盒子：显示返回按钮
     * @param stage
     * @param scene
     * @return
     */
    public VBox addVbox(Stage stage, Scene scene){

        Button buttonReturn = new Button("返回");

        VBox vBox = new VBox();
        vBox.getChildren().add(buttonReturn);
        vBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonReturn.setOnAction(e->{
            stage.setScene(scene);
        });

        return vBox;
    }
    public void start(Stage stage, User user, Note note) throws Exception {

        // 边框面版
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(addHBox(note, user));
        borderPane.setCenter(addGridPane(note, user));
        borderPane.setRight(addVbox(stage,stage.getScene()));
        Scene sceneRead = new Scene(borderPane);

        stage.setScene(sceneRead);
        stage.show();
    }



    @Override
    public void start(Stage stage) throws Exception {

    }
}
