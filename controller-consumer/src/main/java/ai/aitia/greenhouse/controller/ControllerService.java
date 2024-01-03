package ai.aitia.greenhouse.controller;

import ai.aitia.arrowhead.application.library.ArrowheadService;
import eu.arrowhead.common.SSLProperties;
import eu.arrowhead.common.Utilities;
import eu.arrowhead.common.dto.shared.*;
import eu.arrowhead.common.exception.InvalidParameterException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ControllerService {

    @Autowired
    private ArrowheadService arrowheadService;

    @Autowired
    protected SSLProperties sslProperties;

    private final Logger logger = LogManager.getLogger(ControllerService.class);


    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
//        System.out.println("soil moisture: " + getSoilMoisture());
        setWatering();
        setLighting();
//        System.out.println("lighting: " + getLighting());
    }
    public int getSoilMoisture() {
        OrchestrationResponseDTO orchestrationResponse = orchestrationResponse(ControllerConstants.GET_MOISTURE_SERVICE_DEFINITION);
//        logger.info("Orchestration response:");
//        printOut(orchestrationResponse);

        if (orchestrationResponse == null) {
            logger.info("No orchestration response received");
        } else if (orchestrationResponse.getResponse().isEmpty()) {
            logger.info("No provider found during the orchestration");
        } else {
            final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
            validateOrchestrationResult(orchestrationResult, ControllerConstants.GET_MOISTURE_SERVICE_DEFINITION);

//            logger.info("Get soil moisture");
            final String token = orchestrationResult.getAuthorizationTokens() == null ? null : orchestrationResult.getAuthorizationTokens().get(getInterface());
            @SuppressWarnings("unchecked")
            final int moisture = arrowheadService.consumeServiceHTTP(Integer.class, HttpMethod.GET,
                    orchestrationResult.getProvider().getAddress(), orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(),
                    getInterface(), token, null, new String[0]);

            System.out.println("Moisture levels: " + moisture);
            return moisture;
        }
        return -1;
    }
    public void setWatering() {
        OrchestrationResponseDTO orchestrationResponseOn = orchestrationResponse(ControllerConstants.GET_WATERING_SERVICE_ON_DEFINITION);
        OrchestrationResponseDTO orchestrationResponseOff = orchestrationResponse(ControllerConstants.GET_WATERING_SERVICE_OFF_DEFINITION);

//        logger.info("Orchestration response:");
//        printOut(orchestrationResponseOn);
//        printOut(orchestrationResponseOff);

        if (orchestrationResponseOff == null || orchestrationResponseOn == null) {
            logger.info("No orchestration response received");
        } else if (orchestrationResponseOn.getResponse().isEmpty() || orchestrationResponseOff.getResponse().isEmpty()) {
            logger.info("No provider found during the orchestration");
        } else {
            final OrchestrationResultDTO orchestrationResultOn = orchestrationResponseOn.getResponse().get(0);
            validateOrchestrationResult(orchestrationResultOn, ControllerConstants.GET_WATERING_SERVICE_ON_DEFINITION);

            final OrchestrationResultDTO orchestrationResultOff = orchestrationResponseOff.getResponse().get(0);
            validateOrchestrationResult(orchestrationResultOff, ControllerConstants.GET_WATERING_SERVICE_OFF_DEFINITION);

//            logger.info("Get watering system");
            final String tokenOn = orchestrationResultOn.getAuthorizationTokens() == null ? null : orchestrationResultOn.getAuthorizationTokens().get(getInterface());
            final String tokenOff = orchestrationResultOff.getAuthorizationTokens() == null ? null : orchestrationResultOff.getAuthorizationTokens().get(getInterface());


            if (getSoilMoisture() < 50) {
                logger.info("Watering system on");
                arrowheadService.consumeServiceHTTP(String.class, HttpMethod.POST,
                        orchestrationResultOn.getProvider().getAddress(), orchestrationResultOn.getProvider().getPort(), orchestrationResultOn.getServiceUri(),
                        getInterface(), tokenOn, null,  new String[0]);
            }
            else {
                logger.info("Watering system off");
                arrowheadService.consumeServiceHTTP(String.class, HttpMethod.POST,
                        orchestrationResultOff.getProvider().getAddress(), orchestrationResultOff.getProvider().getPort(), orchestrationResultOff.getServiceUri(),
                        getInterface(), tokenOff, null, new String[0]);
            }
        }
    }

    public int getLighting(){
        OrchestrationResponseDTO orchestrationResponse = orchestrationResponse(ControllerConstants.GET_LIGHTINGSENSOR_SERVICE_DEFINITION);
//        logger.info("Orchestration response:");
//        printOut(orchestrationResponse);

        if (orchestrationResponse == null) {
            logger.info("No orchestration response received");
        } else if (orchestrationResponse.getResponse().isEmpty()) {
            logger.info("No provider found during the orchestration");
        } else {
            final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
            validateOrchestrationResult(orchestrationResult, ControllerConstants.GET_LIGHTINGSENSOR_SERVICE_DEFINITION);

//            logger.info("Get soil lighting");
            final String token = orchestrationResult.getAuthorizationTokens() == null ? null : orchestrationResult.getAuthorizationTokens().get(getInterface());
            @SuppressWarnings("unchecked")
            final int light = arrowheadService.consumeServiceHTTP(Integer.class, HttpMethod.GET,
                    orchestrationResult.getProvider().getAddress(), orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(),
                    getInterface(), token, null, new String[0]);

            System.out.println("Light levels: " + light);
            return light;
        }
        return -1;
    }
    public void setLighting(){
        OrchestrationResponseDTO orchestrationResponse = orchestrationResponse(ControllerConstants.GET_LIGHTING_SERVICE_DEFINITION);

//        logger.info("Orchestration response:");
//        printOut(orchestrationResponse);

        if (orchestrationResponse == null) {
            logger.info("No orchestration response received");
        } else if (orchestrationResponse.getResponse().isEmpty()) {
            logger.info("No provider found during the orchestration");
        } else {
            final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
            validateOrchestrationResult(orchestrationResult, ControllerConstants.GET_LIGHTING_SERVICE_DEFINITION);

            final String token = orchestrationResult.getAuthorizationTokens() == null ? null : orchestrationResult.getAuthorizationTokens().get(getInterface());

            if (getLighting() < 50) {
                logger.info("Lighting system on");
                arrowheadService.consumeServiceHTTP(String.class, HttpMethod.POST,
                        orchestrationResult.getProvider().getAddress(), orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(),
                        getInterface(), token, null,  new String[0]);
            }
            else {
                printOut("Lighting levels are good");
            }
        }
    }
    private OrchestrationResponseDTO orchestrationResponse(String service){
//        logger.info("Orchestration request for " + service + " service:");
        final ServiceQueryFormDTO serviceQueryForm = new ServiceQueryFormDTO.Builder(service)
                .interfaces(getInterface())
                .build();

        final OrchestrationFormRequestDTO.Builder orchestrationFormBuilder = arrowheadService.getOrchestrationFormBuilder();
        final OrchestrationFormRequestDTO orchestrationFormRequest = orchestrationFormBuilder.requestedService(serviceQueryForm)
                .build();

//        printOut(orchestrationFormRequest);

        final OrchestrationResponseDTO orchestrationResponse = arrowheadService.proceedOrchestration(orchestrationFormRequest);

//        logger.info("Orchestration response:");
//        printOut(orchestrationResponse);
        return orchestrationResponse;
    }
    private String getInterface() {
        return sslProperties.isSslEnabled() ? ControllerConstants.INTERFACE_SECURE : ControllerConstants.INTERFACE_INSECURE;
    }

    //-------------------------------------------------------------------------------------------------
    private void validateOrchestrationResult(final OrchestrationResultDTO orchestrationResult, final String serviceDefinitin) {
        if (!orchestrationResult.getService().getServiceDefinition().equalsIgnoreCase(serviceDefinitin)) {
            throw new InvalidParameterException("Requested and orchestrated service definition do not match");
        }

        boolean hasValidInterface = false;
        for (final ServiceInterfaceResponseDTO serviceInterface : orchestrationResult.getInterfaces()) {
            if (serviceInterface.getInterfaceName().equalsIgnoreCase(getInterface())) {
                hasValidInterface = true;
                break;
            }
        }
        if (!hasValidInterface) {
            throw new InvalidParameterException("Requested and orchestrated interface do not match");
        }
    }

    //-------------------------------------------------------------------------------------------------
    private void printOut(final Object object) {
        System.out.println(Utilities.toPrettyJson(Utilities.toJson(object)));
    }
}

