package sample.coordinator;

import sample.model.Plane;

import java.util.ArrayList;

public class PlaneCoordinator {
    private static ArrayList<Plane> planes = new ArrayList<>();

    public static ArrayList<Plane> getPlaness() {
        return planes;
    }

    public static void setPlaness(ArrayList<Plane> planess) {
        PlaneCoordinator.planes = planess;
    }

    public static void addPlane(Plane plane){
        planes.add(plane);
    }

    public static void addPlane(){
    //TODO: Add random plane
    }

    public static void removePlane(Plane plane){
        getPlaness().remove(plane);
    }
}
