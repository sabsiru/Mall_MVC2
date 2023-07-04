package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyPage implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id = (String) request.getSession().getAttribute("id");
        HttpSession session = request.getSession();
        if (id == null) {
            session.setAttribute("msg", "로그인 후 이용해주세요.");
            return "/mall/loginForm.do";
        }
        return "/mall/myPage.jsp";
    }
}
