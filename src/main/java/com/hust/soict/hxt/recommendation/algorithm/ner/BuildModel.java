package com.hust.soict.hxt.recommendation.algorithm.ner;

import com.hust.soict.hxt.recommendation.bo.ModelData;
import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.util.Timing;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class BuildModel {

    private CRFClassifier<CoreLabel> crf;
    private SeqClassifierFlags flags;
    private Properties prop;

    public static void main(String[] args) throws Exception {
        BuildModel buildModel = new BuildModel();
//        buildModel.train();
//        buildModel.testModelOld("4");
        buildModel.testModel("4");
    }

    public BuildModel() {
       init();
    }

    public void init() {
        prop = StringUtils.propFileToProperties("data/austen-test.prop.bck");
        flags = new SeqClassifierFlags(prop);
        crf =  chooseCRFClassifier(flags);
    }

    private static CRFClassifier<CoreLabel> chooseCRFClassifier(SeqClassifierFlags flags) {
        CRFClassifier<CoreLabel> crf; // initialized in if/else
        if (flags.useFloat) {
            crf = new CRFClassifierFloat<CoreLabel>(flags);
        } else if (flags.nonLinearCRF) {
            crf = new CRFClassifierNonlinear<CoreLabel>(flags);
        } else if (flags.numLopExpert > 1) {
            crf = new CRFClassifierWithLOP<CoreLabel>(flags);
        } else if (flags.priorType.equals("DROPOUT")) {
            crf = new CRFClassifierWithDropout<CoreLabel>(flags);
        } else if (flags.useNoisyLabel) {
            crf = new CRFClassifierNoisyLabel<CoreLabel>(flags);
        } else {
            crf = new CRFClassifier<CoreLabel>(flags);
        }
        return crf;
    }

    public void testModelOld(String type) throws Exception {
        prop = StringUtils.propFileToProperties("data/austen-test.prop.bck");
        flags = new SeqClassifierFlags(prop);
        crf =  chooseCRFClassifier(flags);

        String loadPath = getPathModelOld(type);
        String testFile = getPathTest(type);
        init();

        if (loadPath != null) {
            crf.loadClassifierNoExceptions(loadPath, prop);
        }

        if (testFile != null) {
            // todo: Change testFile to call testFiles with a singleton list
            DocumentReaderAndWriter<CoreLabel> readerAndWriter = crf.defaultReaderAndWriter();
           crf.classifyAndWriteAnswers(testFile, readerAndWriter, true);
        }
    }

    public List<ModelData> testModel(String type) throws IOException {
        prop = StringUtils.propFileToProperties("data/austen-test.prop");
        flags = new SeqClassifierFlags(prop);
        crf =  chooseCRFClassifier(flags);

        List<ModelData> lstData = new ArrayList<>();
        String loadPath = getPathModel(type);
        String testFile = getPathTest(type);

        if (loadPath != null) {
            crf.loadClassifierNoExceptions(loadPath, prop);
        }

        if (testFile != null) {
            // todo: Change testFile to call testFiles with a singleton list
            DocumentReaderAndWriter<CoreLabel> readerAndWriter = crf.defaultReaderAndWriter();
            String res =  crf.classifyAndReturnAnswers(testFile, readerAndWriter, true);
            System.out.printf(res);
            String[] array = res.split("\n");
            for (int i = 1; i < array.length; i++) {
                String[] arrayValue = array[i].split("\t");
                ModelData data = new ModelData(arrayValue[0],arrayValue[1],arrayValue[2],arrayValue[3],arrayValue[4],arrayValue[5],arrayValue[6]);
                lstData.add(data);
            }
        }
        return lstData;
    }

    public void train() {

        String serializeTo = flags.serializeTo;
        String serializeToText = flags.serializeToText;

        if (crf.flags.trainFile != null) {
            Timing timing = new Timing();
            crf.train();
            timing.done("CRFClassifier training");
        }

        if (serializeTo != null) {
            crf.serializeClassifier(serializeTo);
        }

        if (crf.flags.serializeWeightsTo != null) {
            crf.serializeWeights(crf.flags.serializeWeightsTo);
        }

        if (crf.flags.serializeFeatureIndexTo != null) {
            crf.serializeFeatureIndex(crf.flags.serializeFeatureIndexTo);
        }

        if (serializeToText != null) {
            crf.serializeTextClassifier(serializeToText);
        }
    }

    public String getPathModel(String type) {
        String path = "";
        switch (type) {
            case "1":
                path = "data/amthucnhahang/model.amthucnhahang.gz";
                break;
            case "2":
                path = "data/thoitrangnu/model.thoitrangnu.gz";
                break;
            case "3":
                path = "data/spavalamdep/model.spavalamdep.gz";
                break;
            case "4":
                path = "data/mevabe/model.mevabe.gz";
                break;
            case "6":
                path = "data/thucpham/model.thucpham.gz";
                break;
            case "7":
                path = "data/phukienmypham/model.phukienmypham.gz";
                break;
            case "8":
                path = "data/thoitrangnam/model.thoitrangnam.gz";
                break;
            case "9":
                path = "data/dientucongnghe/model.dientucongnghe.gz";
                break;
            case "12":
                path = "data/giadungnoithat/model.giadungnoithat.gz";
                break;
            default:
        }
        return path;
    }

    public String getPathModelOld(String type) {
        String path = "";
        switch (type) {
            case "1":
                path = "data/amthucnhahang/model2.amthucnhahang.gz";
                break;
            case "2":
                path = "data/thoitrangnu/model2.thoitrangnu.gz";
                break;
            case "3":
                path = "data/spavalamdep/model2.spavalamdep.gz";
                break;
            case "4":
                path = "data/mevabe/model2.mevabe.gz";
                break;
            case "6":
                path = "data/thucpham/model2.thucpham.gz";
                break;
            case "7":
                path = "data/phukienmypham/model2.phukienmypham.gz";
                break;
            case "8":
                path = "data/thoitrangnam/model2.thoitrangnam.gz";
                break;
            case "9":
                path = "data/dientucongnghe/model2.dientucongnghe.gz";
                break;
            case "12":
                path = "data/giadungnoithat/model.giadungnoithat.gz";
                break;
            default:
        }
        return path;
    }

    public String getPathTest(String type) {
        String path = "";
        switch (type) {
            case "1":
                path = "data/amthucnhahang/test.amthucnhahang";
                break;
            case "2":
                path = "data/thoitrangnu/test.thoitrangnu";
                break;
            case "3":
                path = "data/spavalamdep/test.spavalamdep";
                break;
            case "4":
                path = "data/mevabe/test.mevabe";
                break;
            case "6":
                path = "data/thucpham/test.thucpham";
                break;
            case "7":
                path = "data/phukienmypham/test.phukienmypham";
                break;
            case "8":
                path = "data/thoitrangnam/test.thoitrangnam";
                break;
            case "9":
                path = "data/dientucongnghe/test.dientucongnghe";
                break;
            case "12":
                path = "data/giadungnoithat/test.giadungnoithat";
                break;
            default:
        }
        return path;
    }
}
