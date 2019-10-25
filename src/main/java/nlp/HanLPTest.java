package nlp;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLSentence;
import com.hankcs.hanlp.corpus.dependency.CoNll.CoNLLWord;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;

public class HanLPTest {


    public static void main(String[] args){
        String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n" +
                "算法可以宽泛的分为三类，\n" +
                "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n" +
                "二，有限的非确定算法，这类算法在有限的时间内终止。然而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n" +
                "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";
        List<String> sentenceList = HanLP.extractSummary(document, 3);
        List<Term> segment1 = HanLP.segment(document);

//
//        System.out.println(sentenceList);
//
//        // 命名实体识别
//        document = "这家店的鱼汤超赞，非常好吃，下次还会再来";
        document = "商场虽然很大，可是商场的卫生和服务太差，交通也很不方便";
        String[] docs = new String[]{document};

        CoNLLSentence dependency = HanLP.parseDependency(document);
        for(CoNLLWord term :dependency.getWordArrayWithRoot()){
            System.out.println(term);
        }
        Segment segment = HanLP.newSegment().enableNameRecognize(true);
        for (String sentence : docs)
        {
            List<Term> termList = segment.seg(sentence);
            System.out.println(termList);
        }


        String str = "阿尔法：狼伴归途" ;
        // 增加新词,中间按照'\t'隔开




    }
}
