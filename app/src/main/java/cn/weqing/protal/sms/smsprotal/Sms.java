package cn.weqing.protal.sms.smsprotal;

/**
 * Created by weifeng on 2018/2/1.
 */

public class Sms {
    private String number;
    private String datetime;
    private String content;

    public Sms(String number, String datetime, String content) {
        this.number = number;
        this.datetime = datetime;
        this.content = content;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Sms{" +
                "number='" + number + '\'' +
                ", datetime='" + datetime + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
