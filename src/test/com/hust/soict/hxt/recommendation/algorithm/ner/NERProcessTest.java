package com.hust.soict.hxt.recommendation.algorithm.ner;

import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import junit.framework.TestCase;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class NERProcessTest extends TestCase {

    public void testTokenize() throws Exception {
        GlobalResourceInit.initModelMap();
        NERProcess nerProcess = NERProcess.getInstance();
//        nerProcess.fileTokenize("data/dientucongnghe/test.output", 9);
        nerProcess.fileTokenizeLabel("data/dientucongnghe/test.raw", 9);
//        System.out.println(nerProcess.tokenizer("Ốp lưng+bút cảm ứng+viền camera iPhone 6/6 Plus", 9));
//        nerProcess.tokenizeWithLabel(" Đồng hồ thông minh Smartwatch Smart U8 (Trắng) ", 9);

//        nerProcess.buildTitleTrain("Ổ/B-PN cắm/I-PN điện/I-PN đa/B-PROP năng/I-PROP Nakagami/B-BR 4/B-PROP lỗ/I-PROP");
    }
}