package ai.aitia.greenhouse.hardwaresystems;

public class HardwaresystemConstants {
    public static final String BASE_PACKAGE = "ai.aitia";

    public static final String GET_WATERING_SERVICE_ON_DEFINITION = "watering-on";
    public static final String GET_WATERING_SERVICE_OFF_DEFINITION = "watering-off";
    public static final String GET_LIGHTING_SERVICE_DEFINITION = "get-lighting";
    public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
    public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
    public static final String HTTP_METHOD = "http-method";
    public static final String WATERING_SYSTEM_ON_URI = "/on";
    public static final String WATERING_SYSTEM_OFF_URI= "/off";
    public static final String LIGHTING_SYSTEM_URI= "/adjustLight";



    private HardwaresystemConstants() {
        throw new UnsupportedOperationException();
    }
}
