package hello.advanced.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepositoryV0 orderRepository;

    public void order(String itemId) {
        orderRepository.save(itemId);
    }
}
