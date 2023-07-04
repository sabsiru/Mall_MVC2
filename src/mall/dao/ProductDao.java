package mall.dao;

import board.model.ConnUtil;
import mall.model.ProductVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductDao {
    private static ProductDao instance = null;

    private ProductDao() {
    }

    public static ProductDao getInstance() {
        if (instance == null) {
            synchronized (ProductDao.class) {
                instance = new ProductDao();
            }
        }
        return instance;
    }

    public int productCount(Map<String, Object> map) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            String keyField = (String) map.get("keyField");
            String keyWord = (String) map.get("keyWord");
            //keyword utf-8인코딩
            if (keyWord != null || keyWord == "") {
                keyWord = new String(keyWord.getBytes("8859_1"), "utf-8");
            }
            System.out.println("keyWord = " + keyWord);
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT COUNT (*) FROM product WHERE 1=1 AND delinfo = 'no'");
            // 검색조건 keyword 인코딩 한글 인코딩

            if (keyWord != null && !keyWord.equals("")) {
                queryBuilder.append(" AND pname like '%' ||'" + keyWord.trim() + "'|| '%' ");
            }
            System.out.println("queryBuilder = " + queryBuilder);

            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return count;
    }

    public List<ProductVO> productList(Map<String, Object> map) {
        System.out.println("list");
        List<ProductVO> productList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            String keyField = (String) map.get("keyField");
            String keyWord = (String) map.get("keyWord");
            if (keyWord != null || keyWord == "") {
                keyWord = new String(keyWord.getBytes("8859_1"), "utf-8");
            }
            System.out.println("keyField = " + keyField);
            System.out.println("keyWord = " + keyWord);
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();

            queryBuilder.append("SELECT * FROM (");
            queryBuilder.append("SELECT A.*,");
            queryBuilder.append("ROWNUM RNUM FROM (select * from product where 1=1 ");

            if (keyWord != null && !keyWord.equals("")) {
                queryBuilder.append(" AND pname like '%' ||'" + keyWord + "'|| '%' ");
            }
            queryBuilder.append("order by pno desc) A");
            queryBuilder.append(") WHERE RNUM >= ? AND RNUM <= ? and delinfo = 'no'");

            pstmt = conn.prepareStatement(queryBuilder.toString());
            int paramIndex = 1;
            String query = queryBuilder.toString();
            System.out.println("query = " + query);
            //검색조건이 없으면 매개변수 2개
            pstmt.setInt(1, (int) map.get("startCount"));
            pstmt.setInt(2, (int) map.get("endCount"));


            rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductVO product = new ProductVO();
                product.setPno(rs.getInt("pno"));
                product.setPname(rs.getString("pname"));
                product.setPrice(rs.getInt("price"));
                product.setPimage(rs.getString("pimage"));
                product.setDetailimage(rs.getString("detailimage"));
                product.setPcontent(rs.getString("pcontent"));
                product.setPrdate(rs.getString("prdate"));
                product.setDelinfo(rs.getString("delinfo"));
                // Set other properties

                productList.add(product);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources in the reverse order of their creation
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return productList;
    }

    public ProductVO productDetail(int pno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ProductVO productVO = null;

        try {
            // Establish a database connection
            conn = ConnUtil.getConnection();

            // Prepare the SQL statement to retrieve the product details
            String query = "SELECT * FROM product WHERE pno = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pno);

            // Execute the query and retrieve the result set
            rs = pstmt.executeQuery();

            if (rs.next()) {
                // Populate the ProductVO object with the retrieved data
                productVO = new ProductVO();
                productVO.setPno(rs.getInt("pno"));
                productVO.setPname(rs.getString("pname"));
                productVO.setPrice(rs.getInt("price"));
                productVO.setPimage(rs.getString("pimage"));
                productVO.setDetailimage(rs.getString("detailimage"));
                productVO.setPcontent(rs.getString("pcontent"));
                productVO.setPrdate(rs.getString("prdate"));
                productVO.setDelinfo(rs.getString("delinfo"));
                // Set other properties of the ProductVO object
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources in the reverse order of their creation to ensure proper cleanup
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return productVO;
    }

    public List<ProductVO> optionList(int pno) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<ProductVO> optionList = new ArrayList<>();

        try {
            // Establish a database connection
            conn = ConnUtil.getConnection();

            // Prepare the SQL statement to retrieve the option list
            String query = "SELECT * FROM poption WHERE pno = ? ORDER BY optionvalue1 DESC";
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, pno);

            // Execute the query and retrieve the result set
            rs = pstmt.executeQuery();

            while (rs.next()) {
                // Create a new ProductVO object for each option and populate it with the retrieved data
                ProductVO option = new ProductVO();
                option.setPno(rs.getInt("pno"));
                option.setOption1(rs.getString("option1"));
                option.setOption2(rs.getString("option2"));
                option.setOptionvalue1(rs.getString("optionvalue1"));
                option.setOptionvalue2(rs.getString("optionvalue2"));
                option.setRemain(rs.getInt("remain"));

                // Add the option to the option list
                optionList.add(option);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the resources in the reverse order of their creation to ensure proper cleanup
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return optionList;
    }

    //checkRemain
    public int checkRemain(ProductVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnUtil.getConnection();
            String optionvalue1 = vo.getOptionvalue1();
            String optionvalue2 = vo.getOptionvalue2();
            System.out.println("optionvalue2 = " + optionvalue2);
            int pno = vo.getPno();
            int remain=0;
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select remain from poption ");
            if (optionvalue2 != "nooption2") {
                queryBuilder.append(("where pno=" + pno + " and optionvalue1='" + optionvalue1 + "' and optionvalue2='" + optionvalue2 + "'"));
            } else {
                queryBuilder.append(("where pno='" + pno + "' and optionvalue1='" + optionvalue1 + "' and optionvalue2 is null"));
            }
            String query = queryBuilder.toString();
            System.out.println("checkRemain query = " + query);
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                remain = rs.getInt("remain");
                System.out.println("dao remain = " + remain);
                return remain;
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //updateRemainCart
    public int updateRemainCart(ProductVO vo)throws SQLException{
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try{
            conn = ConnUtil.getConnection();
            String optionvalue1 = vo.getOptionvalue1();
            String optionvalue2 = vo.getOptionvalue2();
            int pno = vo.getPno();
            int remain = vo.getRemain();
            int cartcount = vo.getCartcount();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("update poption set remain = remain-"+cartcount+" ");
            if(optionvalue2 != "nooption2"){
                queryBuilder.append("where pno="+pno+" and optionvalue1='"+optionvalue1+"' and optionvalue2='"+optionvalue2+"'");
            }else{
                queryBuilder.append("where pno="+pno+" and optionvalue1='"+optionvalue1+"' and optionvalue2 is null");
            }
            String query = queryBuilder.toString();
            System.out.println("updateRemainCart query = " + query);
            pstmt = conn.prepareStatement(query);
            int result = pstmt.executeUpdate();
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            }
        }
    }



}
