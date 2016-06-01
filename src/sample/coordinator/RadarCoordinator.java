package sample.coordinator;

import sample.model.Radar;

import java.util.ArrayList;

public class RadarCoordinator {
    private static ArrayList<Radar> radars = new ArrayList<>();

    public static ArrayList<Radar> getRadars() {
        return radars;
    }

    public static void addRadar(Radar radar) {
        radars.add(radar);
        saveRadars();
    }

    public static void removeRadar(Radar radar) {
        radars.remove(radar);
        saveRadars();
    }

    public static void loadRadars() {
        // TODO: Read from file
    }

    public static void saveRadars() {
        // TODO: Save to file
    }
}
