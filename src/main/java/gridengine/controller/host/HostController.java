package gridengine.controller.host;

import gridengine.entity.Listing;
import gridengine.entity.host.Host;
import gridengine.service.HostOperationProviderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is responsible for host management operations.
 */
@RestController
@RequestMapping("/hosts")
@RequiredArgsConstructor
public class HostController {

    private final HostOperationProviderService hostOperationProviderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "List host nodes",
    notes = "Returns list that contains information about hosts",
    produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Listing<Host> listHosts() {
        return hostOperationProviderService.hostListing();
    }
}
