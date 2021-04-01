package demo.app.cryptocurrencies.model;

import java.util.List;

public class Coins {
    private String symbol;

    private String marketCap;

    private String color;

    private String change;

    private String btcPrice;

    private String listedAt;

    private String uuid;

    private List<String> sparkline;

    private String _24hVolume;

    private String tier;

    private String coinrankingUrl;

    private String price;

    private String name;

    private String lowVolume;

    private String rank;

    private String iconUrl;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getBtcPrice() {
        return btcPrice;
    }

    public void setBtcPrice(String btcPrice) {
        this.btcPrice = btcPrice;
    }

    public String getListedAt() {
        return listedAt;
    }

    public void setListedAt(String listedAt) {
        this.listedAt = listedAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<String> getSparkline() {
        return sparkline;
    }

    public void setSparkline(List<String> sparkline) {
        this.sparkline = sparkline;
    }

    public String get24hVolume() {
        return _24hVolume;
    }

    public void set24hVolume(String _24hVolume) {
        this._24hVolume = _24hVolume;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getCoinrankingUrl() {
        return coinrankingUrl;
    }

    public void setCoinrankingUrl(String coinrankingUrl) {
        this.coinrankingUrl = coinrankingUrl;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLowVolume() {
        return lowVolume;
    }

    public void setLowVolume(String lowVolume) {
        this.lowVolume = lowVolume;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
