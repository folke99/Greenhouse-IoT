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


    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("HEJSAN");
        getSoilMoisture();
    }
    public void getSoilMoisture() {
        logger.info("Orchestration request for " + ControllerConstants.GET_MOISTURE_SERVICE_DEFINITION + " service:");
        final ServiceQueryFormDTO serviceQueryForm = new ServiceQueryFormDTO.Builder(ControllerConstants.GET_MOISTURE_SERVICE_DEFINITION)
                .interfaces(getInterface())
                .build();

        final OrchestrationFormRequestDTO.Builder orchestrationFormBuilder = arrowheadService.getOrchestrationFormBuilder();
        final OrchestrationFormRequestDTO orchestrationFormRequest = orchestrationFormBuilder.requestedService(serviceQueryForm)
                .flag(OrchestrationFlags.Flag.MATCHMAKING, true)
                .flag(OrchestrationFlags.Flag.OVERRIDE_STORE, true)
                .build();

        printOut(orchestrationFormRequest);

        final OrchestrationResponseDTO orchestrationResponse = arrowheadService.proceedOrchestration(orchestrationFormRequest);

        logger.info("Orchestration response:");
        printOut(orchestrationResponse);

        if (orchestrationResponse == null) {
            logger.info("No orchestration response received");
        } else if (orchestrationResponse.getResponse().isEmpty()) {
            logger.info("No provider found during the orchestration");
        } else {
            final OrchestrationResultDTO orchestrationResult = orchestrationResponse.getResponse().get(0);
            validateOrchestrationResult(orchestrationResult, ControllerConstants.GET_MOISTURE_SERVICE_DEFINITION);

            logger.info("Get soil moisture");
            final String token = orchestrationResult.getAuthorizationTokens() == null ? null : orchestrationResult.getAuthorizationTokens().get(getInterface());
            @SuppressWarnings("unchecked")
            final int moisture = arrowheadService.consumeServiceHTTP(Integer.class, HttpMethod.GET,
                    orchestrationResult.getProvider().getAddress(), orchestrationResult.getProvider().getPort(), orchestrationResult.getServiceUri(),
                    getInterface(), token, null, new String[0]);
            printOut(moisture);
        }
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

