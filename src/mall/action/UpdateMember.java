package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateMember implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("updateMember start");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        MemberVO vo = new MemberVO();
        String id = (String) session.getAttribute("id");
        if(id==null){
            session.setAttribute("msg", "로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }
        String phone = request.getParameter("phone");
        String mail = request.getParameter("mail");
        String postcode = request.getParameter("postcode");
        String addr = request.getParameter("addr");
        String detailaddr = request.getParameter("detailaddr");

        System.out.println("Join id = " + id);

        vo.setPhone(phone);
        vo.setMail(mail);
        vo.setPostcode(postcode);
        vo.setAddr(addr);
        vo.setDetailaddr(detailaddr);
        vo.setId(id);
        session.setAttribute("phone", phone);
        session.setAttribute("mail", mail);
        session.setAttribute("postcode", postcode);
        session.setAttribute("addr", addr);
        session.setAttribute("detailaddr", detailaddr);
        
        MemberDao memberdao = MemberDao.getInstance();
        //vo에 값을 담아서 join 실행
        int result = memberdao.updateMember(vo);
        if (result == 1) {
            session.setAttribute("msg", "회원정보가 수정되었습니다.");
        } else {
            session.setAttribute("msg", "회원정보 수정에 실패하였습니다.");
        }
        return "/mall/myPage.do";
    }
}
