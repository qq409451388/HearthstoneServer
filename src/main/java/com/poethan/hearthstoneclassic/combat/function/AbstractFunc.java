package com.poethan.hearthstoneclassic.combat.function;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.poethan.hearthstoneclassic.combat.combatunit.AbstractCombatUnit;
import com.poethan.jear.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXISTING_PROPERTY,
        property = "funcName",
        visible = true
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = AttendantBuffFunc.class, name = "AttendantBuff"),
})
@Getter
@Setter
abstract public class AbstractFunc extends BaseDTO {
    private String funcName;
    @JsonIgnore
    private AbstractCombatUnit combatUnit;
    public AbstractFunc loadUnit(AbstractCombatUnit combatUnit) {
        this.combatUnit = combatUnit;
        return this;
    }

    abstract public void apply();
}
