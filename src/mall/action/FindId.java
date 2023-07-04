package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class FindId implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        MemberVO vo = new MemberVO();
        MemberDao memberDao = MemberDao.getInstance();
        String name = request.getParameter("name");
        String phone = request.getParameter("phone");
        vo.setName(name);
        vo.setPhone(phone);
        String id = memberDao.findId(vo);
        System.out.println("id="+id);
        String result="no";

        PrintWriter out = response.getWriter();
        response.setContentType("text/html; charset=UTF-8");

        if(id!=null){
            result =id;
            System.out.println("result="+result);
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
