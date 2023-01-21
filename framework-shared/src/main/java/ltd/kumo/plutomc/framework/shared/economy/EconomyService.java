package ltd.kumo.plutomc.framework.shared.economy;

import ltd.kumo.plutomc.framework.shared.Service;
import ltd.kumo.plutomc.framework.shared.player.Player;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EconomyService<T extends Player<P>, P> extends Service<EconomyService<T, P>> {

    /**
     * 设置某个玩家的经济
     *
     * @param p       玩家
     * @param balance 经济
     */
    void setBalance(T p, double balance);

    /**
     * 获取某个玩家的经济
     *
     * @param p 玩家
     * @return 经济
     */
    double getBalance(T p);

    /**
     * 将某个玩家的经济添加一定的值
     *
     * @param p       玩家
     * @param balance 值
     */
    void addBalance(T p, double balance);

    /**
     * 将某个玩家的经济删去一定的值
     *
     * @param p       玩家
     * @param balance 值
     */
    void subtractBalance(T p, double balance);

    /**
     * 将某个玩家的经济乘以一定的倍率
     *
     * @param p     玩家
     * @param times 倍率
     */
    void multiplyBalance(T p, double times);

    /**
     * 将某个玩家的经济除以一定的倍率
     *
     * @param p     玩家
     * @param times 倍率
     */
    void divideBalance(T p, double times);

    /**
     * 清除某一个玩家的经济
     *
     * @param p 玩家
     */
    void clearBalance(T p);

    /**
     * 列出所有人的经济，不建议使用，可能会导致内存溢出
     *
     * @return 所有人的经济
     */
    Set<Map.Entry<T, Double>> listBalances();

    /**
     * 按照经济排名寻找数据
     *
     * @param startIndex 开始的索引值，从0开始，必须大于等于0
     * @param amount     要检索的数量，至少等于1
     * @return 按照经济排行寻找到的玩家
     */
    List<Map.Entry<T, Double>> rank(int startIndex, int amount);

}
