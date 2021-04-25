package lottery;

import java.io.FileOutputStream;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Collector {
    private FileOutputStream out;

    public void start() throws IOException {
        for (int i = 1; i <= getPage(); i++) {
            getData(i);
        }
        if (out != null) {
            out.close();
        }
    }

    private int getPage() throws IOException {
        String address = "http://kaijiang.zhcw.com/zhcw/inc/3d/3d_wqhg.jsp?pageNum=" + 1;
        Document document = Jsoup.connect(address).get();
        Element body = document.body();
        Elements trs = body.getElementsByTag("tr");

        for (Element tr : trs) {
            Elements tds = tr.getElementsByTag("td");
            for (Element td : tds) {
                Elements pgs = td.getElementsByClass("pg");
                for (Element pg : pgs) {
                    Elements strongs = pg.getElementsByTag("strong");
                    if (strongs.size() > 0) {
                        return Integer.parseInt(strongs.get(0).text());
                    }
                }
            }
        }

        return 0;
    }

    private void getData(int page) {
        String address = "http://kaijiang.zhcw.com/zhcw/inc/3d/3d_wqhg.jsp?pageNum=" + page;
        Document document;
        Element body;
        Elements trs;
        try {
            document = Jsoup.connect(address).get();

            body = document.body();
            trs = body.getElementsByTag("tr");

            for (Element tr : trs) {
                Elements tds = tr.getElementsByTag("td");
                for (Element td : tds) {
                    StringBuilder number = new StringBuilder();
                    Elements ems = td.getElementsByTag("em");
                    for (Element em : ems) {
                        number.append(em.text());
                    }
                    if (ems.size() > 0) {
                        saveToFile(number.toString());
                    }
                }
            }
        } catch (IOException e) {
            System.out.println(page + "   " + e.getMessage());
            getData(page);
            e.printStackTrace();
        }
    }

    private void saveToFile(String number) throws IOException {
        if (out == null) {
            out = new FileOutputStream("records");
        }
        out.write((number + '\n').getBytes());
    }
}
