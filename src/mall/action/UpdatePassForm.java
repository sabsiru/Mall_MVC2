package mall.action;

import board.action.CommandAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdatePassForm implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String id=(String)request.getSession().getAttribute("id");
        if(id==null){
            request.getSession().setAttribute("msg", "�α��� �� �̿����ּ���.");
            return "/mall/loginForm.do";
        }
        return "/mall/updatePassForm.jsp";
    }
}
