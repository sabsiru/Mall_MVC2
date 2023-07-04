package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.MemberVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;


public class Login implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        System.out.println("start login");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        MemberVO vo = new MemberVO();
        vo.setId(request.getParameter("id"));
        vo.setPass(request.getParameter("pass"));

        MemberDao memberdao = MemberDao.getInstance();

        MemberVO result = memberdao.login(vo);
     
        if (result.getName() != null&&result.getName()!="") {
            session = request.getSession();
            String id = SessionConfig.getSessionidCheck("id", result.getId());
            //페이지가 바뀌어도 로그인 유지
            session.setAttribute("id", result.getId());
            session.setAttribute("name", result.getName());
            session.setAttribute("phone", result.getPhone());
            session.setAttribute("mail", result.getMail());
            session.setAttribute("addr", result.getAddr());
            session.setAttribute("detailaddr", result.getDetailaddr());
            session.setAttribute("powerno", result.getPowerno());
            session.setAttribute("postcode", result.getPostcode());
            session.setAttribute("regdate", result.getRegdate());
            //로그인 유지 시간 30분
            session.setMaxInactiveInterval(1800);
            //powerno가 5일 경우 관리자님 환영합니다.
            if (result.getPowerno() == 5) {
                session.setAttribute("msg", "관리자님 환영합니다.");
                return "/mall/main.do";
            } else {
                session.setAttribute("msg", result.getName() + "님 환영합니다.");
            }
            return "/mall/main.do";
            //입력값이 없으면 msg에 메시지 담아서 보내기
        } else if (vo.getId().equals("") || vo.getPass().equals("")) {
            session.setAttribute("msg", "아이디 또는 비밀번호를 입력해주세요.");
            return "/mall/loginForm.do";
            //입력값이 없으면 msg에 메시지 담아서 보내기
            //session에 같은 id가 있을경우 기존의 session 초기화


        } else {
            //실패시 msg에 메시지 담아보내기
            session.setAttribute("msg", "아이디 또는 비밀번호가 일치하지 않습니다.");
            return "/mall/loginForm.do";
        }
    }
}
