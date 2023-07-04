package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class GetPhone implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        MemberVO vo=new MemberVO();
        MemberDao memberDao=MemberDao.getInstance();
        String phone = request.getParameter("phone");
        System.out.println("phone = " + phone);
        String id=(String)request.getSession().getAttribute("id");
        vo.setPhone(phone);
        vo.setId(id);
        String result="no";
        int count=memberDao.phoneCheck(phone);
        int count2=memberDao.getPhone(vo);
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out=response.getWriter();
        System.out.println("count = " + count);
        System.out.println("count2 = " + count2);
        if(count==1&&count2==1){
            result="same";
            out.println(result);
            out.close();
            return result;
        }else if(count==0){
            result="ok";
            out.println(result);
            out.close();
            return result;
        } else{
            out.println(result);
            out.close();
            return result;
        }
    }
}
