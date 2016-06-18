package tr.gediz.ars.bus;

import com.google.common.eventbus.EventBus;

public class Bus {
    private static final EventBus instance = new EventBus();

    public static EventBus get() {
        return instance;
    }
}
