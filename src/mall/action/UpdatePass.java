package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdatePass implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("updatePass start");
        HttpSession session = request.getSession();
        MemberVO vo = new MemberVO();
        MemberDao memberDao = MemberDao.getInstance();
        String id = (String) request.getSession().getAttribute("id");
        String pass=request.getParameter("pass");
        vo.setId(id);
        vo.setPass(pass);

        int result = memberDao.updatePass(vo);
        if (result == 1) {
            session.setAttribute("msg", "비밀번호가 수정되었습니다.");
        } else {
            session.setAttribute("msg", "비밀번호 수정에 실패하였습니다.");
        }
        return "/mall/myPage.do";
    }
}
