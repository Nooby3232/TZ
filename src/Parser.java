import java.io.*;
import java.util.ArrayList;
public class Parser {
    private String strBuff = "";
    private int nodeCounter = 0;
    private int previousId = 0;
    ArrayList<Node> nodes = new ArrayList<Node>();
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.parseStr("text.txt", "parsed.txt");
    }
    private void parseStr(String fileName, String newFileName) {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF8"));
            BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newFileName), "UTF8"));
            String str;

            while (((str = fileReader.readLine()) != null) && (str != "")) {
                str = str.replaceAll(" ", "");
                strBuff = strBuff + str;

            }
            str = strBuff.replaceAll("“", "\"");
            str = strBuff.replaceAll("”", "\"");
            str = strBuff.replaceAll("}}", "}");
            if (!str.contains("{")) {
                nodes.add(getLastNodesValues(str, previousId));
            } else {
                if (checkLastNode(str)) {
                    nodes.add(getLastNodesValues(str, previousId));
                }
                else {
                    nodes.add(getIntermediateNodeValues(str, previousId));
                }
            }
            for (int i = 0; i < nodes.size(); i++) {
                fileWriter.newLine();
                fileWriter.write(nodes.get(i).writeNode());
            }

            fileWriter.close();
            fileReader.close();
            }
        catch (IOException e) {
            System.out.println("An error occured while processing the file");
            throw new RuntimeException(e);
        }
    }
    private void checkNodeNameFormat(String str){
        boolean isContainWrongSymbols = str.contains("\\W");
        if (isContainWrongSymbols) {
            System.out.println("Неверный формат данных");
        }
    }
    private String getName(String str) {
        if (str.startsWith("{")|| str.startsWith("}")) {
            str = str.substring(1, str.indexOf('='));
        }
        else {
            str = str.substring(0, str.indexOf('='));
        }
    return str;
    }
    private String cutName(String str) {
        return str.substring(str.indexOf('=')+ 1 );
    }
    private String cutNode(String str) {
        return str.substring(str.indexOf('{') + 1);
    }
    private void checkValueFormat (String str) {
        boolean isContainQuotes = getValue(str).contains("\"");
        boolean isContainNewLine = getValue(str).contains("\n");
        if (isContainQuotes || isContainNewLine) {
            System.out.println("Неверный формат данных");
            System.exit(0);
        }
    }
    private String getValue (String str) {
        str=str.substring(1);
        return str.substring(0, str.indexOf("\""));
    }
    private boolean checkLastNode(String str){
        if (str.startsWith("}")) {
            return false;
        }
        if (str.contains("{")) {
        return (str.contains("\"")&&(str.indexOf("\"")<str.indexOf("{"))); }
        else return str.contains("\"");
    }
    private boolean checkIntermediateNode(String str){
        if (str.contains("\"")) {
            return (str.contains("{")&&(str.indexOf("\"")>str.indexOf("{"))); }
        else return str.contains("\"");
    }
    private boolean checkEnd(String str){
        return str.equals("");
    }
    private String cutLastNode(String str) {
        return str.substring(str.indexOf('\"', str.indexOf('\"')+ 1 ) + 1);
    }
    private String cutClosedBracket(String str) {
        str = str.substring(1);
        return str;
    }
    private Node getLastNodesValues(String str, int previousNodeId){
        LastNode node = new LastNode();
        if (str.startsWith("}")) {
            return node;
        }
        node.setPreviousId(previousNodeId);
        node.setThisId(nodeCounter++);
        checkNodeNameFormat(getName(str));
        node.setName(getName(str));
        str = cutName(str);
        checkValueFormat(str);
        node.setValue(getValue(str));
        String strBuff = cutLastNode(str);
        if (checkLastNode(strBuff)) {
            nodes.add(getLastNodesValues(strBuff, node.getPreviousId()));
        }
        else if (checkIntermediateNode(strBuff)) {
            if (strBuff.startsWith("{")) {
                strBuff = strBuff.substring(1);
            }
            nodes.add(getIntermediateNodeValues(strBuff, node.getPreviousId()));
        }
        else if (strBuff.startsWith("}")) {
            cutClosedBracket(strBuff);
            return node;
        }
        else if (!checkEnd(strBuff)) {

            System.out.println("Неверный формат данных");
            System.exit(0);
        }
        return node;
    }
    private Node getIntermediateNodeValues(String str, int previousNodeId) {
        IntermediateNode node = new IntermediateNode();

        node.setPreviousId(previousNodeId);
        node.setThisId(nodeCounter++);
        checkNodeNameFormat(getName(str));
        node.setName(getName(str));
        str = cutName(str);
        String strBuff = cutNode(str);
        if (checkLastNode(strBuff)) {
            nodes.add(getLastNodesValues(strBuff, node.getThisId()));
        } else if (checkIntermediateNode(strBuff)) {
            if (strBuff.startsWith("{")) {
                strBuff = strBuff.substring(1);
            }
            nodes.add(getIntermediateNodeValues(strBuff, node.getThisId()));
        }


        else if (!checkEnd(strBuff)) {
            System.out.println("Неверный формат данных");
            System.exit(0);
        }
        return node;
    }


}
