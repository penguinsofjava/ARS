package sample.model;

public class Plane {
    private Type type;

    public enum Type {
        FRIENDLY("friendly"), HOSTILE("hostile"), UNKNOWN("unknown");

        private String value;

        Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }

        public Type fromString(String type) {
            switch (type) {
                case "friendly":
                    return FRIENDLY;
                case "hostile":
                    return HOSTILE;
                default:
                    return UNKNOWN;
            }
        }
    }
}
