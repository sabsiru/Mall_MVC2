package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Join implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("start join");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        MemberVO vo = new MemberVO();
        String id = request.getParameter("id");
        System.out.println("Join id = " + id);
        vo.setId(request.getParameter("id"));
        vo.setPass(request.getParameter("pass"));
        vo.setName(request.getParameter("name"));
        vo.setPhone(request.getParameter("phone"));
        vo.setMail(request.getParameter("mail"));
        vo.setAddr(request.getParameter("addr"));
        vo.setDetailaddr(request.getParameter("detailaddr"));
        vo.setPostcode(request.getParameter("postcode"));
        MemberDao memberdao = MemberDao.getInstance();
        //vo에 값을 담아서 join 실행
        int result = memberdao.join(vo);

        return "/mall/loginForm.do";
        }
    
}
