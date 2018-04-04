package saim.com.now.ModelNews;

/**
 * Created by NREL on 4/4/18.
 */

public class ModelTicker {
    String tickerName, tickerDate, tickerLink;

    public ModelTicker(String tickerName, String tickerDate, String tickerLink) {
        this.tickerName = tickerName;
        this.tickerDate = tickerDate;
        this.tickerLink = tickerLink;
    }

    public String getTickerName() {
        return tickerName;
    }

    public String getTickerDate() {
        return tickerDate;
    }

    public String getTickerLink() {
        return tickerLink;
    }
}
