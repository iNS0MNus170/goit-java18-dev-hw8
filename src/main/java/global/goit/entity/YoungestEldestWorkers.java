package global.goit.entity;

public class YoungestEldestWorkers {
    private String type;
    private String name;
    private String birthday;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "type - " + type +
                ", name - " + name +
                ", birthday - " + birthday + "\n";
    }
}
