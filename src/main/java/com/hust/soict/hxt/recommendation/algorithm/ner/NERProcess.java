package com.hust.soict.hxt.recommendation.algorithm.ner;

import com.hust.soict.hxt.recommendation.global.GlobalObject;
import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class NERProcess {

    public static void main(String[] args) {
        GlobalResourceInit.initModelMap();
        NERProcess nerProcess = new NERProcess();
        nerProcess.fileTokenize("data/dientucongnghe/test.raw", 9);
    }

    public NERProcess() {}

    private static Logger logger = LoggerFactory.getLogger(NERProcess.class);

    public void fileTokenize(String path, int catId) {
        AbstractSequenceClassifier<CoreLabel> classifier = GlobalObject.modelMap.get(catId);
        if (classifier != null) {
            List<String> lstData = FileIO.readFile(path);
            for (String line : lstData) {
                String label = classifier.classifyToString(line);
                System.out.println(label);
            }
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

            List<List<CoreLabel>> classify = classifier.classify(title);
            List<CoreLabel> coreLabels = classify.get(0);

            String key = "";
            int index = 0;
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
                if (startLabels.contains(answer)) {
                    if (labelParent == "") {
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
