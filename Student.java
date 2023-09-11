package 选课;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {
    private final SimpleIntegerProperty Sno;
    private final SimpleStringProperty Sname;
    private final SimpleStringProperty Ssex;
    private final SimpleIntegerProperty totalscore;
    Student(int Sno, String Sname,String Ssex, int totalscore){
        this.Sno = new SimpleIntegerProperty(Sno);
        this.Sname = new SimpleStringProperty(Sname);
        this.Ssex = new SimpleStringProperty(Ssex);
        this.totalscore = new SimpleIntegerProperty(totalscore);
    }
    public int getSno() {
        return Sno.get();
    }

    public String getSname() {
        return Sname.get();
    }

    public String getSsex() {
        return Ssex.get();
    }

    public int getTotalscore() {
        return totalscore.get();
    }
}
