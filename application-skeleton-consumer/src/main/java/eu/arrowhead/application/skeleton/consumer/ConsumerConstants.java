package eu.arrowhead.application.skeleton.consumer;

public class ConsumerConstants {

    //=================================================================================================
    // members

    public static final String BASE_PACKAGE = "ai.aitia";

    public static final String INTERFACE_SECURE = "HTTP-SECURE-JSON";
    public static final String INTERFACE_INSECURE = "HTTP-INSECURE-JSON";
    public static final String HTTP_METHOD = "http-method";

    public static final String CREATE_SERVICE_DEFINITION = "create-coonsumer";
    public static final String GET_SERVICE_DEFINITION = "get-consumer";

    //TODO
    public static final String REQUEST_PARAM_KEY_BRAND = "request-param-brand";
    public static final String REQUEST_PARAM_KEY_COLOR = "request-param-color";

    //=================================================================================================
    // assistant methods

    //-------------------------------------------------------------------------------------------------
    private ConsumerConstants() {
        throw new UnsupportedOperationException();
    }
}
