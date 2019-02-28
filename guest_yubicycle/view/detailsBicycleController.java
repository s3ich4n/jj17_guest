package jj17.yubicycle.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

//import com.sun.j3d.loaders.Loader;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import jj17.yubicycle.MainApp;
import jj17.yubicycle.model.Bicycle;
import jj17.yubicycle.query.updateQuery;
import jj17.yubicycle.util.ConnectDatabase;
import jj17.yubicycle.view.mainstageController;

/**
 * 로그인 후 자전거 버튼을 눌렸을 때에 대한 컨트롤러.
 *
 * @see detailsBicycleDialog.fxml
 * @author l4in
 *
 */
public class detailsBicycleController {

	Connection conn;

	private static int bicycleNo;
	private static boolean isRental = false;

	private static final int OKRENTALINDEX	= 1;
	private static final int INRENTALINDEX	= 2;
	private static final int INREPAIRINDEX	= 3;
	private static final int OVERDUEINDEX	= 4;

	private Stage dialogStage;

	@FXML private Label selectedNumberLabel;
	@FXML private Label selectedStatus;

	@FXML private Button doRentalButton;
	@FXML private Button exitButton;

	private Bicycle tbc;

	public detailsBicycleController() {
		tbc = new Bicycle();
	}

	// 그전 화면으로 돌아가는 함수.
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	/**
	 * 읽어온 UID값을 통해 쿼리 수행 후 바인딩
	 * @throws SQLException
	 */
	
	
	// mainstageController.getCellText() : 이건 선택한 자전거 번호
	@FXML
	private void initialize() throws SQLException {
		isRental=false;
		bicycleNo=mainstageController.getCellText();
		tbc = findBC(bicycleNo);
		/*if(tbc.getBicycleStatus()==2) isRental=true;
		else isRental=false;*/
		selectedNumberLabel.setText(String.valueOf(bicycleNo));  // setText바인
		if(tbc.getBicycleStatus() == OKRENTALINDEX) {
			selectedStatus.setText("대여 가능합니다.");
			int isRental=loginController.getStudentDataFromRFID().getIsRental();
			if(isRental==1) doRentalButton.setDisable(true);
			else 			doRentalButton.setDisable(false);
		}
		else if(tbc.getBicycleStatus() == INRENTALINDEX) {
			selectedStatus.setText("대여중입니다.");
			doRentalButton.setDisable(true);
		}
		else if (tbc.getBicycleStatus()==INREPAIRINDEX){
			selectedStatus.setText("연체중입니다.");
			doRentalButton.setDisable(true);
		}
		else {
			selectedStatus.setText("수리중입니다.");
			doRentalButton.setDisable(true);
		}

	}

	/**
	 * DB로부터 값을 가져와 Bicycle Type에 저장한다.
	 * @param bicycleNo
	 * @return
	 * @throws SQLException
	 */
	public Bicycle findBC(int selectedNumber) throws SQLException {
		Connection conn = ConnectDatabase.connectToDB();
		PreparedStatement pstmt = null;
		ResultSet rset;
		String sql = "";

		try {
			sql = "SELECT * FROM MainGateBikeData WHERE bicycleNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, selectedNumber);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				tbc.setBicycleNo(rset.getInt(1));
				tbc.setBicycleStatus(rset.getInt(2));
				tbc.setCurrentRentPersonSID(rset.getInt(3));
				tbc.setCurrentBikeLatitude(rset.getDouble(4));
				tbc.setCurrentBikeLongitude(rset.getDouble(5));
				tbc.setDueDate(rset.getDate(6));
			}
			return tbc;
		} catch (Exception e) {
			e.getMessage();
		}

		if (tbc.getBicycleNo() == 0) {
			System.out.println("에러발생.");
			return null;
		}
		return null;
	}


	/**
	 * 대여버튼 클릭시 쿼리를 수행하는 메소드.
	 * TODO: 예약중인데 또 예약하는 상황을 잡기.
	 */
	@FXML
	private void doRentalButtonClicked() {
		boolean isSucceeded = updateQuery.rentalTransaction(tbc);
		/*int isRental=loginController.getStudentDataFromRFID().getIsRental();
		if(isRental==)*/
		if (isSucceeded) {
			// TODO: 신청완료 창 띄우기
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("대여 성공");
			alert.setHeaderText("대여 성공 :)");
			alert.setContentText("대여에 성공하였습니다. 안전운전 하세요 :)");
			System.out.println("ok");
			isRental = true;			
			mainstageController mainStage = new mainstageController();
			//mainStage.changeBtnColor(bicycleNo);
			//mainStage.setBtnColor();
			
			alert.showAndWait();
			
			dialogStage.close();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("대여 문제 발생");
			alert.setHeaderText("대여중 문제 발생");
			alert.setContentText("대여 중 문제가 발생하였습니다. 관리자에게 문의해 주세요.");

			alert.showAndWait();
			dialogStage.close();
		}
	}


	/**
	 *
	 * @return
	 */
	public static boolean getIsRental() {
		return isRental;
	}
	public static int getBicycleNo() {
		return bicycleNo;
	}


	/**
	 * 버튼 누르면 꺼지도록 작업하는 메소드.
	 */
	@FXML
	private void closeButtonClicked() {
		dialogStage.close();
	}
}