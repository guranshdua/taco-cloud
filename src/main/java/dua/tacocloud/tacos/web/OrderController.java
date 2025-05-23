package dua.tacocloud.tacos.web;

import dua.tacocloud.tacos.TacoOrder;
import dua.tacocloud.tacos.data.OrderRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {
    @GetMapping("/current")
    public String orderForm()
    {
        return "orderForm";
    }
    private final OrderRepository orderRepo;
    @Autowired
    public OrderController(OrderRepository orderRepo)
    {
        this.orderRepo=orderRepo;
    }

    @PostMapping
    public String processOrder(@Valid TacoOrder order, Errors errors, SessionStatus sessionStatus)
    {
        if(errors.hasErrors())
        {
            log.warn(errors.toString());
            return "orderForm";
        }
        orderRepo.save(order);
        log.info("Order submitted : {}",order);
        sessionStatus.setComplete();

        return "redirect:/";
    }
}
