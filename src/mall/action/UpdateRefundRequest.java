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

public class UpdateRefundRequest implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        OrderVO vo = new OrderVO();
        OrderDao orderDao = OrderDao.getInstance();
        String id=(String)request.getSession().getAttribute("id");
        String orderidseq = request.getParameter("orderidseq");
        System.out.println("orderidseq = " + orderidseq);
        String pname = request.getParameter("pname");
        String optionname = request.getParameter("optionname");

        vo.setId(id);
        vo.setOrderidseq(orderidseq);
        vo.setPname(pname);
        vo.setOptionname(optionname);
        
        orderDao.updateRefundRequest(vo);
        return "/mall/orderList.do";
    }
}
