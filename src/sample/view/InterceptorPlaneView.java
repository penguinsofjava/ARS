package sample.view;

import sample.model.Plane;
import sample.model.Radar;
import sample.task.InterceptorTrackTask;

import java.util.Timer;

public class InterceptorPlaneView extends PlaneView {
    private Plane target;

    public InterceptorPlaneView(Plane self, Plane target, Radar radar) {
        super(self);
        this.target = target;
        setPosition(radar.getPosition());
        setOpacity(1);
    }

    @Override
    public void startFlying() {
        if (flyTimer == null) {
            flyTimer = new Timer();
        }
        flyTimer.schedule(InterceptorTrackTask.with(target, this), 0, 100);
    }
}
