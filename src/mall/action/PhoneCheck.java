package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class PhoneCheck implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("start phoneCheck");
        String phone = request.getParameter("phone");
        System.out.println("phone = " + phone);
        MemberDao memberdao = MemberDao.getInstance();
        int count = memberdao.phoneCheck(phone);
        System.out.println("count = " + count);
        String result = "";

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

      //count
        if (count == 1) {
            result = "used";
            out.println(result);
            out.close();

        } else {
            result = "ok";
            out.println(result);
            out.close();
        }
        return null;
    }
}
