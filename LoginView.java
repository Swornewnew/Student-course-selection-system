package 选课;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.sql.*;


public class LoginView extends  Application{
    Label l1=new Label("账号:");//账号标签
    TextField f1=new TextField(); //账号文本框
    HBox user=new HBox();  //账号输入框布局
    Label l2=new Label("密码:"); //密码标签
    TextField f2=new TextField();  //密码文本框
    HBox password=new HBox();//密码输入框布局
    Button btOK=new Button("确定"); //确定按钮
    RadioButton button1=new RadioButton("学生");
    RadioButton button2=new RadioButton("老师");
    ToggleGroup group = new ToggleGroup();//添加单选按钮到ToggleGroup对象,使得一次只能选择一个单选按钮
    HBox select=new HBox();
    AnchorPane pane=new AnchorPane();  //总体登入页面布局
    int type;
    private static final String SQL_LOGIN="select * from users where name=? and password=? and type=?";
    int sno(User user) throws Exception{  //根据用户名得到学号
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        String SQL_SNO="select Sno,Sname,Ssex from student where Sname=?";
        PreparedStatement preparedStatement=connection.prepareStatement(SQL_SNO);
        preparedStatement.setString(1,user.getName());
        ResultSet resultSet=preparedStatement.executeQuery();
        if (resultSet.next()){
            //System.out.println(resultSet.getInt("Sno"));
            return resultSet.getInt("Sno");
        }
        return -1;
    }
    boolean yanzheng(User user) throws Exception{  //验证用户信息
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        PreparedStatement preparedStatement=connection.prepareStatement(SQL_LOGIN);
        preparedStatement.setString(1,user.getName());
        preparedStatement.setString(2,user.getPassword());
        preparedStatement.setInt(3,user.getType());
        ResultSet result=preparedStatement.executeQuery();
        while(result.next()){
            return true;
        }
        return false;
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        user.getChildren().addAll(l1,f1);
        password.getChildren().addAll(l2,f2);
        //设置确定按钮的位置
        btOK.setLayoutX(250);
        btOK.setLayoutY(320);
        pane.getChildren().add(btOK);
        //设置账号文本框的位置
        user.setLayoutX(180);
        user.setLayoutY(200);
        pane.getChildren().add(user);
        //设置密码文本框的位置
        password.setLayoutX(180);
        password.setLayoutY(250);
        pane.getChildren().add(password);
        //设置单选按钮，选择身份（老师或学生）
        button1.setToggleGroup(group);
        button1.setSelected(false);
        button2.setToggleGroup(group);
        button2.setSelected(false);
        select.getChildren().addAll(button1,button2);
        select.setLayoutX(220);
        select.setLayoutY(280);
        pane.getChildren().add(select);
        //添加文字
        Text text=new Text(120,150,"学生选课及学籍管理系统");
        text.setFill(Color.rgb(30, 40, 50, .99));
        text.setFont(Font.font(null, FontWeight.BOLD, 28));
        pane.getChildren().add(text);
        //添加图片
        Image image = new Image("选课/img_1.png");
        Background background=new Background(new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT));
        pane.setBackground(background);
        Scene scene=new Scene(pane); //登入界面场景
        primaryStage.setTitle("学生选课及学籍管理系统登录界面");
        primaryStage.setScene(scene);
        primaryStage.setWidth(500);
        primaryStage.setHeight(500);
        primaryStage.getIcons().add(new Image("选课/img.png"));
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton r = (RadioButton) newValue;
                //System.out.println("已选择:" + r.getText());
                String temp; //保存监听的数据，用于判断是学生还是老师
                temp=r.getText();
                if(temp=="老师"){
                    type=2;
                }else{
                    type=1;
                }
            }
        });
        btOK.setOnAction(event->{

            User user=new User(f1.getText(),f2.getText(),type);
            try {
                if(yanzheng(user)){
                    //System.out.println("登录成功");
                    if(type==1){

                        StudentView studentView=new StudentView();
                        try {
                            studentView.sView(primaryStage,scene,sno(user));
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }if(type==2){
                        TeacherView teacherView=new TeacherView();
                        try {
                            teacherView.tView(primaryStage,scene);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }else{
                    AnchorPane pane5=new AnchorPane();
                    Scene scene5=new Scene(pane5);
                    Stage stage5=new Stage();
                    Label l5=new Label("登录失败！");
                    l5.setLayoutX(70);
                    l5.setLayoutY(50);
                    pane5.getChildren().add(l5);
                    stage5.setResizable(false);
                    stage5.setScene(scene5);
                    stage5.initModality(Modality.APPLICATION_MODAL);
                    stage5.setHeight(150);
                    stage5.setWidth(250);
                    stage5.show();
                    //System.out.println("账号或密码或身份错误");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public static void main(String[] args){
        Application.launch(args);
    }
}
