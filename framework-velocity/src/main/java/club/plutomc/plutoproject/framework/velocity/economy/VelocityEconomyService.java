package club.plutomc.plutoproject.framework.velocity.economy;

import com.velocitypowered.api.proxy.Player;
import club.plutomc.plutoproject.framework.shared.economy.EconomyService;
import club.plutomc.plutoproject.framework.velocity.player.VelocityPlayer;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class VelocityEconomyService implements EconomyService<VelocityPlayer, Player> {
    @Override
    public void setBalance(VelocityPlayer p, double balance) {

    }

    @Override
    public double getBalance(VelocityPlayer p) {
        return 0;
    }

    @Override
    public void addBalance(VelocityPlayer p, double balance) {

    }

    @Override
    public void subtractBalance(VelocityPlayer p, double balance) {

    }

    @Override
    public void multiplyBalance(VelocityPlayer p, double times) {

    }

    @Override
    public void divideBalance(VelocityPlayer p, double times) {

    }

    @Override
    public void clearBalance(VelocityPlayer p) {

    }

    @Override
    public Set<Map.Entry<VelocityPlayer, Double>> listBalances() {
        return null;
    }

    @Override
    public List<Map.Entry<VelocityPlayer, Double>> rank(int startIndex, int amount) {
        return null;
    }
}
