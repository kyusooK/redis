package kyusootest.infra;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import kyusootest.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/orders")
@Transactional
public class OrderController {

    @Autowired
    OrderRepository orderRepository;

    @RequestMapping(
        value = "/orders/placeorder",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Order placeorder(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody PlaceorderCommand placeorderCommand
    ) throws Exception {
        System.out.println("##### /order/placeorder  called #####");
        Order order = new Order();
        order.placeorder(placeorderCommand);
        orderRepository.save(order);
        return order;
    }

    @PostMapping("/login")
    public ResponseEntity<User> session(HttpSession httpSession, Guest guest) {
        User user = loginUser(guest);

        /** Login 진행 후, 결과정보 Session에 Set */
        httpSession.setAttribute("name", user.getName());
        httpSession.setAttribute("registerNumber", user.getRegisterNumber());
        /** 이하 생략 */

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/userInfo")
    public ResponseEntity<User> getSession(HttpSession session) {
        // Start of Selection
        User user = new User();

        String name = (String) session.getAttribute("name");
        String registerNumber = (String) session.getAttribute("registerNumber");

        user.setName(name);
        user.setRegisterNumber(registerNumber);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
//>>> Clean Arch / Inbound Adaptor
