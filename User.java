package 选课;

public class User {
    String name;
    String password;
    int type;
    User(){}
    User(String name,String password,int type){
        this.name=name;
        this.type=type; //0表示学生，1表示老师
        this.password=password;
    }
    User(String name,String password){
        this.name=name;
        this.password=password;
}
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getPassword() {
        return password;
    }
}
