package q005;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Q005 データクラスと様々な集計
 * <p>
 * 以下のファイルを読み込んで、WorkDataクラスのインスタンスを作成してください。
 * resources/q005/data.txt
 * (先頭行はタイトルなので読み取りをスキップする)
 * <p>
 * 読み込んだデータを以下で集計して出力してください。
 * (1) 役職別の合計作業時間
 * (2) Pコード別の合計作業時間
 * (3) 社員番号別の合計作業時間
 * 上記項目内での出力順は問いません。
 * <p>
 * 作業時間は "xx時間xx分" の形式にしてください。
 * また、WorkDataクラスは自由に修正してください。
 * <p>
 * [出力イメージ]
 * 部長: xx時間xx分
 * 課長: xx時間xx分
 * 一般: xx時間xx分
 * Z-7-31100: xx時間xx分
 * I-7-31100: xx時間xx分
 * T-7-30002: xx時間xx分
 * （省略）
 * 194033: xx時間xx分
 * 195052: xx時間xx分
 * 195066: xx時間xx分
 * （省略）
 */
public class Q005 {

    public static void main(String[] args) throws IOException {
        List<String> dataText = openDataFile();

        List<WorkData> workDataList = dataText.stream().map(WorkData::of).collect(Collectors.toList());

        calcPositionTime(workDataList);
        calcPCodeTime(workDataList);
        calcNumberTime(workDataList);
    }

    /**
     * データファイルを開く
     * resources/q005/data.txt
     */
    private static List<String> openDataFile() throws IOException {
        InputStream inputStream = Q005.class.getResourceAsStream("data.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String str;
        List<String> dataText = new ArrayList<>();
        while ((str = br.readLine()) != null) {
            dataText.add(str);
        }
        br.close();
        dataText.remove(0);
        return dataText;
    }

    /**
     * 役職別の作業時間割り出しメソッド
     */
    private static void calcPositionTime(List<WorkData> workDataList) {
        // 役職名のリスト
        List<String> roleList = workDataList.stream()
                .map(WorkData::getPosition)
                .distinct()
                .collect(Collectors.toList());

        // positionとworkTimeのみセットしたWprkDataインスタンスをpositionの種類分生成
        List<WorkData> positionData = roleList.stream()
                .map(item -> WorkData.positionOf(item, 0))
                .collect(Collectors.toList());

        workDataList.stream().forEach(item -> {
            for (int i = 0; i < positionData.size(); i++) {
                WorkData data = positionData.get(i);
                if (data.getPosition().equals(item.getPosition())) {
                    data.setWorkTime(data.getWorkTime() + item.getWorkTime());
                }
            }
        });

        System.out.println("・役職nの作業時間の合計-----------");
        positionData.stream().forEach(item -> {
            System.out.println(item.getPosition() + ": " + changeTimePattern(item));
        });

        // 最初に実装したコード　使いづらかったので改良
//        Map<String, Integer> roleMap = new LinkedHashMap<>();
//        List<Integer> workTime = new ArrayList<>(Arrays.asList(0, 0, 0));
//        for (WorkData data : workDataList) {
//            String roleName = data.getPosition();
//            switch (roleName) {
//                case "部長":
//                    workTime.set(0, workTime.get(0) + data.getWorkTime());
//                    break;
//                case "課長":
//                    workTime.set(1, workTime.get(1) + data.getWorkTime());
//                    break;
//                case "一般":
//                    workTime.set(2, workTime.get(2) + data.getWorkTime());
//                    break;
//                default:
//                    return null;
//            }
//        }
//
//        roleMap.put("部長", workTime.get(0));
//        roleMap.put("課長", workTime.get(1));
//        roleMap.put("一般", workTime.get(2));
    }

    /**
     * 役職別の作業時間割り出しメソッド
     */
    private static void calcPCodeTime(List<WorkData> workDataList) {
        List<String> pCodeList = workDataList.stream()
                .map(WorkData::getPCode)
                .distinct()
                .collect(Collectors.toList());

        Collections.sort(pCodeList);
        Collections.reverse(pCodeList);

        // positionとworkTimeのみセットしたWprkDataインスタンスをpositionの種類分生成
        List<WorkData> pCodeData = pCodeList.stream()
                .map(item -> WorkData.pCodeOf(item, 0))
                .collect(Collectors.toList());

        workDataList.stream().forEach(item -> {
            for (int i = 0; i < pCodeData.size(); i++) {
                WorkData data = pCodeData.get(i);
                if (data.getPCode().equals(item.getPCode())) {
                    data.setWorkTime(data.getWorkTime() + item.getWorkTime());
                }
            }
        });

        System.out.println("・Pコードnの作業時間の合計-----------");
        pCodeData.stream().forEach(item -> {
            System.out.println(item.getPCode() + ": " + changeTimePattern(item));
        });
    }

    /**
     * 社員番号別の作業時間割り出しメソッド
     */
    private static void calcNumberTime(List<WorkData> workDataList) {
        List<String> numberList = workDataList.stream()
                .map(WorkData::getNumber)
                .distinct()
                .collect(Collectors.toList());

        Collections.sort(numberList);

        // positionとworkTimeのみセットしたWprkDataインスタンスをpositionの種類分生成
        List<WorkData> numberData = numberList.stream()
                .map(item -> WorkData.numberOf(item, 0))
                .collect(Collectors.toList());

        workDataList.stream().forEach(item -> {
            for (int i = 0; i < numberData.size(); i++) {
                WorkData data = numberData.get(i);
                if (data.getNumber().equals(item.getNumber())) {
                    data.setWorkTime(data.getWorkTime() + item.getWorkTime());
                }
            }
        });

        System.out.println("・社員番号の作業時間の合計-----------");
        numberData.stream().forEach(item -> {
            System.out.println(item.getNumber() + ": " + changeTimePattern(item));
        });
    }

    /**
     * 作業時間を規定の表示形式に変換するメソッド
     *
     * @param data インスタンスのリスト
     */
    private static String changeTimePattern(WorkData data) {
        Integer hour = 0;
        Integer minute = 0;

        hour = data.getWorkTime() / 60;
        minute = data.getWorkTime() % 60;
        return hour + "時間" + minute + "分";
    }
}
// 完成までの時間: 2時間 10分