package 选课;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.AnchorPane;

import java.sql.*;

public class TeacherView {
    AnchorPane pane = new AnchorPane();
    Scene scene = new Scene(pane);

    public  void tView(Stage primaryStage,Scene oldscene) throws Exception {
        Button bt1=new Button("选课结果");
        bt1.setLayoutX(410);
        bt1.setLayoutY(50);
        pane.getChildren().add(bt1);
        Button bt2=new Button("录入成绩");
        bt2.setLayoutX(410);
        bt2.setLayoutY(100);
        pane.getChildren().add(bt2);
        Button bt3=new Button("按学分排序");
        bt3.setLayoutX(410);
        bt3.setLayoutY(150);
        pane.getChildren().add(bt3);
        Button bt4=new Button("退出登录");
        bt4.setLayoutX(410);
        bt4.setLayoutY(250);
        pane.getChildren().add(bt4);
        Button bt5=new Button("课程不及格");
        bt5.setLayoutX(410);
        bt5.setLayoutY(200);
        pane.getChildren().add(bt5);
        //
        TableView table = new TableView();
        TableColumn cno=new TableColumn("课程号");
        TableColumn cname=new TableColumn("课程名");
        TableColumn sno=new TableColumn("学号");
        TableColumn sname=new TableColumn("姓名");
        TableColumn grade=new TableColumn("成绩");
        cno.setCellValueFactory(
                new PropertyValueFactory<>("cno"));
        cname.setCellValueFactory(
                new PropertyValueFactory<>("cname"));
        sno.setCellValueFactory(
                new PropertyValueFactory<>("sno"));
        sname.setCellValueFactory(
                new PropertyValueFactory<>("sname"));
        grade.setCellValueFactory(
                new PropertyValueFactory<>("grade"));
        //table.setItems(data);
        table.getColumns().addAll(cno,cname,sno,sname,grade);
        pane.getChildren().add(table);
        //

        bt1.setOnAction(e->{
            try {
                xianshi();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        bt2.setOnAction(e->{
            AnchorPane pane1=new AnchorPane();
            Stage stage1=new Stage();
            Scene scene1=new Scene(pane1);
            HBox h1=new HBox();
            Label l1=new Label("课程号");
            TextField t1=new TextField();
            h1.getChildren().addAll(l1,t1);
            h1.setLayoutX(50);
            h1.setLayoutY(50);
            pane1.getChildren().add(h1);
            HBox h2=new HBox();
            Label l2=new Label("学号");
            TextField t2=new TextField();
            h2.getChildren().addAll(l2,t2);
            h2.setSpacing(13);
            h2.setLayoutX(50);
            h2.setLayoutY(100);
            pane1.getChildren().add(h2);
            HBox h3=new HBox();
            Label l3=new Label("成绩");
            TextField t3=new TextField();
            h3.getChildren().addAll(l3,t3);
            h3.setSpacing(13);
            h3.setLayoutX(50);
            h3.setLayoutY(150);
            pane1.getChildren().add(h3);
            Button b1=new Button("确定");
            b1.setLayoutX(125);
            b1.setLayoutY(180);
            pane1.getChildren().add(b1);
            stage1.setResizable(false);
            stage1.setScene(scene1);
            stage1.initModality(Modality.APPLICATION_MODAL);
            stage1.setHeight(300);
            stage1.setWidth(300);
            b1.setOnAction(event->{
                if(t1.getText().equals("") || t2.getText().equals("") || t3.getText().equals("")){
                    AnchorPane pane=new AnchorPane();
                    Scene scene=new Scene(pane);
                    Stage stage=new Stage();
                    Label l=new Label("不能为空！");
                    l.setLayoutX(70);
                    l.setLayoutY(50);
                    pane.getChildren().add(l);
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.setHeight(150);
                    stage.setWidth(250);
                    stage.show();
                }else {
                    int Cno=Integer.parseInt(t1.getText());
                    int Sno=Integer.parseInt(t2.getText());
                    int Grade=Integer.parseInt(t3.getText());
                    try {
                        if(!yanzheng(Cno,Sno)){
                            AnchorPane pane=new AnchorPane();
                            Scene scene=new Scene(pane);
                            Stage stage=new Stage();
                            Label l=new Label("无效操作！");
                            l.setLayoutX(70);
                            l.setLayoutY(50);
                            pane.getChildren().add(l);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setHeight(150);
                            stage.setWidth(250);
                            stage.show();
                        }else {
                            try {
                                dafen(Cno,Sno,Grade);
                                AnchorPane pane=new AnchorPane();
                                Scene scene=new Scene(pane);
                                Stage stage=new Stage();
                                Label l=new Label("成绩已更改！");
                                l.setLayoutX(70);
                                l.setLayoutY(50);
                                pane.getChildren().add(l);
                                stage.setResizable(false);
                                stage.setScene(scene);
                                stage.initModality(Modality.APPLICATION_MODAL);
                                stage.setHeight(150);
                                stage.setWidth(250);
                                stage.show();
                            } catch (Exception ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });
            stage1.show();

        });
        bt3.setOnAction(e->{
            try {
                paixu();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        bt4.setOnAction(e->{
            primaryStage.setScene(oldscene);
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("老师管理界面");
        primaryStage.show();
        bt5.setOnAction(e->{
            try {
                bujuge();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }
    boolean yanzheng(int cno,int sno) throws Exception{  //判断该成绩是否存在
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        String SQL = "select * from sc where Cno=? and Sno=?";
        PreparedStatement statement=connection.prepareStatement(SQL);
        statement.setInt(1,cno);
        statement.setInt(2,sno);
        ResultSet resultSet=statement.executeQuery();
        while (resultSet.next()){
            return true;
        }
        return false;
    }
    void dafen(int cno,int sno,int grade) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        String SQL = "update sc set grade=? where Sno=? and Cno=?";
        PreparedStatement statement=connection.prepareStatement(SQL);
        statement.setInt(1,grade);
        statement.setInt(2,sno);
        statement.setInt(3,cno);
        statement.executeUpdate();
    }
    void xianshi() throws Exception{  //显示所有学生成绩
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        String sql = "select sc.Cno,Cname,Cscore,sc.Sno,Sname,grade from sc,course,student where course.Cno=sc.Cno and student.Sno=sc.Sno order by Cscore"; //SQL选择查询语句以该账号为条件查询该表
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery(sql);
        ObservableList<SC> data = FXCollections.observableArrayList();
        TableView <SC>table = new TableView();
        TableColumn Cno=new TableColumn("课程号");
        TableColumn Cname=new TableColumn("课程名");
        TableColumn Cscore=new TableColumn("学分");
        TableColumn Sno=new TableColumn("学号");
        TableColumn Sname=new TableColumn("姓名");
        TableColumn Grade=new TableColumn("成绩");
        Cno.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Cno"));
        Cname.setCellValueFactory(
                new PropertyValueFactory<SC,String>("Cname"));
        Cscore.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Cscore"));
        Sno.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Sno"));
        Sname.setCellValueFactory(
                new PropertyValueFactory<SC,String>("Sname"));
        Grade.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Grade"));
        while(rs.next()){
            data.add(new SC(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6)));
        }
        table.setItems(data);
        table.getColumns().addAll(Cno,Cname,Cscore,Sno,Sname,Grade);
        Cno.setPrefWidth(60);
        Cname.setPrefWidth(80);
        Cscore.setPrefWidth(60);
        Sno.setPrefWidth(60);
        Sname.setPrefWidth(60);
        pane.getChildren().add(table);
    }
    void paixu() throws Exception{  //按总学分排序
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        String sql = "select Sno,Sname,Ssex,totalscore from student order by totalscore;"; //SQL选择查询语句以该账号为条件查询该表
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery(sql);
        ObservableList<Student> data = FXCollections.observableArrayList();
        TableView <Student>table = new TableView();
        TableColumn Sno=new TableColumn("学号");
        TableColumn Sname=new TableColumn("姓名");
        TableColumn Ssex=new TableColumn("性别");
        TableColumn totalscore=new TableColumn("总学分");
        Sno.setCellValueFactory(
                new PropertyValueFactory<>("Sno"));
        Sname.setCellValueFactory(
                new PropertyValueFactory<>("Sname"));
        Ssex.setCellValueFactory(
                new PropertyValueFactory<>("Ssex"));
        totalscore.setCellValueFactory(
                new PropertyValueFactory<>("totalscore"));
        while(rs.next()){
            data.add(new Student(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));
        }
        table.setItems(data);
        table.getColumns().addAll(Sno,Sname,Ssex,totalscore);
        Sno.setPrefWidth(100);
        Sname.setPrefWidth(100);
        Ssex.setPrefWidth(100);
        totalscore.setPrefWidth(100);
        pane.getChildren().add(table);
    }
    void bujuge() throws Exception{  //按总学分排序
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
        String sql = "select sc.Cno,Cname,Cscore,sc.Sno,Sname,grade from sc,course,student where course.Cno=sc.Cno and student.Sno=sc.Sno  and grade<60"; //SQL选择查询语句以该账号为条件查询该表
        Statement statement=connection.createStatement();
        ResultSet rs=statement.executeQuery(sql);
        ObservableList<SC> data = FXCollections.observableArrayList();
        TableView <SC>table = new TableView();
        TableColumn Cno=new TableColumn("课程号");
        TableColumn Cname=new TableColumn("课程名");
        TableColumn Cscore=new TableColumn("学分");
        TableColumn Sno=new TableColumn("学号");
        TableColumn Sname=new TableColumn("姓名");
        TableColumn Grade=new TableColumn("成绩");
        Cno.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Cno"));
        Cname.setCellValueFactory(
                new PropertyValueFactory<SC,String>("Cname"));
        Cscore.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Cscore"));
        Sno.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Sno"));
        Sname.setCellValueFactory(
                new PropertyValueFactory<SC,String>("Sname"));
        Grade.setCellValueFactory(
                new PropertyValueFactory<SC,Integer>("Grade"));
        while(rs.next()){
            data.add(new SC(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getString(5),rs.getInt(6)));
        }
        table.setItems(data);
        table.getColumns().addAll(Cno,Cname,Cscore,Sno,Sname,Grade);
        Cno.setPrefWidth(60);
        Cname.setPrefWidth(80);
        Cscore.setPrefWidth(60);
        Sno.setPrefWidth(60);
        Sname.setPrefWidth(60);
        pane.getChildren().add(table);
    }
}
