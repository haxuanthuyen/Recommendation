package com.hust.soict.hxt.recommendation.algorithm.ner;

import edu.stanford.nlp.ie.crf.*;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.sequences.DocumentReaderAndWriter;
import edu.stanford.nlp.sequences.SeqClassifierFlags;
import edu.stanford.nlp.util.StringUtils;
import edu.stanford.nlp.util.Timing;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by thuyenhx on 1/24/16.
 */
public class BuildModel {

    private CRFClassifier<CoreLabel> crf;
    private SeqClassifierFlags flags;
    private Properties prop;

    public static void main(String[] args) throws IOException {
        BuildModel buildModel = new BuildModel();
        buildModel.train();
//        buildModel.testModel();
    }

    public BuildModel() {
        prop = StringUtils.propFileToProperties("data/austen-test.prop");
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

    public void testModel() throws IOException {

        String loadPath = flags.serializeTo;
        String testFile = flags.testFile;

        if (loadPath != null) {
            crf.loadClassifierNoExceptions(loadPath, prop);
        }

        if (testFile != null) {
            // todo: Change testFile to call testFiles with a singleton list
            DocumentReaderAndWriter<CoreLabel> readerAndWriter = crf.defaultReaderAndWriter();
            crf.classifyAndWriteAnswers(testFile, readerAndWriter, true);
//            crf.printProbs(testFile, readerAndWriter);
//            crf.getCliqueTrees(testFile,readerAndWriter);
        }
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
}
