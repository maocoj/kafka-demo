package stock;

import java.io.Serializable;

/**
 * Created by maom3 on 2018/1/11.
 */
public class StockQuotationInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stockCode;
    private String stockName;
    private long tradeTime;
    private float preClosePrice;
    private float openPrivce;
    private float currentPrice;
    private float highPrice;
    private float lowPrice;

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public long getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(long tradeTime) {
        this.tradeTime = tradeTime;
    }

    public float getPreClosePrice() {
        return preClosePrice;
    }

    public void setPreClosePrice(float preClosePrice) {
        this.preClosePrice = preClosePrice;
    }

    public float getOpenPrivce() {
        return openPrivce;
    }

    public void setOpenPrivce(float openPrivce) {
        this.openPrivce = openPrivce;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }

    public float getHighPrice() {
        return highPrice;
    }

    public void setHighPrice(float highPrice) {
        this.highPrice = highPrice;
    }

    public float getLowPrice() {
        return lowPrice;
    }

    public void setLowPrice(float lowPrice) {
        this.lowPrice = lowPrice;
    }

    @Override
    public String toString() {
        return "StockQuotationInfo{" +
                "stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", tradeTime=" + tradeTime +
                ", preClosePrice=" + preClosePrice +
                ", openPrivce=" + openPrivce +
                ", currentPrice=" + currentPrice +
                ", highPrice=" + highPrice +
                ", lowPrice=" + lowPrice +
                '}';
    }
}
