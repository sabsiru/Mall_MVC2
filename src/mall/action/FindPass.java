package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class FindPass implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        MemberVO vo = new MemberVO();
        MemberDao memberDao = MemberDao.getInstance();
        String id = request.getParameter("id");
        String phone = request.getParameter("phone");
        vo.setId(id);
        vo.setPhone(phone);
        String pass = memberDao.findPass(vo);
        String result = "no";

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");

        if (pass != null) {
            result = pass;
            out.println(result);
            out.close();
            return result;
        } else {
            out.println(result);
            out.close();
            return result;
        }
    }
}
