package tr.gediz.ars.task;

import tr.gediz.ars.bus.Bus;
import tr.gediz.ars.bus.event.PlaneInterceptedEvent;
import tr.gediz.ars.controller.MainController;
import tr.gediz.ars.model.*;
import tr.gediz.ars.view.MissileView;

import java.util.TimerTask;

public class TrackTask extends TimerTask {
    private Radar radar;
    private Plane target;
    private MissileView self;

    private TrackTask(Radar radar, Plane target, MissileView self) {
        this.radar = radar;
        this.target = target;
        this.self = self;
    }

    public static TrackTask with(Radar radar, Plane target, MissileView self) {
        return new TrackTask(radar, target, self);
    }

    private void trackPlaneWithMissile(Plane plane, Radar radar) {
        if (calculateDistance() > 10) {
            int missileX = self.getPosition().getX();
            int missileY = self.getPosition().getY();
            int speed = MissileView.SPEED;
            self.setX(missileX < target.getPosition().getX() ? missileX + speed : missileX - speed);
            self.setY(missileY < target.getPosition().getY() ? missileY + speed : missileY - speed);
        } else {
            self.setX(plane.getPosition().getX());
            self.setY(plane.getPosition().getY());
            Bus.get().post(new PlaneInterceptedEvent(radar, target));
            MainController.getInstance().removeMissile(self);
            cancel();
        }
    }

    @SuppressWarnings("Duplicates")
    private int calculateDistance() {
        int missileX = self.getPosition().getX();
        int missileY = self.getPosition().getY();
        int targetX = target.getPosition().getX();
        int targetY = target.getPosition().getY();
        return (int) Math.sqrt(Math.pow(missileX - targetX, 2) + Math.pow(missileY - targetY, 2));
    }

    @Override
    public void run() {
        trackPlaneWithMissile(target, radar);
    }
}
