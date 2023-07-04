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
import java.util.Arrays;
import java.util.Random;

public class InsertOrder implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session=request.getSession();
        String id = (String) session.getAttribute("id");
        CartDao cartDao = CartDao.getInstance();
        OrderDao orderDao = OrderDao.getInstance();
        ProductDao productDao = ProductDao.getInstance();
        if(id == null){
            session.setAttribute("msg","�α��� �� �̿����ּ���.");
            return "/mall/login.do";
        }
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
        String[] cartArray = request.getParameterValues("cartid");
        int size = cartArray.length;
        //���糯¥�� int������ �����
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = currentDate.format(formatter);

        Random random = new Random();
        //1���� random�� 1���� ����

        int randomNumber = random.nextInt(9000) + 1000;

        String orderNumber = formattedDate + randomNumber;
        vo.setOrderidseq(orderNumber);
        System.out.println("orderNumber = " + orderNumber);
        int remain=0;
        for (int i = 0; i < size; i++) {
            int cartid = Integer.parseInt(cartArray[i]);
            System.out.println("cartid2 = " + cartid);
            //cartid2�� ��ǰ ������ �����´�
            CartVO cVo = cartDao.selectCartOrder(cartid);
            System.out.println("cVo = " + cVo);
            vo.setPno(cVo.getPno());
            vo.setPname(cVo.getPname());
            vo.setPimage(cVo.getPimage());
            vo.setCartid(cartid);
            String optionname = cVo.getOptionname();
            System.out.println("optionname = " + optionname);
            //optionvalue�� -�� ���� ������ ����� 1�̸� optionvalue1�� �ְ� 2�̸� optionvalue1,2�� �ִ�
            String[] optionArray = optionname.split("-");
            System.out.println("optionArray = " + Arrays.toString(optionArray));
            ProductVO pVo = new ProductVO();
            if(optionArray.length <2){
                String optionvalue1 = optionArray[0];
                System.out.println("optionvalue1 = " + optionvalue1);
                pVo.setOptionvalue1(optionvalue1);
                pVo.setOptionvalue2("nooption2");
            }
            else{
                String optionvalue1 = optionArray[0];
                String optionvalue2 = optionArray[1];
                pVo.setOptionvalue1(optionvalue1);
                pVo.setOptionvalue2(optionvalue2);
            }
            String pname = cVo.getPname();
            System.out.println("pname = " + pname);
            int cartcount = cVo.getCartcount();
            System.out.println("cartcount = " + cartcount);
            //���Ȯ��
            pVo.setPno(cVo.getPno());
            remain=productDao.checkRemain(pVo);
            System.out.println("after checkRemain remain = " + remain);

            //��� ������ msg�� ��� �����ϴٸ� ��Ƽ� ����
            //cartcount�� remain ��
            if (remain == 0 || cartcount > remain) {
                session.setAttribute("msg", "�ֹ� ���� ��ǰ" + pname + "�� �ɼ�" + optionname + "��/�� ǰ���Դϴ�.");
                return "/mall/main.do";
            } else {
                session.setAttribute("msg", "�ֹ� ����.");

                System.out.println("value2="+pVo.getOptionvalue2());
                pVo.setPno(cVo.getPno());
                pVo.setCartcount(cVo.getCartcount());
                productDao.updateRemainCart(pVo);




                cVo.setOptionname(optionname);
                cVo.setCartcount(cVo.getCartcount());
                cVo.setPno(cVo.getPno());
                cartDao.updateCartRemain(cVo);
                cartDao.updateCountAfterOrder(cVo);

                vo.setOptionname(cVo.getOptionname());
                vo.setStockcount(cVo.getCartcount());
                orderDao.insertOrder(vo);
                cartDao.deleteCart(cartid);
            }
        }

        return "/mall/main.do";
    }
}
