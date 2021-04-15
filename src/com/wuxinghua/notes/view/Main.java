package com.wuxinghua.notes.view;

import com.wuxinghua.notes.bean.UserBean;
import com.wuxinghua.notes.controller.UserController;
import com.wuxinghua.notes.entity.User;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Main extends Application {
    private UserController userController;

    public static void main(String[] args) {

        Application.launch(args);
    }

    @Override
        public void start(Stage primaryStage) throws Exception {
            Label l_name = new Label("用户名");
            Label l_password = new Label("密码");
            TextField t_name = new TextField();
            PasswordField p_password = new PasswordField();

            Label l_name2 = new Label("新用户名");
            Label l_password2 = new Label("新密码");
            Label l_passwordCheck = new Label("确认密码");
            TextField t_name2 = new TextField();
            PasswordField p_password2 = new PasswordField();
            PasswordField p_password_check = new PasswordField();

            Button admin_login = new Button("管理员登录");
            Button user_login = new Button("用户登录");
            Button clear = new Button("重置");
            Button register = new Button("注册");
            Button exit = new Button("退出");



            Button clear_2= new Button("重置");
            Button return_2 = new Button("返回");
            Button register_2 = new Button("确认注册");
            //网格布局
            GridPane gr = new GridPane();
            GridPane gr2 = new GridPane();
            gr.setStyle("-fx-background-color: deepskyblue");
            gr.add(l_name,0,0);
            gr.add(t_name,1,0);
            gr.add(l_password,0,1);
            gr.add(p_password,1,1);


            gr.add(clear,0,2);
            gr.add(user_login,2,2);
            gr.add(admin_login,1,2);
            gr.add(register,0,3);
            gr.add(exit,2,3);
            //全部居中
            gr.setAlignment(Pos.CENTER);
            //水平间距
            gr.setHgap(5);
            //垂直间距
            gr.setVgap(15);

            //设置注册左边外边距
            GridPane.setMargin(admin_login,new Insets(0,0,0,120));

            gr2.add(l_name2,0,0);
            gr2.setStyle("-fx-background-color: green");
            gr2.add(t_name2,1,0);
            gr2.add(l_password2,0,1);
            gr2.add(p_password2,1,1);
            gr2.add(l_passwordCheck,0,2);
            gr2.add(p_password_check,1,2);
            gr2.add(clear_2,1,3);
            gr2.add(register_2,0,3);
            gr2.add(return_2,2,3);
            //全部居中
            gr2.setAlignment(Pos.CENTER);
            //水平间距
            gr2.setHgap(5);
            //垂直间距
            gr2.setVgap(15);


            Scene scene= new Scene(gr);
            Scene scene2 = new Scene(gr2);

            primaryStage.setScene(scene);
            primaryStage.setTitle("用户登录");
            primaryStage.setWidth(500);
            primaryStage.setHeight(300);
            primaryStage.show();




        //重置按钮点击事件
        clear.setOnAction(e->{
            t_name.setText("");
            p_password.setText("");
        });

        //管理员登录按钮点击事件
        admin_login.setOnAction(e->{

            String name = t_name.getText();
            String pasword = p_password.getText();

            if("admin".equals(name)&&"admin".equals(pasword)){
                System.out.println("管理员登录成功");
                Label login_success =  new Label("管理员登录成功");
                VBox vBox = new VBox(100);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().add(login_success);
                Stage stage =  new Stage();
                stage.setScene(new Scene(vBox));
                stage.showAndWait();
                AdminAfterLogin adminAfterLogin = new AdminAfterLogin();
                try {
                    adminAfterLogin.start(primaryStage);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }else{
                System.out.println("管理员登录失败");
                Label login_fail =  new Label("管理员登录失败");
                VBox vBox = new VBox(100);
                vBox.getChildren().add(login_fail);
                vBox.setAlignment(Pos.CENTER);
                Stage stage = new Stage();
                stage.setScene(new Scene(vBox));
                stage.showAndWait();
            }
        });

        // 用户登录点击事件
        user_login.setOnAction(e->{
            User login_user;

            UserBean userBean;
            userController = new UserController();
            userBean = userController.login(t_name.getText(),p_password.getText());
            login_user = userBean.getUser();

            Label login_result = new Label(userBean.getResult());
            VBox vBox = new VBox(100);
            vBox.getChildren().add(login_result);
            Stage stage = new Stage();
            stage.setScene(new Scene(vBox));
            stage.showAndWait();
            AfterLogin pt = new AfterLogin();
            try {
                pt.start(primaryStage,login_user);
            } catch (Exception ex) {
                System.out.println("抓住无用户异常");
            }

        });

        //注册按钮点击事件
        register.setOnAction(e->{
            primaryStage.setTitle("用户注册");
            primaryStage.setScene(scene2);

        });

        // 点击退出登录直接退出程序
        exit.setOnAction(e->{
            Platform.exit();
        });

        // 确认返回按钮点击事件
        register_2.setOnAction(e->{
            userController = new UserController();
            UserBean userBean = userController.register(t_name2.getText(),p_password2.getText(),p_password_check.getText());

            if (userBean.getUser() == null){
                    Label l_register_fail = new Label(userBean.getResult());
                    HBox hBox = new HBox();
                    hBox.getChildren().add(l_register_fail);

                    Stage stage = new Stage();
                    stage.initModality(Modality.APPLICATION_MODAL);//窗口的模式
                    Scene scene_register_fail = new Scene(hBox,150,150);

                    stage.setTitle("注册失败");
                    stage.setScene(scene_register_fail);
                    stage.showAndWait();

                    t_name2.setText("");
                    p_password2.setText("");
                    p_password_check.setText("");
            }else {
                System.out.println(userBean.getResult());
                primaryStage.setTitle("用户注册");
                Label l_register_result = new Label(userBean.getResult());
                HBox hBox = new HBox();
                hBox.getChildren().add(l_register_result);
                Stage stage = new Stage();
                stage.initModality(Modality.APPLICATION_MODAL); //  窗口的模式： 阻止输入事件从同一应用程序传递到所有窗口的阶段，子层次结构中的事件除外
                Scene scene_register_result = new Scene(hBox, 150, 150);
                stage.setTitle("注册成功");
                stage.setScene(scene_register_result);
                stage.showAndWait();
                primaryStage.setScene(scene);
            }

        });

        // 注册返回按钮点击事件
        return_2.setOnAction(e->{

            primaryStage.setScene(scene);
        });
    }

    }


