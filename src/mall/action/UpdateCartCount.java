package mall.action;

import board.action.CommandAction;
import mall.dao.CartDao;
import mall.model.CartVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateCartCount implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id=(String)request.getSession().getAttribute("id");
        CartDao cartDao= CartDao.getInstance();
        CartVO vo=new CartVO();
        if(id==null){
            request.getSession().setAttribute("msg", "로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }
        int cartid=Integer.parseInt(request.getParameter("cartid"));
        int cartcount=Integer.parseInt(request.getParameter("cartcount"));
        vo.setCartid(cartid);
        vo.setCartcount(cartcount);
        cartDao.updateCartCount(vo);

        return "/mall/listCart.do";
    }
}
