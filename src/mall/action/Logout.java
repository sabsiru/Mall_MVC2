package mall.action;

import board.action.CommandAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        HttpSession session = request.getSession();
        session.invalidate();
        session = request.getSession();
        session.setAttribute("msg", "�α׾ƿ� �Ǿ����ϴ�.");
       
        return "/mall/main.do";
    }
}
