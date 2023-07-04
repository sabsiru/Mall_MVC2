package mall.dao;

import board.model.ConnUtil;
import mall.model.OrderVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderDao {
    private static OrderDao instance = null;

    private OrderDao() {
    }

    public static OrderDao getInstance() {
        if (instance == null) {
            synchronized (OrderDao.class) {
                instance = new OrderDao();
            }
        }
        return instance;
    }

    //insertOrder
    public int insertOrder(OrderVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {

            String orderidseq = vo.getOrderidseq();
            int orderid = vo.getOrderid();
            String id = vo.getId();
            int cartid = vo.getCartid();
            int pno = vo.getPno();
            String pname = vo.getPname();
            //pname=new String(pname.getBytes("8859_1"),"UTF-8");
            String optionname = vo.getOptionname();
           // optionname=new String(optionname.getBytes("8859_1"),"UTF-8");
            int stockcount = vo.getStockcount();
            String pimage = vo.getPimage();
            String name = vo.getName();
            String phone = vo.getPhone();
            String addr = vo.getAddr();

            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO orderlist values(");
            queryBuilder.append("orderid_seq.nextval,'" + orderidseq + "','" + id + "'," + cartid + "," + pno + ",'" + pname + "','" + optionname + "'," + stockcount + ",'" + pimage + "','" + name + "','" + phone + "','" + addr + "','ready','a','yet',sysdate)");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
        return 0;
    }

    //updateRefundRequest
    public void updateRefundRequest(OrderVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String orderidseq = vo.getOrderidseq();
            String id = vo.getId();
            String pname = vo.getPname();
            String optionname = vo.getOptionname();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE orderlist SET refund='request' WHERE orderidseq='" + orderidseq + "' AND id='" + id + "' AND pname='" + pname + "' AND optionname='" + optionname + "'");
            String query = queryBuilder.toString();
            conn = ConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            System.out.println("refundRequest query = " + query);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }

    //cancelRequest
    public void cancelRequest(OrderVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String orderidseq = vo.getOrderidseq();
            String id = vo.getId();
            String pname = vo.getPname();
            String optionname = vo.getOptionname();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE orderlist SET refund='a' WHERE orderidseq='" + orderidseq + "' AND id='" + id + "' AND pname='" + pname + "' AND optionname='" + optionname + "'");
            String query = queryBuilder.toString();
            conn = ConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }

    //orderConfirm
    public void orderConfirm(OrderVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            String orderidseq = vo.getOrderidseq();
            String id = vo.getId();
            String pname = vo.getPname();
            String optionname = vo.getOptionname();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("UPDATE orderlist SET receive='done' WHERE orderidseq='" + orderidseq + "' AND id='" + id + "' AND pname='" + pname + "' AND optionname='" + optionname + "'");
            String query = queryBuilder.toString();
            conn = ConnUtil.getConnection();
            pstmt = conn.prepareStatement(query);
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException ex) {
            }
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException ex) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException ex) {
            }
        }
    }
}
