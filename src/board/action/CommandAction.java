package board.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAction {
//��û �Ķ���ͷ� ��ɾ �����ϴ� ����� ���� �������̽�
	
	public String requestPro(HttpServletRequest request, HttpServletResponse response)throws Throwable;
}
