package nlp;

import com.hankcs.hanlp.mining.word2vec.Word2VecTrainer;
import com.hankcs.hanlp.mining.word2vec.WordVectorModel;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author: liumch
 * @create: 2019/7/23 11:15
 **/

public class Word2VecTest {
    private final static String PATH = "D:\\data\\word2vec";
    private final static String modelName = PATH + File.separator + "model.bin";
    private static List<List<String>> readData(String filename) {
        List<List<String>> list = new ArrayList<>(1000);
        try(InputStream in = new FileInputStream(filename); BufferedReader reader = new BufferedReader(new InputStreamReader(in))){
                String line;
                while( null != (line = reader.readLine())){
                    list.add(Arrays.asList(line.split("\\s+")));
                }
        }catch (IOException ex){
            ex.printStackTrace();
        }
        return list;

    }
    public static void main(String[] args) throws Exception{
        Word2VecTrainer trainer = new Word2VecTrainer();
        trainer.setMinVocabFrequency(1);
        trainer.setLayerSize(400);
//        WordVectorModel model = trainer.train(PATH + File.separator + "swresult_withoutnature.txt" ,PATH + File.separator + "model.bin");
        WordVectorModel model = trainer.train(PATH + File.separator + "keywords.txt" ,PATH + File.separator + "keywords.model");

//        WordVectorModel model = new WordVectorModel(modelName);
//        float similarity = model.similarity("中国","北京");
//        float similarity = model.similarity("中国","北京");
//        System.out.println("similarity:" + similarity);
//        similarity = model.similarity("中国","江泽民");
//        System.out.println("similarity:" + similarity);
        List<Map.Entry<String, Float>> nearest = model.nearest("品牌多", 10);
        nearest.stream().forEach(x-> System.out.println(x.getKey() + "--" + x.getValue()));

        float similarity = model.similarity("总体不错","总体好");
        System.out.println("similarity:" + similarity);

        List<Map.Entry<String, Float>> nearest1 = model.nearest("服务差", 10);
        nearest1.stream().forEach(x-> System.out.println(x.getKey() + "--" + x.getValue()));


                //hanlp
//        WordVectorModel model = trainer.train(PATH + File.separator + "swresult_withoutnatureithoutnature.txt" ,PATH + File.separator + "model.bin");
//        List<Map.Entry<String, Float>> analogy = model.analogy("中国", "北京", "东京", 5);
//        analogy.forEach(x-> System.out.println("k:" + x.getKey() + ",value:" + x.getValue()));
    }
}
