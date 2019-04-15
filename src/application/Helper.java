package application;

import java.sql.*;
import java.util.Vector;


public class Helper {
	static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";	//JDBC������
	static final String DB_URL = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";//���ݿ�URL
	static final String USER = "root";	//�û���
	static final String PASS = "123456";//�û���¼����
	
	private Connection connection;
	private Statement statement;
	
	public Helper() 
	{
		//ע��JDBC����
		try {
			System.out.println("�����ݿ�");
			Class.forName(JDBC_DRIVER);
			connection = DriverManager.getConnection(DB_URL,USER,PASS);
			statement = connection.createStatement();
		} catch(Exception e) {
			System.out.println("���ݿ�����ʧ��");
			e.printStackTrace();
		}
	}
	
	protected void finalize( )
	{
		try {
			System.out.println("�ر����ݿ�");
			statement.close();
			connection.close();
		} catch(SQLException e) {
			System.out.println("�ر����ݿ�����ʧ��...");
			e.printStackTrace();
		}
	}
	
	/*
	 * 	�������ܣ���ȡ������Ϣ
	 * 	��ڲ�����ksbh�������ұ�ţ�Ϊ��ʱ��ȡ���п�����Ϣ��
	 * 	���ڲ�������
	 * 	����ֵ��������Ϣ���������ұ�š��������ơ�����ƴ�����ף�
	 */
	public Vector<String[]> getKsxx(String ksbh) 
	{
		Vector<String[]> ksxx=new Vector<String[]>();
		
		String query="SELECT * FROM t_ksxx"+(ksbh.compareTo("")==0? "": " WHERE KSBH="+addQuo(ksbh));
		try {
			ResultSet ksxxSet = statement.executeQuery(query);
			while(ksxxSet.next()) {
				String[] tuple =  new String[3];
				tuple[0]=ksxxSet.getString("KSBH");
				tuple[1]=ksxxSet.getString("KSMC");
				tuple[2]=ksxxSet.getString("PYZS");
				ksxx.addElement(tuple);
			}
		} catch(SQLException e) {
			ksxx.clear();
			e.printStackTrace();
		}
		
		return ksxx;
	}
	
	/*
	 * �������ܣ���ȡҽ����Ϣ
	 * ��ڲ�����ysbh����ҽ����ţ��մ�ʱ��������ҽ����Ϣ
	 * ���ڲ�������
	 * ����ֵ��ҽ����Ϣ
	 */
	public Vector<String[]> getYsxx(String ysbh)
	{
		Vector<String[]> ysxx=new Vector<String[]>();
		
		String query="SELECT * FROM t_ksys"+(ysbh.compareTo("")==0? "": " WHERE YSBH="+addQuo(ysbh));
		try {
			ResultSet set=statement.executeQuery(query);
			if(set.next()) {
				String[] tuple=new String[7];
				tuple[0]=set.getString("YSBH");
				tuple[1]=set.getString("KSBH");
				tuple[2]=set.getString("YSMC");
				tuple[3]=set.getString("PYZS");
				tuple[4]=set.getString("DLKL");
				tuple[5]=set.getString("SFZJ");
				tuple[6]=set.getString("DLRQ");
				ysxx.addElement(tuple);
			}
		}catch(SQLException e) {
			ysxx.clear();
			e.printStackTrace();
		}
		
		return ysxx;
	}
	
	/*
	 * �������ܣ���ȡ������Ϣ
	 * ��ڲ�����brbh�������˱�ţ��մ�ʱ�������в�����Ϣ
	 * ���ڲ�������
	 * ����ֵ��������Ϣ
	 */
	public Vector<String[]> getBrxx(String brbh)
	{
		Vector<String[]> brxx=new Vector<String[]>();
		
		String query="SELECT * FROM t_brxx"+(brbh.compareTo("")==0? "": " WHERE BRBH="+addQuo(brbh));
		try {
			ResultSet set=statement.executeQuery(query);
			if(set.next()) {
				String[] tuple=new String[5];
				tuple[0]=set.getString("BRBH");
				tuple[1]=set.getString("BRMC");
				tuple[2]=set.getString("DLKL");
				tuple[3]=set.getString("YCJE");
				tuple[4]=set.getString("DLRQ");
				brxx.addElement(tuple);
			}
		}catch(SQLException e) {
			brxx.clear();
			e.printStackTrace();
		}
		
		return brxx;
	}
	
