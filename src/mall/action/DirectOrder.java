package mall.action;

import board.action.CommandAction;
import mall.dao.CartDao;
import mall.dao.ProductDao;
import mall.model.CartVO;
import mall.model.OrderVO;
import mall.model.ProductVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DirectOrder implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id = (String) request.getSession().getAttribute("id");
        ProductDao productDao = ProductDao.getInstance();
        HttpSession session = request.getSession();
        if (id == null) {
            session.setAttribute("msg", "로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }

        int pno = Integer.parseInt(request.getParameter("pno"));
        int price = Integer.parseInt(request.getParameter("price"));
        String pname = request.getParameter("pname");
        pname=new String(pname.getBytes("8859_1"),"UTF-8");
        String pimage = request.getParameter("pimage");
        String optionname = request.getParameter("optionname");
        optionname=new String(optionname.getBytes("8859_1"),"UTF-8");
        System.out.println("pname = " + pname+", optionname="+optionname);
        int remain = Integer.parseInt(request.getParameter("remain"));
        int count = Integer.parseInt(request.getParameter("count"));

        String name = (String) session.getAttribute("name");
        String phone = (String) session.getAttribute("phone");
        String addr = (String) session.getAttribute("addr") + " " + (String) session.getAttribute("detailaddr");
        OrderVO vo = new OrderVO();
        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("phone = " + phone);
        System.out.println("addr = " + addr);

        vo.setId(id);
        vo.setName(name);
        vo.setPhone(phone);
        vo.setAddr(addr);
        String[] optionArray = optionname.split("-");
        System.out.println("optionArray = " + Arrays.toString(optionArray));
        ProductVO pVo = new ProductVO();
        if (optionArray.length < 2) {
            String optionvalue1 = optionArray[0];
            System.out.println("optionvalue1 = " + optionvalue1);
            pVo.setOptionvalue1(optionvalue1);
            pVo.setOptionvalue2("nooption2");

        } else {
            String optionvalue1 = optionArray[0];
            String optionvalue2 = optionArray[1];
            optionvalue2=new String(optionvalue2.getBytes("8859_1"),"UTF-8");
            pVo.setOptionvalue1(optionvalue1);
            pVo.setOptionvalue2(optionvalue2);
        }
        //pno와 optionvalue1,2로 상품 정보를 가져온다.

        request.setAttribute("pno", pno);
        request.setAttribute("price", price);
        session.setAttribute("pname", pname);
        request.setAttribute("pimage", pimage);
        session.setAttribute("optionname", optionname);
        request.setAttribute("remain", remain);
        request.setAttribute("count", count);

        return "/mall/DirectOrder.jsp";
    }
}
