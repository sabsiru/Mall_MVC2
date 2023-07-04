package board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

	private static BoardDAO instance = null;

	private BoardDAO() {
	}

	public static BoardDAO getInstance() {
		if (instance == null) {
			synchronized (BoardDAO.class) {
				instance = new BoardDAO();

			}
		}
		return instance;
	}

	//

	public int getBoardCount() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int x = 0;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement("select count(*) from board");
			rs = pstmt.executeQuery();
			if (rs.next()) {
				x = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e2) {
				}
		}
		return x;
	}

	public List<BoardVO> getBoards(int start, int end) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<BoardVO> boardList = null;
		try {
			conn = ConnUtil.getConnection();
			pstmt = conn.prepareStatement(
					"select * from (SELECT rownum rnum,NUM,WRITER,EMAIL,SUBJECT,PASS,REGDATE,READCOUNT,REF,STEP,DEPTH,CONTENT,IP FROM (select * from board order by ref desc,step asc)) where rnum>=? and rnum<=?");// �닔�젙3
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				boardList = new ArrayList<BoardVO>();// �닔�젙4
				do {
					BoardVO board = new BoardVO();
					board.setNum(rs.getInt("num"));
					board.setWriter(rs.getString("writer"));
					board.setEmail(rs.getString("email"));
					board.setSubject(rs.getString("subject"));
					board.setPass(rs.getString("pass"));
					board.setRegdate(rs.getTimestamp("regdate"));
					board.setReadcount(rs.getInt("readcount"));
					board.setRef(rs.getInt("ref"));
					board.setStep(rs.getInt("STEP"));
					board.setDepth(rs.getInt("depth"));
					board.setContent(rs.getString("content"));
					board.setIp(rs.getString("ip"));
					boardList.add(board);
				} while (rs.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e2) {
				}
		}
		return boardList;
	}

}
