package mall.action;

import board.action.CommandAction;
import mall.dao.ProductDao;
import mall.model.ProductVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import mall.paging.Paging;

public class Main implements CommandAction {
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
		List<ProductVO> productList = null;
		ProductDao productDao = ProductDao.getInstance(); // Replace with your actual DAO instantiation
		HashMap<String, Object> map = new HashMap<>();
		String keyField = request.getParameter("keyField");
		String keyWord = request.getParameter("keyWord");
		map.put("keyField", keyField);
		map.put("keyWord", keyWord);
		map.put("startCount", startCount);
		map.put("endCount", endCount);
		
		count = productDao.productCount(map); // Total number of products

		Paging page = new Paging(keyField, keyWord, currentPage, count, pageSize, blockCount, "main.do");
		pagingHtml = page.getPagingHtml().toString();

		if (count > 0) {
			productList = productDao.productList(map); // Get product list
		} else {
			productList = Collections.emptyList();
		}
		//productList에 담긴 pno 가져오기
        for (ProductVO productVO : productList) {
            int pno = productVO.getPno();
            System.out.println("pno=" + pno);
        }

		number = count - (currentPage - 1) * pageSize; // Calculate the product number

		// Set attributes for the request
		request.setAttribute("keyField", keyField);
		request.setAttribute("keyWord", keyWord);
		request.setAttribute("productList", productList);
	     request.setAttribute("currentPage", Integer.valueOf(currentPage));
	        request.setAttribute("count", Integer.valueOf(count));
	        request.setAttribute("number", Integer.valueOf(number));
	        request.setAttribute("pagingHtml", pagingHtml);

		return "/mall/Main.jsp"; // Return the view name
	}

}
