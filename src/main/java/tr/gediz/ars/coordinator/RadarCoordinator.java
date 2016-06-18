package tr.gediz.ars.coordinator;

import tr.gediz.ars.model.Radar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class RadarCoordinator {
    private static final File STORE_FILE = new File("files/radar.txt");

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
        try {
            Scanner input = new Scanner(STORE_FILE);

            radars.clear();
            while (input.hasNext()) {
                Radar radar = new Radar();
                radar.setId(input.nextInt());
                radar.setName(input.next());
                radar.setScanAngle(input.nextInt());
                radar.setRadius(input.nextInt());
                radar.setScanInterval(input.nextInt());
                radar.setPosition(input.nextInt(), input.nextInt());
                radars.add(radar);
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            System.out.println("You do not have write access to this file!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File Not Found!");
        } catch (NoSuchElementException nse) {
            System.out.println("No such element exception occurred");
        }
    }

    public static void saveRadars() {
        // TODO: Save to file
    }
}