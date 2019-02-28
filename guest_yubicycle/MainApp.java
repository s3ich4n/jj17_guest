package jj17.yubicycle;

import jj17.yubicycle.view.loginController;
import jj17.yubicycle.view.mainstageController;
import jj17.yubicycle.view.returnController;

import java.sql.Connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {

	Connection conn;

	private Stage primaryStage;
	private BorderPane rootLayout;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("The YU Bicycle rental system");

		initRootLayout();

		showMainPage();
	}

	/**
	 * 루트 레이아웃 생성
	 * 여기를 기반으로 메인페이지가 뜬다!
	 * 아마 이거때문에 메인페이지가 안뜨는거일지도 모른다...
	 */
	public void initRootLayout() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/rootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 메인페이지 뷰어를 여기다가 세팅, 컨트롤러 세팅
	 * 스레드를 돌려서 현재시간 보여주게 하기
	 */
	public void showMainPage() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/mainstage.fxml"));
			AnchorPane mainpage = (AnchorPane) loader.load();

			rootLayout.setCenter(mainpage);

			mainstageController controller = loader.getController();
			controller.setMainApp(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 로그인 다이얼로그를 띄우는 컨트롤러.
	 * 그 다음 스테이지인 로그인창 띄우는 과정을 갖다가 작업해준다.
	 */
	public void showLoginDialog() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/loginDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("login");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        loginController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        dialogStage.showAndWait();

	        System.out.println("postmortem");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 반납관련 다이얼로그.
	 */
	public void showReturnDialog() {
		try {

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/returnDialog.fxml"));
			AnchorPane page = (AnchorPane) loader.load();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("register");
	        dialogStage.initModality(Modality.WINDOW_MODAL);
	        dialogStage.initOwner(primaryStage);
	        Scene scene = new Scene(page);
	        dialogStage.setScene(scene);

	        returnController controller = loader.getController();
	        controller.setDialogStage(dialogStage);

	        dialogStage.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * MainApp으로 돌아오게하는 메소드
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}


	/**
	 * 메인함수는 여기있다!
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
