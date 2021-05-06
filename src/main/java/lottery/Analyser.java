package lottery;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Analyser {
    private List<Integer> infos = new ArrayList<>();
    private List<Integer> groupNums = new ArrayList<>();
    private List<RecordInfo> records = new ArrayList<>();

    private boolean mDirect;
    private boolean mOrder;

    private FileOutputStream out;

    public void start() throws IOException {
        start(true, false);
        start(true, true);
        start(false, false);
        start(false, true);
    }

    public void start(boolean direct, boolean order) throws IOException {
        this.mDirect = direct;
        this.mOrder = order;
        infos.clear();
        groupNums.clear();
        records.clear();
        initData();
        analyseData();
    }

    private void initData() throws IOException {
        for (int i = 0; i < 1000; i++) {
            int num = makeNum(i);
            if (!groupNums.contains(num)) {
                groupNums.add(num);
                records.add(new RecordInfo(num));
            }
        }
        BufferedReader reader = new BufferedReader(new FileReader(new File("records")));
        String line = "";
        while ((line = reader.readLine()) != null) {
            infos.add(0, Integer.parseInt(line));
        }
        reader.close();
    }

    private int makeNum(int i) {
        if (mDirect)
            return i;
        List<Integer> data = new ArrayList<>();
        int hun = i / 100;
        int ten = i % 100 / 10;
        int single = i % 10;
        data.add(hun);
        data.add(ten);
        data.add(single);
        Collections.sort(data, (o1, o2) -> o1 - o2);

        int num = data.get(0) * (100) + data.get(1) * 10 + data.get(2);
        return num;
    }

    private void analyseData() throws IOException {
        for (Integer number : infos) {
            for (RecordInfo record : records) {
                number = makeGroup(number);
                if (number == record.getNumber()) {
                    record.setMiss(0);
                } else {
                    record.setMiss(record.getMiss() + 1);
                }
            }
        }

        if (mOrder) {
            Collections.sort(records, (o1, o2) -> o2.getMiss() - o1.getMiss());
        }

        for (RecordInfo record : records) {
            saveToFile(record);
        }
        if (out != null) {
            out.close();
            out = null;
        }
    }

    private void saveToFile(RecordInfo record) throws IOException {
        String file = "";
        if (out == null) {
            if (mDirect) {
                if (mOrder) {
                    file = "direct_order_by_miss";
                } else {
                    file = "direct_order_by_number";
                }
            } else {
                if (mOrder) {
                    file = "group_order_by_miss";
                } else {
                    file = "group_order_by_number";
                }
            }
            out = new FileOutputStream(file);
        }
        out.write((String.format("%03d", record.getNumber()) + "   " + record.getMiss() + '\n').getBytes());
    }
}
