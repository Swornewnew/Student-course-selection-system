package 选课;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class SC {
    private final SimpleIntegerProperty Cno;
    private final SimpleStringProperty Cname;
    private final SimpleIntegerProperty Cscore;
    private final SimpleIntegerProperty Sno;
    private final SimpleStringProperty Sname;
    private final SimpleIntegerProperty Grade;
    SC(int cno, String cname,int Cscore,int sno, String sname,int grade){
        this.Cno = new SimpleIntegerProperty(cno);
        this.Cname = new SimpleStringProperty(cname);
        this.Cscore=new SimpleIntegerProperty(Cscore);
        this.Sno= new SimpleIntegerProperty(sno);
        this.Sname = new SimpleStringProperty(sname);
        this.Grade=new SimpleIntegerProperty(grade);
    }

    public String getCname() {
        return Cname.get();
    }

    public int getCno() {
        return Cno.get();
    }

    public int getCscore() {
        return Cscore.get();
    }

    public int getSno() {
        return Sno.get();
    }

    public String getSname() {
        return Sname.get();
    }

    public int getGrade() {
        return Grade.get();
    }
}
