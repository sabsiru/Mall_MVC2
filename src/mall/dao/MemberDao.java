package mall.dao;

import board.model.ConnUtil;
import mall.model.MemberVO;
import mall.model.OrderVO;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MemberDao {
    private static MemberDao instance = null;

    private MemberDao() {
    }

    public static MemberDao getInstance() {
        if (instance == null) {
            synchronized (MemberDao.class) {
                instance = new MemberDao();
            }
        }
        return instance;
    }

    //login
    public MemberVO login(MemberVO vo) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        System.out.println("dao start");
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();

            String id = vo.getId();
            String pass = vo.getPass();
            queryBuilder.append("SELECT * FROM mallmember WHERE id = '" + id + "' AND pass = '" + pass + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            System.out.println("dao vo.getId() = " + vo.getId());
            System.out.println("dao vo.getPass() = " + vo.getPass());
            System.out.println("vo.getName() = " + vo.getName());

            rs = pstmt.executeQuery();
            System.out.println("query = " + query);

            //resultset호출
            //결과가 있으면
            if (rs.next()) {
                vo.setId(rs.getString("id"));
                vo.setPass(rs.getString("pass"));
                vo.setName(rs.getString("name"));
                vo.setPhone(rs.getString("phone"));
                vo.setMail(rs.getString("mail"));
                vo.setAddr(rs.getString("addr"));
                vo.setDetailaddr(rs.getString("detailaddr"));
                vo.setPowerno(rs.getInt("powerno"));
                vo.setPostcode(rs.getString("postcode"));
                vo.setRegdate(rs.getString("regdate"));
                System.out.println("vo.getName() = " + vo.getName());
                System.out.println("vo.getPass() = " + vo.getPass());
                System.out.println("vo.getId() = " + vo.getId());
                System.out.println("vo.getPhone() = " + vo.getPhone());
                System.out.println("vo.getMail() = " + vo.getMail());
                System.out.println("vo.getAddr() = " + vo.getAddr());
                System.out.println("vo.getDetailaddr() = " + vo.getDetailaddr());
                System.out.println("vo.getPowerno() = " + vo.getPowerno());
                System.out.println("vo.getPostcode() = " + vo.getPostcode());
                System.out.println("vo.getRegdate() = " + vo.getRegdate());
                return vo;
            } else {
                return vo;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (pstmt != null) pstmt.close();
            if (conn != null) conn.close();

        }
        return vo;

    }

    //join
    public int join(MemberVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            conn = ConnUtil.getConnection();
            String id = vo.getId();
            System.out.println("dao id = " + id);
            String pass = vo.getPass();
            String name = vo.getName();
            String phone = vo.getPhone();
            String mail = vo.getMail();
            String addr = vo.getAddr();
            String detailaddr = vo.getDetailaddr();
            String postcode = vo.getPostcode();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("INSERT INTO mallmember(mno,id,pass,name,phone,mail,addr,detailaddr,postcode,regdate)");
            queryBuilder.append("VALUES(mno_seq.nextval,'" + id + "','" + pass + "','" + name + "','" + phone + "','" + mail + "','" + addr + "','" + detailaddr + "','" + postcode + "',sysdate)");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = 1;
            } else {
                result = 0;
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
        return result;
    }

    //idCheck 중복체크
    public int idCheck(String id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "";
        int count = 0;
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            System.out.println("dao id = " + id);
            queryBuilder.append("SELECT count(*) FROM mallmember WHERE id = '" + id + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            System.out.println("dao count = " + count);
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

    //phoneCheck
    public int phoneCheck(String phone) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String result = "";
        int count = 0;
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            System.out.println("dao phone = " + phone);
            queryBuilder.append("SELECT count(*) FROM mallmember WHERE phone = '" + phone + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();

            System.out.println("dao count = " + count);
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

    //getPhone
    public int getPhone(MemberVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        String phone = vo.getPhone();
        String id = vo.getId();
        try {
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            System.out.println("dao phone = " + phone);
            System.out.println("dao id = " + id);
            queryBuilder.append("SELECT count(*) FROM mallmember WHERE id ='" + id + "' and phone = '" + phone + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);
            System.out.println("dao count2 = " + count);
            if (rs.next()) {
                count = rs.getInt(1);
                System.out.println("count = " + count);
            } else {
                count = rs.getInt(1);
                System.out.println("count = " + count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //getPass
    public int getPass(MemberVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        String id = vo.getId();
        String pass = vo.getPass();
        try {
            StringBuilder queryBuilder = new StringBuilder();
            conn = ConnUtil.getConnection();
            queryBuilder.append("SELECT count(*) FROM mallmember WHERE id ='" + id + "' and pass = '" + pass + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            rs = pstmt.executeQuery();
            System.out.println("query = " + query);

            if (rs.next()) {
                count = rs.getInt(1);
                System.out.println("count = " + count);
            } else {
                count = rs.getInt(1);
                System.out.println("count = " + count);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //updateMember
    public int updateMember(MemberVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int result = 0;
        try {
            StringBuilder queryBuilder = new StringBuilder();
            conn = ConnUtil.getConnection();
            String id = vo.getId();
            String phone = vo.getPhone();
            String mail = vo.getMail();
            String addr = vo.getAddr();
            String detailaddr = vo.getDetailaddr();
            String postcode = vo.getPostcode();
            queryBuilder.append("UPDATE mallmember SET phone='" + phone + "',mail='" + mail + "',addr='" + addr + "',detailaddr='" + detailaddr + "',postcode='" + postcode + "' WHERE id='" + id + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                result = 1;
            } else {
                result = 0;
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
        return result;
    }

    //updatePass
    public int updatePass(MemberVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            StringBuilder queryBuilder = new StringBuilder();
            String pass = vo.getPass();
            String id = vo.getId();
            conn = ConnUtil.getConnection();
            queryBuilder.append("UPDATE mallmember SET pass='" + pass + "' WHERE id='" + id + "'");
            String query = queryBuilder.toString();
            pstmt = conn.prepareStatement(query);
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = 1;
            } else {
                count = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    //selectOrderListAll
    public List<OrderVO> selectOrderListAll(Map<String, Object> map) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        List<OrderVO> orderList = new ArrayList<OrderVO>();
        try {
            String keyField = (String) map.get("keyField");
            String keyWord = (String) map.get("keyWord");

            if (keyWord != null && !keyWord.equals("")) {
                keyWord = new String(keyWord.getBytes("8859_1"), "utf-8");
            }

            String id = (String) map.get("id");

            System.out.println("keyField = " + keyField);
            System.out.println("keyWord = " + keyWord);
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select * from(");
            queryBuilder.append("select A.*,");
            queryBuilder.append("rownum rnum from(select * from orderlist where id='" + id + "' ");

            //keyWord가 null 아니고 keyField가 'all'일때
            if (keyWord!=null&&!keyWord.equals("")&&keyField.equals("all")) {
                queryBuilder.append("AND(orderidseq LIKE '%' ||'" + keyWord + "' || '%' ");
                queryBuilder.append("OR pname LIKE '%' ||'" + keyWord + "' || '%')");
            } else if (keyWord!=null&&!keyWord.equals("")&&keyField.equals("orderidseq")) {
                queryBuilder.append("AND orderidseq LIKE '%' ||'" + keyWord + "' || '%'");
            } else if (keyWord!=null&&!keyWord.equals("")&&keyField.equals("pname")) {
                queryBuilder.append("AND pname LIKE '%' ||'" + keyWord + "' || '%'");
            }

            queryBuilder.append("order by orderdate desc,orderidseq desc, pno desc) A");
            queryBuilder.append(") WHERE RNUM >= ? AND RNUM <= ?");

            pstmt = conn.prepareStatement(queryBuilder.toString());
            String query = queryBuilder.toString();
            System.out.println("query = " + query);
            pstmt.setInt(1, (int) map.get("startCount"));
            pstmt.setInt(2, (int) map.get("endCount"));

            rs = pstmt.executeQuery();

            while (rs.next()) {
                OrderVO oVo = new OrderVO();
                oVo.setOrderid(rs.getInt("orderid"));
                oVo.setOrderidseq(rs.getString("orderidseq"));
                oVo.setId(rs.getString("id"));
                oVo.setCartid(rs.getInt("cartid"));
                oVo.setPno(rs.getInt("pno"));
                oVo.setPname(rs.getString("pname"));
                oVo.setOptionname(rs.getString("optionname"));
                oVo.setStockcount(rs.getInt("stockcount"));
                oVo.setPimage(rs.getString("pimage"));
                oVo.setName(rs.getString("name"));
                oVo.setPhone(rs.getString("phone"));
                oVo.setAddr(rs.getString("addr"));
                oVo.setOrderdate(rs.getString("orderdate"));
                oVo.setStatus(rs.getString("status"));
                oVo.setRefund(rs.getString("refund"));
                oVo.setReceive(rs.getString("receive"));

                orderList.add(oVo);
            }
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
        return orderList;
    }

    //selectOrderListAllCount
    public int selectOrderListAllCount(Map<String, Object> map) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            String keyField = (String) map.get("keyField");
            String keyWord = (String) map.get("keyWord");
            if (keyWord != null && !keyWord.equals("")) {
                keyWord = new String(keyWord.getBytes("8859_1"), "utf-8");
            }
            String id = (String) map.get("id");

            System.out.println("keyField = " + keyField);
            System.out.println("keyWord = " + keyWord);
            conn = ConnUtil.getConnection();
            StringBuilder queryBuilder = new StringBuilder();
            queryBuilder.append("select count(*) from orderlist where id='" + id + "' ");
            //keyWord가 null 아니고 keyField가 'all'일때

            if (keyWord!=null&&!keyWord.equals("")&&keyField.equals("all")) {
                queryBuilder.append("AND(orderidseq LIKE '%' ||'" + keyWord + "' || '%' ");
                queryBuilder.append("OR pname LIKE '%' ||'" + keyWord + "' || '%')");
            } else if (keyWord!=null&&!keyWord.equals("")&&keyField.equals("orderidseq")) {
                queryBuilder.append("AND orderidseq LIKE '%' ||'" + keyWord + "' || '%'");
            } else if (keyWord!=null&&!keyWord.equals("")&&keyField.equals("pname")) {
                queryBuilder.append("AND pname LIKE '%' ||'" + keyWord + "' || '%'");
            }


            pstmt = conn.prepareStatement(queryBuilder.toString());
            String query = queryBuilder.toString();
            System.out.println("query = " + query);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
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
        return count;
    }

    //findId
    public String findId(MemberVO vo) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

       String name=vo.getName();
       String phone=vo.getPhone();
       String id=null;
        try {
            conn = ConnUtil.getConnection();
            String query = "select id from mallmember where name=? and phone=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                id = rs.getString("id");
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
        return id;
    }
    //findPass
    public String findPass(MemberVO vo){
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String id=vo.getId();
        String phone=vo.getPhone();
        String pass=null;
        try {
            conn = ConnUtil.getConnection();
            String query = "select pass from mallmember where id=? and phone=?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, id);
            pstmt.setString(2, phone);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                pass = rs.getString("pass");
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
        return pass;
    }
}
