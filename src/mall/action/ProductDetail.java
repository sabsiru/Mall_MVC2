package mall.action;

import board.action.CommandAction;
import mall.dao.ProductDao;
import mall.model.ProductVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ProductDetail implements CommandAction {
    public String requestPro(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        int pno = Integer.parseInt(request.getParameter("pno"));
        ProductDao productDao = ProductDao.getInstance(); // Replace with your actual DAO instantiation
        ProductVO prVO = productDao.productDetail(pno);
        List<ProductVO> optionList = productDao.optionList(pno);

        request.setAttribute("prVO", prVO);
        request.setAttribute("optionList", optionList);
        return "/mall/ProductDetail.jsp";
    }
}
