package com.poethan.hearthstoneclassic.domain;

import com.poethan.hearthstoneclassic.constants.CombatUnitConstants;
import com.poethan.jear.jdbc.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Getter
@Setter
@ToString
public class CardDO extends BaseDO<Long> {
    /**
     * 卡牌名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 卡牌描述
     */
    @Column(name = "description")
    private String description;

    /**
     * 卡牌特色描述
     */
    @Column(name = "flavor_text")
    private String flavorText;

    /**
     * 画师姓名
     */
    @Column(name = "artistname")
    private String artistName;

    /**
     * 卡牌唯一id
     */
    @Column(name = "card_uniqid")
    private String cardUniqId;

    /**
     * 卡牌拓展包
     * @link CardExtendConst
     */
    @Column(name = "card_extend")
    private String cardExtend;

    /**
     * 卡牌类型
     * @see com.poethan.hearthstoneclassic.constants.CombatUnitConstants
     */
    @Column(name = "card_type")
    private String cardType;

    /**
     * 卡牌水晶耗费
     * range 1 - 10
     */
    @Column(name = "card_cost")
    private Integer cardCost;

    /**
     * 生命
     */
    @Column(name = "health")
    private Integer health;

    /**
     * 攻击力
     */
    @Column(name = "damage")
    private Integer damage;

    /**
     * 卡牌图片资源路径
     */
    @Column(name = "card_img")
    private String cardImageUrl;

    /**
     * 特殊能力 json
     * @link CardSpecialAbilityConst
     */
    @Column(name = "special_ability")
    private String specialAbility;

    /**
     * 特性
     * @link CardCharacteristicConst
     */
    @Column(name = "characteristic")
    private String characteristic;

    /**
     * 稀有度
     * @see com.poethan.hearthstoneclassic.constants.CardRarityConstants
     */
    @Column(name = "rarity")
    private String rarity;

    /**
     * 所属种族
     * @see com.poethan.hearthstoneclassic.constants.CardRaceConstants
     */
    @Column(name = "race")
    private String race;

    /**
     * 所属职业
     * @see com.poethan.hearthstoneclassic.constants.CareerConstants
     */
    @Column(name = "class")
    private String career;

    public boolean typeAttendant() {
        return CombatUnitConstants.TYPE_ATTENDANT.equals(this.cardType);
    }

    public boolean typeMagic() {
        return CombatUnitConstants.TYPE_MAGIC.equals(this.cardType);
    }

    public boolean typeWeapon() {
        return CombatUnitConstants.TYPE_WEAPON.equals(this.cardType);
    }

    public boolean typeBoss() {
        return CombatUnitConstants.TYPE_BOSS.equals(this.cardType);
    }
}
