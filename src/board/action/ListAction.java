package board.action;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.BoardDAO;
import board.model.BoardVO;

public class ListAction implements CommandAction{//�۸�� ó��
	public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable{
		String pageNum = request.getParameter("pageNum"); //��������ȣ
		if(pageNum == null){
			pageNum = "1";
		}
		int pageSize = 10;//�� �������� ���� ����
		int currentPage = Integer.parseInt(pageNum);
		//���������� ���۱� ��ȣ
		int startRow = (currentPage - 1 ) * pageSize +1;
		int endRow = currentPage * pageSize;//�� �������� �������� ��ȣ
	    int count = 0;
	    int number = 0;
	    List<BoardVO> boardList = null;
	    BoardDAO dbPro = BoardDAO.getInstance();//db����
	    count = dbPro.getBoardCount();//��ü ���� ��
	    if(count >0){boardList = dbPro.getBoards(startRow,endRow);//����3
	    }else{
	    	boardList = Collections.emptyList();
	    }
	    number = count-(currentPage-1)*pageSize; //�۸�Ͽ� ǥ���� �۹�ȣ
	    //�ش� �信�� ����� �Ӽ�
	    request.setAttribute("currentPage",new Integer(currentPage));
	    request.setAttribute("startRow",new Integer(startRow));
	    request.setAttribute("endRow",new Integer(endRow));
	    request.setAttribute("count",new Integer(count));
	    request.setAttribute("pageSize",new Integer(pageSize));
	    request.setAttribute("number",new Integer(number));
	    request.setAttribute("boardList",boardList);
	    return "/board/list.jsp";//�ش��
	}

}
