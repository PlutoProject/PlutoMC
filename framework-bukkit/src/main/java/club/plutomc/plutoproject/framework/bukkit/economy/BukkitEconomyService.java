package club.plutomc.plutoproject.framework.bukkit.economy;

import club.plutomc.plutoproject.framework.bukkit.player.BukkitPlayer;
import club.plutomc.plutoproject.framework.shared.economy.EconomyService;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitEconomyService implements EconomyService<BukkitPlayer, Player> {

    @Override
    public void setBalance(BukkitPlayer p, double balance) {

    }

    @Override
    public double getBalance(BukkitPlayer p) {
        return 0;
    }

    @Override
    public void addBalance(BukkitPlayer p, double balance) {

    }

    @Override
    public void subtractBalance(BukkitPlayer p, double balance) {

    }

    @Override
    public void multiplyBalance(BukkitPlayer p, double times) {

    }

    @Override
    public void divideBalance(BukkitPlayer p, double times) {

    }

    @Override
    public void clearBalance(BukkitPlayer p) {

    }

    @Override
    public Set<Map.Entry<BukkitPlayer, Double>> listBalances() {
        return null;
    }

    @Override
    public List<Map.Entry<BukkitPlayer, Double>> rank(int startIndex, int amount) {
        return null;
    }

}
