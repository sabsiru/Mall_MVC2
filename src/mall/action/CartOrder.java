package mall.action;

import board.action.CommandAction;
import mall.dao.CartDao;
import mall.model.CartVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartOrder implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id=(String)request.getSession().getAttribute("id");
        CartDao cartDao=CartDao.getInstance();
        if(id==null){
            request.getSession().setAttribute("msg", "로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }
        String[]check=request.getParameterValues("selectedItems");
        for(int i=0;i<check.length;i++){
        System.out.println("check = " + check[i]);
        }
        Map<String,Object>cartIdArray=new HashMap<String,Object>();
        List<CartVO> cartList=null;
        cartIdArray.put("cartIdArray",check);
        System.out.println("cartIdArray2 = " + cartIdArray);
        cartList=cartDao.selectCart(cartIdArray);
        request.setAttribute("cartList",cartList);

        return "/mall/Order.jsp";
    }
}
