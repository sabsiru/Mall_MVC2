package mall.dao;

import board.model.ConnUtil;
import mall.model.CartVO;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CartDao {
    private static CartDao instance = null;

    private CartDao() {
    }

    public static CartDao getInstance() {
        if (instance == null) {
            synchronized (CartDao.class) {
                instance = new CartDao();
            }
        }
        return instance;
    }

    //장바구니 넣기
    public String insertCart(CartVO vo) throws UnsupportedEncodingException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String id = vo.getId();
        System.out.println("id = " + id);
        int pno = vo.getPno();
        int price = vo.getPrice();
        String pname = vo.getPname();
        //pname 인코딩
        pname = new String(pname.getBytes("8859_1"), "utf-8");
        System.out.println("pname = " + pname);
        String pimage = vo.getPimage();
        int cartcount = vo.getCartcount();
        String optionname = vo.getOptionname();
        //optionname 인코딩
        optionname = new String(optionname.getBytes("8859_1"), "utf-8");
        System.out.println("optionname = " + optionname);
        int remain = vo.getRemain();
        System.out.println("remain = " + remain);
        System.out.println("insertCart start");
        try {
            conn = ConnUtil.getConnection();
            String data = "";
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("insert into cart values(");
            queryBuilder.append("cartid_seq.nextval, '" + id + "','" + pname + "','" + optionname + "','" + pimage + "'," + price + "," + cartcount + "," + pno + "," + remain + ")");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("query = " + query);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }

        }
        return "/mall/productDetail.do?pno=" + pno;
    }

    //CheckOption
    public int CheckOption(CartVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CartVO cartVO = new CartVO();
        int count = 0;
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select count(*) from cart where id = '" + vo.getId() + "' and optionname = '" + vo.getOptionname() + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                count = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //updateCartCount2
    public void updateCartCount2(CartVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("update cart set cartcount = cartcount + " + vo.getCartcount() + " where id = '" + vo.getId() + "' and optionname = '" + vo.getOptionname() + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("query = " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //selectCartCount
    public int selectCartCount(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select count(*) from cart where id = '" + id + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);

            if (rs.next()) {
                count = rs.getInt(1);
            } else {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }

        }
        return count;
    }

    //selectCartList
    public List<CartVO> selectCartList(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        List<CartVO> cartList = new ArrayList<>();
        //cartList에 담아서 리턴

        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select * from cart where id = '" + id + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);
            while (rs.next()) {
                CartVO cartVO = new CartVO();
                cartVO.setCartid(rs.getInt("cartid"));
                cartVO.setId(rs.getString("id"));
                cartVO.setPname(rs.getString("pname"));
                cartVO.setOptionname(rs.getString("optionname"));
                cartVO.setPimage(rs.getString("pimage"));
                cartVO.setPrice(rs.getInt("price"));
                cartVO.setCartcount(rs.getInt("cartcount"));
                cartVO.setPno(rs.getInt("pno"));
                cartVO.setRemain(rs.getInt("remain"));
                cartList.add(cartVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        return cartList;
    }

    //selectCart
    public List<CartVO> selectCart(Map<String, Object> cartIdArray) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<CartVO> cartList = new ArrayList<>();
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("SELECT * FROM cart WHERE cartid IN (");

            String[] cartIds = (String[]) cartIdArray.get("cartIdArray");
            int size = cartIds.length;

            for (int i = 0; i < size; i++) {
                queryBuilder.append("?");
                if (i < size - 1) {
                    queryBuilder.append(",");
                }
            }
            queryBuilder.append(")");

            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);

            for (int i = 0; i < size; i++) {
                pstmt.setString(i + 1, cartIds[i]);
            }
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);

            while (rs.next()) {
                CartVO cartVO = new CartVO();
                cartVO.setCartid(rs.getInt("cartid"));
                cartVO.setId(rs.getString("id"));
                cartVO.setPname(rs.getString("pname"));
                cartVO.setOptionname(rs.getString("optionname"));
                cartVO.setPimage(rs.getString("pimage"));
                cartVO.setPrice(rs.getInt("price"));
                cartVO.setCartcount(rs.getInt("cartcount"));
                cartVO.setPno(rs.getInt("pno"));
                cartVO.setRemain(rs.getInt("remain"));
                cartList.add(cartVO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        return cartList;
    }

    //updateCartCount
    public void updateCartCount(CartVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("update cart set cartcount = " + vo.getCartcount() + " where cartid = " + vo.getCartid());
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("query = " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //selectCartOrder
    public CartVO selectCartOrder(int cartid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CartVO cartVO = new CartVO();
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select * from cart where cartid = " + cartid);
            System.out.println("selectCartOrder cartid = " + cartid);
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);
            while (rs.next()) {
                cartVO.setCartid(rs.getInt("cartid"));
                cartVO.setId(rs.getString("id"));
                cartVO.setPname(rs.getString("pname"));
                cartVO.setOptionname(rs.getString("optionname"));
                cartVO.setPimage(rs.getString("pimage"));
                cartVO.setPrice(rs.getInt("price"));
                cartVO.setCartcount(rs.getInt("cartcount"));
                cartVO.setPno(rs.getInt("pno"));
                cartVO.setRemain(rs.getInt("remain"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        return cartVO;

    }

    //updateCartRemain
    public int updateCartRemain(CartVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnUtil.getConnection();
            int cartcount = vo.getCartcount();
            int pno = vo.getPno();
            String optionname = vo.getOptionname();
            optionname=new String(optionname.getBytes("8859_1"),"UTF-8");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("update cart set remain = remain-" + cartcount + " where pno = " + pno + " and optionname = '" + optionname + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("updateCartRemain query = " + query);
        } catch (SQLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }

        }
        return vo.getRemain();
    }

    //updateCountAfterOrder
    public int updateCountAfterOrder(CartVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = ConnUtil.getConnection();
            int cartcount = vo.getCartcount();
            int pno = vo.getPno();
            String optionname = vo.getOptionname();
            optionname=new String(optionname.getBytes("8859_1"),"UTF-8");
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("update cart set cartcount = remain where remain <= cartcount and pno = " + pno + " and optionname = '" + optionname + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("updatecartafterorder query = " + query);
        } catch (SQLException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        return vo.getCartcount();
    }

    //deleteCart
    public int deleteCart(int cartid) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        CartVO cartVO = new CartVO();
        try{
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            System.out.println("deletecart cartid = " + cartid);
            queryBuilder.append("delete from cart where cartid = " + cartid);
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            pstmt.executeUpdate();
            System.out.println("query = " + query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }
        return cartid;
    }
}
