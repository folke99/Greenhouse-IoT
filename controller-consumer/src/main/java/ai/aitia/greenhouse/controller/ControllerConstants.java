package ai.aitia.greenhouse.controller;

public class ControllerConstants {
    public static final String BASE_PACKAGE = "ai.aitia";
    public static final String GET_MOISTURE_SERVICE_DEFINITION = "get-moisture";
    public static final String GET_WATERING_SERVICE_ON_DEFINITION = "watering-on";
    public static final String GET_WATERING_SERVICE_OFF_DEFINITION = "watering-off";
    public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
    public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
    public static final String HTTP_METHOD = "http-method";
    public static final String SOIL_SENSOR_URI = "/";
    public static final String WATERING_SYSTEM_ON_URI = "/";
    public static final String WATERING_SYSTEM_OFF_URI = "/";


    private ControllerConstants() {
        throw new UnsupportedOperationException();
    }
}
