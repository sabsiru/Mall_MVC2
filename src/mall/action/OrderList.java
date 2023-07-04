package mall.action;

import board.action.CommandAction;
import mall.dao.MemberDao;
import mall.model.OrderVO;
import mall.paging.Paging;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class OrderList implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String pageNum = request.getParameter("pageNum"); // Current page number
        String pagingHtml = "";

        if (pageNum == null) {
            pageNum = "1";
        }

        int pageSize = 10; // Number of items per page
        int blockCount = 10;
        int currentPage = Integer.parseInt(pageNum);
        int startCount = (currentPage - 1) * pageSize + 1; // Start count
        int endCount = currentPage * pageSize; // End count
        int count = 0;
        int number = 0;
        String id=(String)request.getSession().getAttribute("id");
        List<OrderVO> orderList = null;
        MemberDao memberDao = MemberDao.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        String keyField = request.getParameter("keyField");
        String keyWord = request.getParameter("keyWord");
        map.put("keyField", keyField);
        map.put("keyWord", keyWord);
        map.put("startCount", startCount);
        map.put("endCount", endCount);
        map.put("id", id);

        count = memberDao.selectOrderListAllCount(map);
        Paging page = new Paging(keyField, keyWord, currentPage, count, pageSize, blockCount, "orderList.do");;
        pagingHtml = page.getPagingHtml().toString();
        if (count > 0) {
            orderList = memberDao.selectOrderListAll(map);
        } else {
            orderList = Collections.emptyList();
        }
        number = count - (currentPage - 1) * pageSize;

        request.setAttribute("orderList", orderList);
        
        request.setAttribute("currentPage", Integer.valueOf(currentPage));
        request.setAttribute("count", Integer.valueOf(count));
        request.setAttribute("number", Integer.valueOf(number));
        request.setAttribute("pagingHtml", pagingHtml);
        
        return "/mall/OrderList.jsp";
    }
}
