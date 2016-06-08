package sample.coordinator;

import sample.model.Plane;

import java.util.ArrayList;

public class PlaneCoordinator {
    private static ArrayList<Plane> planes = new ArrayList<>();

    public static ArrayList<Plane> getPlanes() {
        return planes;
    }

    public static void setPlanes(ArrayList<Plane> planess) {
        PlaneCoordinator.planes = planess;
    }

    public static void addPlane(Plane plane){
        planes.add(plane);
    }

    public static void removePlane(Plane plane){
        getPlanes().remove(plane);
    }
}
