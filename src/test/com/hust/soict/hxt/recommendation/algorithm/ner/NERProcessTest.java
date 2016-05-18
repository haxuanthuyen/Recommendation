package com.hust.soict.hxt.recommendation.algorithm.ner;

import com.hust.soict.hxt.recommendation.services.GlobalResourceInit;
import com.hust.soict.hxt.recommendation.utils.FileIO;
import junit.framework.TestCase;

import java.util.HashMap;
import java.util.List;

/**
 * Created by thuyenhx on 3/21/16.
 */
public class NERProcessTest extends TestCase {

    public void testTokenize() throws Exception {
        GlobalResourceInit.initModelMap();
        NERProcess nerProcess = NERProcess.getInstance();
//        nerProcess.fileTokenize("data/dientucongnghe/test.output", 9);
        nerProcess.fileTokenizeLabel("data/test.raw", 2);
//        System.out.println(nerProcess.tokenizer("Ốp lưng+bút cảm ứng+viền camera iPhone 6/6 Plus", 9));
//        nerProcess.tokenizeWithLabel(" Đồng hồ thông minh Smartwatch Smart U8 (Trắng) ", 9);

//        nerProcess.buildTitleTrain("Ổ/B-PN cắm/I-PN điện/I-PN đa/B-PROP năng/I-PROP Nakagami/B-BR 4/B-PROP lỗ/I-PROP");
    }

    public void testTokenize2() throws Exception {

        GlobalResourceInit.initModelMap();
        NERProcess nerProcess = NERProcess.getInstance();
        String tokens = nerProcess.tokenizer("3 quần lửng bo gấu 100% cotton xinh xắn cho bé", 4);
//        HashMap<String, String> map = nerProcess.tokenizeWithLabel("Quần vải dáng Baggy sành điệu cho nàng", 2);
        System.out.println(tokens);
    }

    public void testTokenize3() throws Exception {
        GlobalResourceInit.initModelMap();
        NERProcess nerProcess = NERProcess.getInstance();
        List<String> lstData = FileIO.readFile("data/test.raw");
        for (String title : lstData) {
            String text = nerProcess.tokenizer(title, 2);
            System.out.println(text);
            System.out.println();
        }
    }
}