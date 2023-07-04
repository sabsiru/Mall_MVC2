package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class GetPass implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        MemberVO vo = new MemberVO();
        MemberDao memberDao = MemberDao.getInstance();
        String pass = request.getParameter("pass");
        System.out.println("pass = " + pass);
        String id = (String) request.getSession().getAttribute("id");

        vo.setId(id);
        vo.setPass(pass);
        String result = "no";
        int count = memberDao.getPass(vo);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        System.out.println("count = " + count);
        if(count==1){
            result="ok";
            out.println(result);
            out.close();
            return result;
        }else{
            out.println(result);
            out.close();
            return result;
        }
    }
}
