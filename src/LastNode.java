import java.util.ArrayList;


public class LastNode implements Node {
    String name;
    int thisId;
    String value;
    int previousId;
    public void setName(String str) {
        name = str;
    }
    public String getName() {
        return name;
    }
    public void setThisId(int id) {
        thisId = id;
    }
    public int getThisId() {
        return thisId;
    }
    public void setPreviousId(int id) {
        previousId = id;
    }
    public int getPreviousId() {
        return previousId;
    }
    public void setValue(String str) {
        this.value = str;
    }
    public String getValue() {
        return value;
    }
    public String writeNode() {
        return "node id: " + getThisId() + ", previous node id: " + getPreviousId() + ", node name: " + getName() + ", node value: " + getValue();
    }
}

