package jj17.yubicycle.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jj17.yubicycle.model.BicycleStatus;

public class getBicycleValueFromDB {
	public static Vector<Integer> getBicycleData() {

		Connection conn = null;
		Statement stmt 	= null;
		ResultSet rs	= null;

		// 숫자 담는 벡터 생성
		Vector<Integer> VQueryResult = new Vector<>();

		try {

		// 여러쿼리를 동시에 보낸다
		String findST1 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 1; ";
		String findST2 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 2; ";
		String findST3 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 3; ";
		String findST4 = "SELECT COUNT(bicycleStatus) FROM MainGateBikeData WHERE bicycleStatus = 4";

		String selectSQL = findST1 + findST2 + findST3 + findST4;

		stmt = conn.createStatement();
		boolean hasMoreResultSets = stmt.execute( selectSQL );

		// 여러쿼리에 대한 처리구문
		READING_QUERY_RESULTS:
			while( hasMoreResultSets || stmt.getUpdateCount() != -1 ) {
				if (hasMoreResultSets ) {
					rs = stmt.getResultSet();
				}
				else {
					int queryResult = stmt.getUpdateCount();
					if ( queryResult == -1 ) {
						break READING_QUERY_RESULTS;
					}
				}

				// 값을 벡터에 저장
				while(rs.next()) {
					String bsdata = rs.getString(1);

					VQueryResult.add(new Integer(Integer.parseInt(bsdata)));
				}

				hasMoreResultSets = stmt.getMoreResults();
			}

		// 저장된 벡터 속의 값은 수행한 쿼리순서대로 저장되어있다.
		for(int i=0; i<VQueryResult.size(); i++) {
			System.out.println(VQueryResult.get(i));
		}

//		bicycleData.add(
//				new BicycleStatus (
//				VQueryResult.get(0),
//				VQueryResult.get(1),
//				VQueryResult.get(2),
//				VQueryResult.get(3) )
//			);

		} catch (SQLException se) {
			se.printStackTrace();
		} finally {
			try {	conn.close();	} catch(Exception e) { e.printStackTrace(); }
			try {	stmt.close();	} catch(Exception e) { e.printStackTrace(); }
			try {	rs.close();		} catch(Exception e) { e.printStackTrace(); }
		}

		return VQueryResult;
	}
}
