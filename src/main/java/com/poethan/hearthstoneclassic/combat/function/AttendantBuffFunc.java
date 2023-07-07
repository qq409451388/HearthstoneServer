package com.poethan.hearthstoneclassic.combat.function;

import com.poethan.hearthstoneclassic.combat.combatunit.CombatUnitAttendant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttendantBuffFunc extends AbstractFunc {
    private int buffHealth;
    private int buffDamage;

    public void loadUnit(CombatUnitAttendant combatUnit) {
        this.setCombatUnit(combatUnit);
    }

    public void apply() {
        ((CombatUnitAttendant)this.getCombatUnit()).setBuffHealth(this.buffHealth);
        ((CombatUnitAttendant)this.getCombatUnit()).setBuffDamage(this.buffDamage);
    }
}
