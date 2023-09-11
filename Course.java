package 选课;

import com.sun.source.tree.UsesTree;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Course {
    private final SimpleIntegerProperty cno;
    private final SimpleStringProperty cname;
    private final SimpleIntegerProperty ccount;
    private final SimpleIntegerProperty cscore;
    private final SimpleIntegerProperty cperson;
    Course(int cno, String cname,int ccount, int cscore,int cperson){
        this.cno = new SimpleIntegerProperty(cno);
        this.cname = new SimpleStringProperty(cname);
        this.ccount = new SimpleIntegerProperty(ccount);
        this.cscore = new SimpleIntegerProperty(cscore);
        this.cperson=new SimpleIntegerProperty(cperson);
    }

    public int getCno() {
        return cno.get();
    }

    public int getCcount() {
        return ccount.get();
    }

    public String getCname() {
        return cname.get();
    }

    public int getCscore() {
        return cscore.get();
    }

    public int getCperson() {
        return cperson.get();
    }
}
