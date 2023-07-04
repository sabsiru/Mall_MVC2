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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertDirectOrder implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        int pno = Integer.parseInt(request.getParameter("pno"));
        HttpSession session = request.getSession();
        ProductDao productDao = ProductDao.getInstance();
        OrderDao orderDao = OrderDao.getInstance();
        CartDao cartDao = CartDao.getInstance();
        String id = (String) session.getAttribute("id");
        if(id==null){
            session.setAttribute("msg", "로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }
        String name = (String) session.getAttribute("name");
        String phone = (String) session.getAttribute("phone");
        String addr = (String) session.getAttribute("addr") + " " + (String) session.getAttribute("detailaddr");
        //폼에서보내온 상품정보
        String optionname =(String) session.getAttribute("optionname");
        String pname = request.getParameter("pname");
        pname=new String(pname.getBytes("8859_1"),"UTF-8");
        String pimage = request.getParameter("pimage");
        int cartid=0;
        int stockcount=Integer.parseInt(request.getParameter("cartcount"));

        System.out.println("id = " + id);
        System.out.println("name = " + name);
        System.out.println("phone = " + phone);
        System.out.println("addr = " + addr);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);

        Random random = new Random();
        int randomNumber = random.nextInt(9000) + 1000;

        OrderVO vo = new OrderVO();
        ProductVO pVo = new ProductVO();
        CartVO cVo=new CartVO();

        //주문번호
        String orderNumber = formattedDate + randomNumber;
        int length=optionname.split("-").length;
        if(length<2){
            String optionvalue1 = optionname.split("-")[0];
            String optionvalue2 = "nooption2";
            pVo.setOptionvalue1(optionvalue1);
            pVo.setOptionvalue2(optionvalue2);
        }
        else{
            String optionvalue1 = optionname.split("-")[0];
            String optionvalue2 = optionname.split("-")[1];
            pVo.setOptionvalue1(optionvalue1);
            pVo.setOptionvalue2(optionvalue2);
        }

        vo.setId(id);
        vo.setName(name);
        vo.setPhone(phone);
        vo.setAddr(addr);
        vo.setPno(pno);
        vo.setPname(pname);
        vo.setPimage(pimage);
        vo.setCartid(cartid);
        vo.setOptionname(optionname);
        vo.setStockcount(stockcount);
        vo.setOrderidseq(orderNumber);

        //재고 확인
        pVo.setPno(pno);
        pVo.setCartcount(stockcount);
        int remain = productDao.checkRemain(pVo);
        //재고가 없거나 재고보다 많은 수량을 주문하면 msg에 재고가 없습니다를 담아서 상품상세페이지로 이동
        if (remain == 0 || stockcount > remain) {
            session.setAttribute("msg", "주문 실패 상품" + pname + "의 옵션" + optionname + "이/가 품절입니다.");
            return "redirect:/mall/productDetail.do?pno="+ pno;
        } else {
            session.setAttribute("msg", "주문 성공.");
            productDao.updateRemainCart(pVo);

            cVo.setOptionname(optionname);
            cVo.setCartcount(stockcount);
            cVo.setPno(pno);
            cartDao.updateCartRemain(cVo);
            cartDao.updateCountAfterOrder(cVo);


            orderDao.insertOrder(vo);
            cartDao.deleteCart(cartid);
        }
        return "/mall/main.do";
    }
}
