package demo.app.cryptocurrencies.model;

import java.util.List;

public class GetCoins {
    private Stats stats;

    private List<Coins> coins;

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public List<Coins> getCoins() {
        return coins;
    }

    public void setCoins(List<Coins> coins) {
        this.coins = coins;
    }
}
