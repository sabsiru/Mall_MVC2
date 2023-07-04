package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class IdCheck implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("start idCheck");
        String id = request.getParameter("id");
        System.out.println("id = " + id);
        MemberDao memberdao = MemberDao.getInstance();
        int count = memberdao.idCheck(id);
        System.out.println("count = " + count);
        String result = "";

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

      //count ∞° 1¿Ã∏È used
        if (count == 1) {
            result = "used";
            out.println(result);
            out.close();

        } else {
            result = "ok";
            out.println(result);
            out.close();

        }
        return id;
    }
}
