package mall.action;

import board.action.CommandAction;
import mall.dao.CartDao;
import mall.dao.OrderDao;
import mall.dao.ProductDao;
import mall.model.CartVO;
import mall.model.OrderVO;
import mall.model.ProductVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class DeleteCart implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        CartVO vo = new CartVO();
        CartDao cartDao = CartDao.getInstance();
        int cartid = Integer.parseInt(request.getParameter("cartid"));
        System.out.println("cartid = " + cartid);
        cartDao.deleteCart(cartid);
        return "/mall/listCart.do";
    }
}
