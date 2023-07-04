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
            //�������� �ٲ� �α��� ����
            session.setAttribute("id", result.getId());
            session.setAttribute("name", result.getName());
            session.setAttribute("phone", result.getPhone());
            session.setAttribute("mail", result.getMail());
            session.setAttribute("addr", result.getAddr());
            session.setAttribute("detailaddr", result.getDetailaddr());
            session.setAttribute("powerno", result.getPowerno());
            session.setAttribute("postcode", result.getPostcode());
            session.setAttribute("regdate", result.getRegdate());
            //�α��� ���� �ð� 30��
            session.setMaxInactiveInterval(1800);
            //powerno�� 5�� ��� �����ڴ� ȯ���մϴ�.
            if (result.getPowerno() == 5) {
                session.setAttribute("msg", "�����ڴ� ȯ���մϴ�.");
                return "/mall/main.do";
            } else {
                session.setAttribute("msg", result.getName() + "�� ȯ���մϴ�.");
            }
            return "/mall/main.do";
            //�Է°��� ������ msg�� �޽��� ��Ƽ� ������
        } else if (vo.getId().equals("") || vo.getPass().equals("")) {
            session.setAttribute("msg", "���̵� �Ǵ� ��й�ȣ�� �Է����ּ���.");
            return "/mall/loginForm.do";
            //�Է°��� ������ msg�� �޽��� ��Ƽ� ������
            //session�� ���� id�� ������� ������ session �ʱ�ȭ


        } else {
            //���н� msg�� �޽��� ��ƺ�����
            session.setAttribute("msg", "���̵� �Ǵ� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
            return "/mall/loginForm.do";
        }
    }
}
