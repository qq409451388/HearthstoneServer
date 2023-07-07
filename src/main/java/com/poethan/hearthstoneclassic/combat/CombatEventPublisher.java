package com.poethan.hearthstoneclassic.combat;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class CombatEventPublisher implements ApplicationEventPublisher {
    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void publishEvent(@NonNull ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    @Override
    public void publishEvent(@NonNull Object event) {
        applicationContext.publishEvent(event);
    }
}
