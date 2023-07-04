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
            //�α��� �ȵǾ������� �α��� �������� �̵��ϰ� alertâ ����
            request.setAttribute("msg","�α��� �� �̿����ּ���.");
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
