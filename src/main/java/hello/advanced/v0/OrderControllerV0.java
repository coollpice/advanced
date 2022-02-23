package hello.advanced.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV0 {

    private final OrderService orderService;

    @GetMapping("/v0/request")
    public String request(String itemId) {
        orderService.order(itemId);
        return "ok";
    }
}
