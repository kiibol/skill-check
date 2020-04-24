package q003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * Q003 集計と並べ替え
 * <p>
 * 以下のデータファイルを読み込んで、出現する単語ごとに数をカウントし、アルファベット辞書順に並び変えて出力してください。
 * resources/q003/data.txt
 * 単語の条件は以下となります
 * - "I"以外は全て小文字で扱う（"My"と"my"は同じく"my"として扱う）
 * - 単数形と複数形のように少しでも文字列が異れば別単語として扱う（"dream"と"dreams"は別単語）
 * - アポストロフィーやハイフン付の単語は1単語として扱う（"isn't"や"dead-end"）
 * <p>
 * 出力形式:単語=数
 * <p>
 * [出力イメージ]
 * （省略）
 * highest=1
 * I=3
 * if=2
 * ignorance=1
 * （省略）
 * <p>
 * 参考
 * http://eikaiwa.dmm.com/blog/4690/
 */
public class Q003 {
    public static void main(String[] args) throws IOException {
        // テキストファイルを文字列で取得
        String dataInfo = openDataFile();

        // 文字列を空白毎にリストに詰める
        List<String> data = Arrays.asList(dataInfo.split(" "));

        // 単語をリスト化する
        List<String> splitList = makeList(data);

        // 単語が出てきた回数をカウントする
        Map<String, Integer> responseMap = countString(splitList, data);

        // 表示
        responseMap.forEach((key, value) -> System.out.println(key + "=" + value));
    }

    /**
     * データファイルを開く
     * resources/q003/data.txt
     */
    private static String openDataFile() throws IOException {
        InputStream inputStream = Q003.class.getResourceAsStream("data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String str;
        String dataText = "";
        while ((str = br.readLine()) != null) {
            dataText = dataText.isEmpty() ? str : (dataText + " " + str);
        }
        br.close();
        return dataText;
    }

    /**
     * 出現する単語ごとに抜き出して
     * 昇順に並び替えるメソッド
     */
    private static List<String> makeList(List<String> data) {
//        List<String> list = new ArrayList<>();
//
//        data.stream()
//                .map(word -> word.replaceFirst("[.,!;]$", ""))
//                .filter(word -> !list.contains(word.toLowerCase()))
//                .forEach(word -> list.add(word.toLowerCase()));
//        Collections.sort(list);

        // 単語を小文字にし、末尾記号を削除したものを重複を排除してリスト化
        List<String> list = data.stream()
                .map(word -> word.toLowerCase().replaceFirst("[.,!;]$", ""))
                .distinct()
                .collect(Collectors.toList());
        // 英単語昇順ソート
        Collections.sort(list);

        int iIndex = list.indexOf("i");
        list.set(iIndex, list.get(iIndex).toUpperCase());
        return list;
    }

    /**
     * 文字の出現回数を計算する
     *
     * @param wordList 単語が1つ入ったリスト（Iのみ大文字、そのほかは原文がアッパーケースでも小文字）
     * @param data     テキストファイルの文を空白毎にリストに詰めたもの
     */
    private static Map<String, Integer> countString(List<String> wordList, List<String> data) {
        // (単語, 出現回数)のマップを作成
        Map<String, Integer> responseMap = new LinkedHashMap<>();

        wordList.forEach(item -> {
            // 文字列カウント用のの変数
            AtomicInteger count = new AtomicInteger();

            data.forEach(word -> {
                //　index計算用のカウンタ変数と計算式
                AtomicInteger dataIndex = new AtomicInteger();
                dataIndex.getAndIncrement();

                if (item.equals(word.toLowerCase().replaceFirst("[.,!;]$", "")) ||
                        item.equals(word)) {
                    count.getAndIncrement();            // 単語が含まれている数を加算
                    data.remove(dataIndex);       // 一度使用された元データの単語は処理速度改善のために削除
                }
            });
            responseMap.put(item, count.get());
        });
        return responseMap;
    }
}
// 完成までの時間: xx時間 xx分