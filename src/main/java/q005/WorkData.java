package q005;

/**
 * 作業時間管理クラス
 * 自由に修正してかまいません
 */
public class WorkData {
    /**
     * 社員番号
     */
    private String number;

    /**
     * 部署
     */
    private String department;

    /**
     * 役職
     */
    private String position;

    /**
     * Pコード
     */
    private String pCode;

    /**
     * 作業時間(分)
     */
    private int workTime;

    public static WorkData of(String data) {
        String[] dataInfo = data.split(",");
        WorkData workData = new WorkData();

        workData.setNumber(dataInfo[0]);
        workData.setDepartment(dataInfo[1]);
        workData.setPosition(dataInfo[2]);
        workData.setPCode(dataInfo[3]);
        workData.setWorkTime(Integer.parseInt(dataInfo[4]));
        return workData;
    }

    public static WorkData positionOf(String position, Integer time) {
        WorkData workData = new WorkData();

        workData.setPosition(position);
        workData.setWorkTime(time);
        return workData;
    }

    public static WorkData pCodeOf(String pCode, Integer time) {
        WorkData workData = new WorkData();

        workData.setPCode(pCode);
        workData.setWorkTime(time);
        return workData;
    }

    public static WorkData numberOf(String number, Integer time) {
        WorkData workData = new WorkData();

        workData.setNumber(number);
        workData.setWorkTime(time);
        return workData;
    }

    // WorkDataの各セッター
    public void setNumber(String number) {
        this.number = number;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPCode(String pCode) {
        this.pCode = pCode;
    }

    public void setWorkTime(int workTime) {
        this.workTime = workTime;
    }

    // WorkDataの各ゲッター
    public String getNumber() {
        return this.number;
    }

    public String getDepartment() {
        return this.department;
    }

    public String getPosition() {
        return this.position;
    }

    public String getPCode() {
        return this.pCode;
    }

    public int getWorkTime() {
        return this.workTime;
    }
}
