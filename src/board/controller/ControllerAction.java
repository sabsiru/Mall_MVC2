package board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.action.CommandAction;


public class ControllerAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// ��ɾ�� ��ɾ� ó�� Ŭ������ ������ ����
	private Map<String, Object> commandMap = new HashMap<String, Object>();

	// ��ɾ�� ó��Ŭ������ ���εǾ� �ִ� properties ������
	// �о Map��ü�� commandMap�� ����
	// ��ɾ�� ó��Ŭ������ ���εǾ� �ִ� properties ������ Command.properties����
	public void init(ServletConfig config) throws ServletException {
		// web.xml ���� PropertyConfig�� �ش��ϴ� init-param�� ���� �о��
		String props = config.getInitParameter("propertyConfig");
		// ��ɾ�� ó��Ŭ������ ���������� ������ Properties��ü ����
		Properties pr = new Properties();
		String path = config.getServletContext().getRealPath("/WEB-INF");
		FileInputStream f = null;
		try {
			// Command.properties������ ������ �о��
			f = new FileInputStream(new File(path, props));
			// Command.properties������ ������ Properties��ü�� ����
			pr.load(f);
		} catch (IOException e) {
			throw new ServletException(e);
		} finally {
			if (f != null)
				try {
					f.close();
				} catch (IOException ex) {
				}
		}
		// Iterator��ü�� Enumeration ��ü�� Ȯ���Ų ������ ��ü
		Iterator<Object> keylter = pr.keySet().iterator();
		// ��ü�� �ϳ��� ������ �� ��ü������ Properties��ü�� ����� ��ü�� ����
		while (keylter.hasNext()) {
			String command = (String) keylter.next();
			String className = pr.getProperty(command);
			try {// �ش� ���ڿ��� Ŭ������ �����
				@SuppressWarnings("rawtypes")
				Class commandClass = Class.forName(className);
				Object commandInstance = commandClass.newInstance();// �ش�Ŭ������
																	// ��ü�� ����
				// Map��ü�� commandMap�� ��ü ����
				commandMap.put(command, commandInstance);
			} catch (ClassNotFoundException e) {
				throw new ServletException(e);
			} catch (InstantiationException e) {
				throw new ServletException(e);
			} catch (IllegalAccessException e) {
				throw new ServletException(e);
			}

		}
	}

	public void doGet(// get����� ���� �޼ҵ�
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestPro(request, response);
	}

	protected void doPost(// post ����� ���� �޼ҵ�) {
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		requestPro(request, response);
	}

	// ������� ��û�� �м��ؼ� ���� �۾��� ó��
	private void requestPro(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String view = null;
		CommandAction com = null;
		 response.setContentType("text/html; charset=UTF-8");
		 
		try {
			String command = request.getRequestURI();
			if (command.indexOf(request.getContextPath()) == 0) {
				command = command.substring(request.getContextPath().length());
			}
			com = (CommandAction) commandMap.get(command);
			view = com.requestPro(request, response);
		} catch (Throwable e) {
			throw new ServletException(e);
		}
		// Check if the response is already committed
		if (!response.isCommitted()) {
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);

		}
	}
}
