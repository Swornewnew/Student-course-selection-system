package 选课;
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
public class StudentView {
    AnchorPane pane = new AnchorPane();
    Scene scene = new Scene(pane);

    public  void sView(Stage primaryStage,Scene oldsence,int sno) throws Exception {
        //
        //System.out.println(n);
        Button bt1=new Button("课程信息");
        bt1.setLayoutX(420);
        bt1.setLayoutY(50);
        pane.getChildren().add(bt1);
        Button bt2=new Button("选课页面");
        bt2.setLayoutX(420);
        bt2.setLayoutY(100);
        pane.getChildren().add(bt2);
        Button bt4=new Button("已选课程");
        bt4.setLayoutX(420);
        bt4.setLayoutY(150);
        pane.getChildren().add(bt4);
        Button bt3=new Button("选课完成");
        bt3.setLayoutX(420);
        bt3.setLayoutY(200);
        pane.getChildren().add(bt3);
        //
        TableView <Course>table = new TableView();
        TableColumn cno=new TableColumn("课程号");
        TableColumn cname=new TableColumn("课程名");
        TableColumn ccount=new TableColumn("最大选课数");
        TableColumn cscore=new TableColumn("学分");
        TableColumn cperson=new TableColumn("当前选课人数");
        cno.setCellValueFactory(
                new PropertyValueFactory<>("cno"));
        cname.setCellValueFactory(
                new PropertyValueFactory<>("cname"));
        ccount.setCellValueFactory(
                new PropertyValueFactory<>("ccount"));
        cscore.setCellValueFactory(
                new PropertyValueFactory<>("cscore"));
        cperson.setCellValueFactory(
                new PropertyValueFactory<>("cperson"));
        //table.setItems(data);
        table.getColumns().addAll(cno,cname,ccount,cscore,cperson);
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
            try {
                int n=num(sno);
                xuanke(sno,n);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        bt3.setOnAction(e->{
            int n= 0;
            try {
                n = num(sno);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            if(n>=3 && n<=5){
                primaryStage.setScene(oldsence);
            }
            else {
                AnchorPane pane2=new AnchorPane();
                Scene scene2=new Scene(pane2);
                Stage stage2=new Stage();
                Label l2=new Label("请完成选课！");
                l2.setLayoutX(70);
                l2.setLayoutY(50);
                pane2.getChildren().add(l2);
                stage2.setResizable(false);
                stage2.setScene(scene2);
                stage2.initModality(Modality.APPLICATION_MODAL);
                stage2.setHeight(150);
                stage2.setWidth(250);
                stage2.show();
            }
        });
        bt4.setOnAction(e->{
            try {
                yixuan(sno);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        primaryStage.setScene(scene);
        primaryStage.setTitle("学生选课界面");


  }
  void  yixuan(int sno) throws Exception{ //显示该学生已经选择的课程
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL = "select course.Cno, Cname, Ccount, Cscore, Cperson from course,sc where Sno=? and sc.Cno=course.Cno order by course.Cno";
      //String SQL = "select * from course"; //SQL选择查询语句以该账号为条件查询该表
      PreparedStatement statement=connection.prepareStatement(SQL);
      statement.setInt(1,sno);
      //Statement statement=connection.createStatement();
      ResultSet rs=statement.executeQuery();
      ObservableList<Course> data = FXCollections.observableArrayList();
      TableView <Course>table = new TableView();
      TableColumn cno=new TableColumn("课程号");
      TableColumn cname=new TableColumn("课程名");
      TableColumn ccount=new TableColumn("最大选课数");
      TableColumn cscore=new TableColumn("学分");
      TableColumn cperson=new TableColumn("当前选课人数");
      cno.setCellValueFactory(
              new PropertyValueFactory<>("cno"));
      cname.setCellValueFactory(
              new PropertyValueFactory<>("cname"));
      ccount.setCellValueFactory(
              new PropertyValueFactory<>("ccount"));
      cscore.setCellValueFactory(
              new PropertyValueFactory<>("cscore"));
      cperson.setCellValueFactory(
              new PropertyValueFactory<>("cperson"));
      //table.setItems(data);
      while(rs.next()){
          data.add(new Course(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
      }
      table.setItems(data);
      table.getColumns().addAll(cno,cname,ccount,cscore,cperson);
      pane.getChildren().add(table);
  }
  void xianshi() throws Exception{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String sql = "select * from course"; //SQL选择查询语句以该账号为条件查询该表
      Statement statement=connection.createStatement();
      ResultSet rs=statement.executeQuery(sql);
      ObservableList<Course> data = FXCollections.observableArrayList();
      TableView <Course>table = new TableView();
      TableColumn cno=new TableColumn("课程号");
      TableColumn cname=new TableColumn("课程名");
      TableColumn ccount=new TableColumn("最大选课数");
      TableColumn cscore=new TableColumn("学分");
      TableColumn cperson=new TableColumn("当前选课人数");
      cno.setCellValueFactory(
              new PropertyValueFactory<>("cno"));
      cname.setCellValueFactory(
              new PropertyValueFactory<>("cname"));
      ccount.setCellValueFactory(
              new PropertyValueFactory<>("ccount"));
      cscore.setCellValueFactory(
              new PropertyValueFactory<>("cscore"));
      cperson.setCellValueFactory(
              new PropertyValueFactory<>("cperson"));
      while(rs.next()){
          data.add(new Course(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4),rs.getInt(5)));
      }
      table.setItems(data);
      table.getColumns().addAll(cno,cname,ccount,cscore,cperson);
      pane.getChildren().add(table);
  }
  void xuanke(int sno,int n) throws Exception{
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL = " insert into sc(Sno,Cno) values(?,?)";
      PreparedStatement statement=connection.prepareStatement(SQL);
      AnchorPane pane1=new AnchorPane();
      Scene scene1=new Scene(pane1);
      HBox h1=new HBox();
      Label l1=new Label("课程号");
      TextField t1=new TextField();
      h1.getChildren().addAll(l1,t1);
      h1.setLayoutX(70);
      h1.setLayoutY(70);
      pane1.getChildren().add(h1);
      Button b1=new Button("选该课");
      b1.setLayoutX(120);
      b1.setLayoutY(100);
      pane1.getChildren().add(b1);
      Stage stage1=new Stage();
      stage1.setResizable(false);
      stage1.setScene(scene1);
      stage1.initModality(Modality.APPLICATION_MODAL);
      stage1.setHeight(300);
      stage1.setWidth(300);
      stage1.show();
      b1.setOnAction(e->{
          if(t1.getText().equals("")){
              AnchorPane pane3=new AnchorPane();
              Scene scene3=new Scene(pane3);
              Stage stage3=new Stage();
              Label l3=new Label("课程号不能为空！");
              l3.setLayoutX(70);
              l3.setLayoutY(50);
              pane3.getChildren().add(l3);
              stage3.setResizable(false);
              stage3.setScene(scene3);
              stage3.initModality(Modality.APPLICATION_MODAL);
              stage3.setHeight(150);
              stage3.setWidth(250);
              stage3.show();
          }else {
              int cno=Integer.parseInt(t1.getText());
              int temp;
              try {
                  temp= num(sno);
              } catch (Exception ex) {
                  throw new RuntimeException(ex);
              }
              if(temp>=5){
                  AnchorPane pane4=new AnchorPane();
                  Scene scene4=new Scene(pane4);
                  Stage stage4=new Stage();
                  Label l3=new Label("已达最大选课数！");
                  l3.setLayoutX(70);
                  l3.setLayoutY(50);
                  pane4.getChildren().add(l3);
                  stage4.setResizable(false);
                  stage4.setScene(scene4);
                  stage4.initModality(Modality.APPLICATION_MODAL);
                  stage4.setHeight(150);
                  stage4.setWidth(250);
                  stage4.show();
              }else {
                  try {
                      if(cno>7 || cno<0){
                          AnchorPane pane5=new AnchorPane();
                          Scene scene5=new Scene(pane5);
                          Stage stage5=new Stage();
                          Label l5=new Label("无效的课程号！");
                          l5.setLayoutX(70);
                          l5.setLayoutY(50);
                          pane5.getChildren().add(l5);
                          stage5.setResizable(false);
                          stage5.setScene(scene5);
                          stage5.initModality(Modality.APPLICATION_MODAL);
                          stage5.setHeight(150);
                          stage5.setWidth(250);
                          stage5.show();
                      }
                      else if(sc(sno,cno)){
                          AnchorPane pane5=new AnchorPane();
                          Scene scene5=new Scene(pane5);
                          Stage stage5=new Stage();
                          Label l5=new Label("您已经选过该课！");
                          l5.setLayoutX(70);
                          l5.setLayoutY(50);
                          pane5.getChildren().add(l5);
                          stage5.setResizable(false);
                          stage5.setScene(scene5);
                          stage5.initModality(Modality.APPLICATION_MODAL);
                          stage5.setHeight(150);
                          stage5.setWidth(250);
                          stage5.show();
                      }
                      else {
                          int line;
                          try {
                              statement.setInt(1,sno);
                              statement.setInt(2,cno);
                              line=statement.executeUpdate();
                          } catch (SQLException ex) {
                              throw new RuntimeException(ex);
                          }
                          try {
                              updatecourse(cno);
                              updatexuankeshu(sno);
                              updatetotalscore(sno,cno);
                          } catch (Exception ex) {
                              throw new RuntimeException(ex);
                          }
                          if(line==1){
                              AnchorPane pane4=new AnchorPane();
                              Scene scene4=new Scene(pane4);
                              Stage stage4=new Stage();
                              Label l4=new Label("选课成功！");
                              l4.setLayoutX(70);
                              l4.setLayoutY(50);
                              pane4.getChildren().add(l4);
                              stage4.setResizable(false);
                              stage4.setScene(scene4);
                              stage4.initModality(Modality.APPLICATION_MODAL);
                              stage4.setHeight(150);
                              stage4.setWidth(250);
                              stage4.show();
                          }
                      }
                  } catch (Exception ex) {
                      throw new RuntimeException(ex);
                  }
              }
          }
      });

  }
  int num(int sno) throws Exception{  //返回该学生当前的选课数
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL="select num from xuankeshu where Sno = ?";
      PreparedStatement statement=connection.prepareStatement(SQL);
      statement.setInt(1,sno);
      //执行sql并接收返回结果
      ResultSet resultSet = statement.executeQuery();
      int res=0;
      while (resultSet.next()){
          res=resultSet.getInt(1);
      }
      return res;
  }
  void updatecourse(int cno) throws Exception{  //更新选课人数
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL = "update course set Cperson=Cperson+1 where Cno=?";
      PreparedStatement statement=connection.prepareStatement(SQL);
      statement.setInt(1,cno);
      statement.executeUpdate();
  }
  void updatexuankeshu(int sno) throws Exception{  //更新当前选课数
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL = "update xuankeshu set num=num+1 where Sno=?";
      PreparedStatement statement=connection.prepareStatement(SQL);
      statement.setInt(1,sno);
      statement.executeUpdate();
  }
  void updatetotalscore(int sno,int cno) throws Exception{  //更新总学分
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL = "update student,course set totalscore=totalscore+Cscore where Sno=? and Cno=?";
      PreparedStatement statement=connection.prepareStatement(SQL);
      statement.setInt(1,sno);
      statement.setInt(2,cno);
      statement.executeUpdate();
  }
  boolean sc(int sno,int cno) throws Exception{  //判断是否选过该门课
      Class.forName("com.mysql.cj.jdbc.Driver");
      Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db?serverTimezone=Asia/Shanghai", "root", "123456");
      String SQL = "select *from sc where Sno=? and Cno=?";
      PreparedStatement statement=connection.prepareStatement(SQL);
      statement.setInt(1,sno);
      statement.setInt(2,cno);
      ResultSet resultSet=statement.executeQuery();
      if(resultSet.next()) return true;
      return false;
  }
}
