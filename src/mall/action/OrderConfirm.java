package mall.action;

import board.action.CommandAction;
import mall.dao.OrderDao;
import mall.model.OrderVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OrderConfirm implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        OrderVO vo = new OrderVO();
        OrderDao orderDao = OrderDao.getInstance();
        String orderidseq = request.getParameter("orderidseq");
        String id=(String)request.getSession().getAttribute("id");
        String pname = request.getParameter("pname");
        String optionname = request.getParameter("optionname");

        vo.setId(id);
        vo.setOrderidseq(orderidseq);
        vo.setPname(pname);
        vo.setOptionname(optionname);

        orderDao.orderConfirm(vo);
        return "/mall/orderList.do";
    }
}
