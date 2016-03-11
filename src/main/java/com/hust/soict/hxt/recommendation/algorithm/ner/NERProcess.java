package com.hust.soict.hxt.recommendation.algorithm.ner;

import com.hust.soict.hxt.recommendation.global.GlobalObject;
import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import com.hust.soict.hxt.recommendation.utils.StringNormalize;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class NERProcess {

    public static void main(String[] args) throws Exception {
        GlobalResourceInit.initModelMap();
        NERProcess nerProcess = new NERProcess();
//        nerProcess.fileTokenize("data/dientucongnghe/test.output", 9);
        nerProcess.fileTokenizeLabel("data/thoitrangnu/test.raw", 2);
//        nerProcess.tokenize("Ổ cắm điện đa năng Nakagami 4 lỗ", 9);

//        nerProcess.parseTitle("Ổ/B-PN cắm/I-PN điện/I-PN đa/B-PROP năng/I-PROP Nakagami/B-BR 4/B-PROP lỗ/I-PROP");
    }

    public NERProcess() {}

    private static Logger logger = LoggerFactory.getLogger(NERProcess.class);

    public String parseTitle(String title) {
        String[] arrayValue = title.split("\\s+");
        String tmp = "";
        String label = "";
        StringBuilder result = new StringBuilder();
        for (String tk : arrayValue) {
            if (tk.contains("/B-")) {
                if (!tmp.equals("")) {
                    result.append(tmp + "/" + label + " ");
                    tmp = "";
                    label = "";
                }
                tmp = tk.split("/")[0];
                label = tk.split("/")[1];
            }else if (tk.contains("/I-") && !tmp.equals("")) {
                tmp += "_" + tk.split("/")[0];
            }else if (tk.contains("/O")) {
                if (!tmp.equals("")) {
                    result.append(tmp + "/" + label + " ");
                    tmp = "";
                    label = "";
                }
                result.append(tk + " ");
            }
        }
        if (!tmp.equals("")) {
            result.append(tmp + "/" + label + " ");
        }

//        System.out.println(result.toString());
        return result.toString();
    }

    public void fileTokenizeLabel(String path, int catId) {
        Map<String, String> result = new HashMap<>();
        StringBuilder rs = new StringBuilder();
        try {
            AbstractSequenceClassifier<CoreLabel> classifier = GlobalObject.modelMap.get(catId);
            if (classifier != null) {
                List<String> lstData = FileIO.readFile(path);
                for (String line : lstData) {
//                String label = classifier.classifyToString(line);
//                System.out.println(label);
                    String title = StringNormalize.normalize(line);
                    TreeMap<String, String> datas = tokenize(title, catId);
                    for (Map.Entry entry : datas.entrySet()) {
                        String key = entry.getKey().toString().split("_")[1];
                        String value = entry.getValue().toString();
                        if (!result.containsKey(key)) {
                            result.put(key, value);
                        }else {
                            String v = result.get(key);
                            result.put(key, v + "," + value);
                        }

                        rs.append(value + " ");
                    }
                    rs.append("\n");
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(result.size());
        System.out.println(rs.toString());
    }

    public void fileTokenize(String path, int catId) {
        StringBuilder content = new StringBuilder();
        try {
            AbstractSequenceClassifier<CoreLabel> classifier = GlobalObject.modelMap.get(catId);
            if (classifier != null) {
                List<String> lstData = FileIO.readFile(path);
                for (String line : lstData) {
                    String title = StringNormalize.normalize(line);
                    String label = classifier.classifyToString(title);
//                    String parseTitle = parseTitle(label);
//                    String[] arrayLabel = parseTitle.split("\\s+");
                    String[] arrayLabel = label.split("\\s+");
                    for (String s : arrayLabel) {
                        String lb = "";
                        if (s.contains("//")) {
                            String tmp = s.split("/")[2];
                            lb = "/\t" + tmp;
                        }else {
                            lb = s.replace("/", "\t");
                        }
                        content.append(lb + "\n");
                    }
                    content.append("\r\n");
                }
            }
            FileIO.writeFile("data/dientucongnghe/export.test", content.toString());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TreeMap<String, String> tokenize(String title, int catId) throws Exception {

        TreeMap<String, String> data = new TreeMap<>();
        try {
            AbstractSequenceClassifier<CoreLabel> classifier = GlobalObject.modelMap.get(catId);
            if (classifier == null) return null;

            List<String> startLabels = Arrays.asList("B-PN", "B-BR", "B-STYLE", "B-OB", "B-FUNC", "B-PROP",
                    "B-FUNC-OB", "B-PN-OB", "B-SEV", "B-SEV-OB", "B-LOC", "B-TYPE",
                    "B-NAME", "B-TIME", "B-HPROP", "B-TPROP");

            System.out.println(classifier.classifyToString(title));
            List<List<CoreLabel>> classify = classifier.classify(title);
            List<CoreLabel> coreLabels = classify.get(0);

            String key = "";
            int index = 0;
            int indexPn = -1;
            int indexChild = 0;

            for (CoreLabel coreLabel : coreLabels) {
                String word = coreLabel.word();
                String answer = coreLabel.get(CoreAnnotations.AnswerAnnotation.class);
                int pos = 0;
                if (answer.contains("-")) {
                    pos = 2;
                }
                String label = answer.substring(pos);
                String labelParent = "";
                if (label.contains("-")) {
                    labelParent = answer.substring(pos, pos + label.lastIndexOf("-"));
                }

                if (label.equals("FUNC") && indexPn + 1 == index) {
                    answer = "I-PN";
                }

                if (startLabels.contains(answer)) {
                    if (labelParent == "") {
                        if (label.equals("PN")) {
                            indexPn = index;
                        }
                        key = index + "_" + label;
                        index++;
                    } else {
                        key = (index - 1) + "_" + indexChild + "_" + label;
                        indexChild++;
                    }

                    data.put(key, word);
                } else if (!answer.equals("O")) {
                    if (data.containsKey(key)) {
                        data.put(key, data.get(key) + "_" + word);
                    }
                }
            }

        } catch (Exception e) {
            logger.error("model map size " + GlobalObject.modelMap.size());
            logger.error("error tokenize title " + title + " - cat_id " + catId, e);
        }
        return data;
    }
}
