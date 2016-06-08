package sample.coordinator;

import sample.model.Position;
import sample.model.Radar;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class RadarCoordinator {
    private static ArrayList<Radar> radars = new ArrayList<>();

    // file
    private Scanner input;
    private Formatter output;
    private String path;
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

    public Scanner getInput() {
        return input;
    }

    public void setInput(Scanner input) {
        this.input = input;
    }

    public Formatter getOutput() {
        return output;
    }

    public void setOutput(Formatter output) {
        this.output = output;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public void openFile() {
        try {
            input = new Scanner(new File(path));
        } catch (SecurityException e) {
            System.out.println("You do not have write access to this file!");
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found!");
            System.exit(1);
        }
    }

    public void readRecords() {
        radars.clear();
        Radar rdr;
        try {
            while (input.hasNext()) {

                int id = input.nextInt();
                int scanAngle = input.nextInt();
                int radius = input.nextInt();
                int scanInterval = input.nextInt();
                int x = input.nextInt();
                int y = input.nextInt();

                rdr = new Radar(id, scanAngle,radius,scanInterval, new Position(x,y));


                radars.add(rdr);

            }
        } catch (NoSuchElementException nse) {
            System.out.println("No such element exception occured");
        } catch (IllegalStateException ise) {
            System.out.println("IllegalStateException");
        }

    }
}