	/*
	 * �������ƣ�updatePatientInfo
	 * �������ܣ����²������
	 * ���������brbh�������˱�š�newMoney�����������
	 * �����������
	 * ����ֵ���ɹ�ʱ������true��ʧ��ʱ������false��
	 */
	boolean updatePatientMoney(String brbh,float newMoney)
	{
		boolean isSuccess=false;
		String query="UPDATE t_brxx SET YCJE="+String.valueOf(newMoney)+" WHERE BRBH="+addQuo(brbh);
		System.out.println(query);
		
		try{
			if(statement.executeUpdate(query)==1)
				isSuccess=true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return isSuccess;
	}
	
	/*
	 * �������ܣ���ȡ������Ϣ
	 * ���������hzbh�������ֱ��
	 * �����������
	 * ����ֵ��������Ϣ
	 */
	public Vector<String[]> getHzxx(String hzbh) 
	{
		Vector<String[]> hzxx=new Vector<String[]>();
		
		String query="SELECT * FROM t_hzxx WHERE HZBH="+addQuo(hzbh);
		try {
			ResultSet set=statement.executeQuery(query);
			if(set.next()) {
				String[] tuple=new String[7];
				tuple[0]=set.getString("HZBH");
				tuple[1]=set.getString("HZMC");
				tuple[2]=set.getString("PYZS");
				tuple[3]=set.getString("KSBH");
				tuple[4]=set.getString("SFZJ");
				tuple[5]=set.getString("GHRS");
				tuple[6]=set.getString("GHFY");
				hzxx.addElement(tuple);
			}
		}catch(SQLException e) {
			hzxx.clear();
			e.printStackTrace();
		}
		
		return hzxx;
	}
	
	
	/*
	 * �������ܣ����ݿ��ұ�����������ȡ������Ϣ
	 * ��ڲ�����ksbh�������ұ�š�isExcept�����Ƿ�Ϊר�Һ�
	 * ���ڲ�������
	 * ����ֵ��������Ϣ
	 */
	public String[] getMatchHzxx(String ksbh,boolean isExcept) 
	{
		String[] hzxx=new String[7];
		
		String query="SELECT * FROM t_hzxx WHERE KSBH="+addQuo(ksbh)+" AND SFZJ="+isExcept;
		System.out.println("Query: 0"+query);
		try {
			ResultSet set=statement.executeQuery(query);
			if(set.next()) {
				hzxx[0]=set.getString("HZBH");
				hzxx[1]=set.getString("HZMC");
				hzxx[2]=set.getString("PYZS");
				hzxx[3]=set.getString("KSBH");
				hzxx[4]=set.getString("SFZJ");
				hzxx[5]=set.getString("GHRS");
				hzxx[6]=set.getString("GHFY");
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return hzxx;
	}
	
	/*
	 * �������ܣ�����Һ���Ϣ
	 * ����������Һ���Ϣ�����ֶ�
	 * �����������
	 * ����ֵ���Һ���Ϣ����ɹ����عҺű�ţ�ʧ�ܷ��ؿմ�
	 */
	public String insertGhxx(Vector<String> ghInfo) 
	{
		String numberStr;
		
		//�����Ƿ�
		if(ghInfo.size() != 8)
			return "";
		
		//��ȡ��һ���ùҺű��
		numberStr=getNextNumber();
		
		//����
		String query="INSERT INTO t_ghxx (GHBH,HZBH,YSBH,BRBH,GHRC,THBZ,GHFY,RQSJ,KBSJ)"+" VALUES("
					+addQuo(numberStr)+","+addQuo(ghInfo.get(0))+","+addQuo(ghInfo.get(1))+","+addQuo(ghInfo.get(2))+","+addQuo(ghInfo.get(3))+","
					+addQuo(ghInfo.get(4))+","+addQuo(ghInfo.get(5))+","+addQuo(ghInfo.get(6))+","+addQuo(ghInfo.get(7))+")";
		System.out.println(query);
		try {
			if(statement.executeUpdate(query)==1) {//�ɹ�
				
			}
			else{
				numberStr="";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return numberStr;
	}
	
	/*
	 * �������ƣ�getNextNumber
	 * �������ܣ���ȡ��һ���ùҺű��
	 * �����������
	 * �����������
	 * ����ֵ���ɹ�ʱ��������һ���õĹҺű�ţ�ʧ��ʱ�����ؿմ���
	 */
	private String getNextNumber() 
	{
		String numberStr="";
		String query1="SELECT MAX(GHBH) FROM t_ghxx";
		try {
			ResultSet set=statement.executeQuery(query1);
			if(set.next()) {
				numberStr=set.getString(1);
				Integer number=Integer.valueOf(numberStr==null? "000000": numberStr);
				number++;
				numberStr=String.format("%06d", number);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		
		return numberStr;
	}
	
	/*
	 * �������ƣ�
	 * �������ܣ���ȡĳ�ֺ��ֵ����ѹҺ��˴�
	 * ���������hzbh�������ֱ��
	 * �����������
	 * ����ֵ���ɹ�ʱ������ָ�����ֵ����ѹҺ��˴Σ�ʧ��ʱ������-1
	 */
	int getPeople(String ghbh)
	{
		int num=-1;
		String query="SELECT MAX(GHRC) FROM t_ghxx WHERE DATE(RQSJ)=CurDate() AND GHBH="+addQuo(ghbh);
		System.out.println(query);
		try {
			ResultSet set=statement.executeQuery(query);
			if(set.next())
				num=set.getInt(1);
			else
				num=0;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
	
	/*
	 * �������ƣ�addQuo
	 * �������ܣ���ָ���ִ���������
	 * �������������˫���ŵ��ִ�
	 * �����������
	 * ����ֵ������˫���ŵ��ִ�
	 */
	private String addQuo(String str) 
	{
		return "\""+str+"\"";
	}
}