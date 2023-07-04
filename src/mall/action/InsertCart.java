package mall.action;

import board.action.CommandAction;
import mall.dao.CartDao;
import mall.model.CartVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertCart implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        CartVO vo=new CartVO();
        CartDao cartDao=CartDao.getInstance();
      String id=(String)request.getSession().getAttribute("id");
        int pno = Integer.parseInt(request.getParameter("pno"));
        int price = Integer.parseInt(request.getParameter("price"));
        String pname = request.getParameter("pname");
        String pimage = request.getParameter("pimage");
        int cartcount = Integer.parseInt(request.getParameter("cartcount"));
        String optionname = request.getParameter("optionname");
        

        int remain = Integer.parseInt(request.getParameter("remain"));
        System.out.println("remain = " + remain);

        vo.setId(id);
        vo.setPno(pno);
        vo.setPrice(price);
        vo.setPname(pname);
        vo.setPimage(pimage);
        vo.setCartcount(cartcount);
        vo.setOptionname(optionname);
        vo.setRemain(remain);

        String data = "";
        System.out.println("intsert Cart Action");
        int count= cartDao.CheckOption(vo);
        if(count==1){
            cartDao.updateCartCount2(vo);
        }else{
            cartDao.insertCart(vo);
        }
        return "/mall/productDetail.do?pno="+pno;
    }

}
