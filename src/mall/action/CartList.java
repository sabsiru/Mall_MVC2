package mall.action;

import board.action.CommandAction;
import mall.dao.CartDao;
import mall.model.CartVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class CartList implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id=(String)request.getSession().getAttribute("id");
        CartDao cartDao=CartDao.getInstance();
        
        if (id==null){
            //로그인 안되어있으면 로그인 페이지로 이동하고 alert창 띄우기
            request.setAttribute("msg","로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }
        int count= CartDao.getInstance().selectCartCount(id);
        List<CartVO>cartList=null;
        if(count>0){
            cartList=cartDao.selectCartList(id);
        }

        request.setAttribute("cartList",cartList);
        return "/mall/CartList.jsp";
    }
}
