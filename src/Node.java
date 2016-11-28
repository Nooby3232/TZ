/**
 * Created by User on 26.11.2016.
 */
public interface Node {
    public void setName(String str);
    public String getName();
    public void setThisId(int id);
    public int getThisId();
    public void setPreviousId(int id);
    public int getPreviousId();
    public void setValue(String str);
    public String getValue();
    public String writeNode();
}
